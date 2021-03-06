package kr.co.saramin.itsDailyReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.saramin.itsDailyReport.entity.IssueQueryResult;
import kr.co.saramin.itsDailyReport.entity.ReportObject;
import kr.co.saramin.itsDailyReport.module.RequestQuery;
import kr.co.saramin.itsDailyReport.service.ConfluenceCreateService;
import kr.co.saramin.itsDailyReport.service.CreateHtmlTemplate;
import kr.co.saramin.itsDailyReport.service.JiraIssueService;

/** 
 * <pre>
 *  파 일 명 : ScheduledTasks.java
 *  설    명 : 보고서 생성 작업 스케줄러(실행)
 *  작 성 자 : 김소현
 *  작 성 일 : 2017.06.22
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 */

@Component
public class ScheduledTasks {
	
	@Autowired
	private static JiraIssueService jiraIssueService;
	@Autowired
	private static ConfluenceCreateService confluenceCreateService;
	@Autowired
	private static RequestQuery requestQuery;
	
	private final static String dev1Team = "개발1팀";
	private final static String dev2Team = "개발2팀";
	private final static String uiDevTeam = "UI개발팀";
	
	/**
	 * 매일 새벽 5시에 개발1,2팀 어제 변경된 ITS 이력을 Report로 생성, WIKI에 페이지를 자동으로 생성한다.
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 5 * * *")
	public void reportCurrntTime() throws Exception{
		
		this.execute(dev1Team);
		this.execute(dev2Team);
		this.execute(uiDevTeam);
		
	}
	/**
	 * 1. 개발1,2팀을 구분하여 각각의 리포트를 생성하기 위한 JIRA API 호출
	 * 2. 응답받은 데이터를 ReportObject 객체에 셋팅
	 * 		1) jira api의 응답은 한번에 최대 50개 까지 받을 수 있다.
	 * 		2) total이 50개가 넘어가면 startAt = 50 * i 개(50의 배수)로 쿼리를 다시 세팅하여 api를 호출한다.
	 * 		3) 호출하여 세팅한 객체를 devTeamResult 객체의 Issues에 add한다.
	 * 3. ReportObject 객체를 파라미터로 넘겨 HtmlTemplate을 생성
	 * 4. 생성한 Template으로 Confluence API를 호출하여 WIKI 특정 공간에 새 리포트 페이지 생성
	 * @param devTeam
	 * @throws Exception
	 */
	public void execute(String devTeam) throws Exception{
		
		String devTeamQuery = "";
		jiraIssueService = new JiraIssueService();
		requestQuery = new RequestQuery();
		confluenceCreateService = new ConfluenceCreateService();
		
		if("개발1팀".equals(devTeam)){
			devTeamQuery = requestQuery.ServiceDev1Team;
		}else if("개발2팀".equals(devTeam)){
			devTeamQuery = requestQuery.ServiceDev2Team;
		}else if("UI개발팀".equals(devTeam)){
			devTeamQuery = requestQuery.UIDevTeam;
		}

		IssueQueryResult devTeamResult = jiraIssueService.getIssuesFromQuery(devTeamQuery);
		
		if(0 < devTeamResult.getTotal()){
			
			for(int i = 1; i * 50 < devTeamResult.getTotal(); i++) { 
				
				IssueQueryResult devTeamResultPag = jiraIssueService.getIssuesFromQuery(devTeamQuery + "&startAt=" + i * 50);
				
				for(int j = 0; j < (devTeamResult.getTotal() - (i * 50)); j++){
					devTeamResult.getIssues().add(devTeamResultPag.getIssues().get(j));
				}
				
			}			
			jiraIssueService.addWorkLogFields(devTeamResult);
			
			for(int i = 0; i < devTeamResult.getTotal(); i++) {

				new ReportObject(devTeamResult, i); 
			}

			CreateHtmlTemplate template = new CreateHtmlTemplate();
			String templateHtml = template.createHtmlTemplete(devTeamResult, devTeam);

			confluenceCreateService.postCreateNewPage(templateHtml, devTeam);
			
		}
	}
	

	public static void main(String[] args) throws Exception {
		ScheduledTasks test = new ScheduledTasks();
		test.execute(dev1Team);
		test.execute(dev2Team);
		test.execute(uiDevTeam);
	}
	
	
}
