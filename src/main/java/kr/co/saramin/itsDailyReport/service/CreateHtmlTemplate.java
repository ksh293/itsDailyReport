package kr.co.saramin.itsDailyReport.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import kr.co.saramin.itsDailyReport.entity.HistoryItem;
import kr.co.saramin.itsDailyReport.entity.IssueQueryResult;
import kr.co.saramin.itsDailyReport.entity.ReportObject;
import kr.co.saramin.itsDailyReport.entity.WorkLogs;

public class CreateHtmlTemplate {
	
	/**
	 * html template을 생성한다.
	 * @param issueQueryResult
	 * @param devTeam
	 * @return
	 */
	public String createHtmlTemplete(IssueQueryResult issueQueryResult, String devTeam) {
		
		ReportObject reportObject;
		int issueTotal = issueQueryResult.getTotal();
		int workLogTotal = 0;
		int changeLogTotal = 0;
	
		List<WorkLogs> workLogs = null;
		List<HistoryItem> changeLogs = null;
		HistoryItem historyItem = null;
		String key = "";
		String summary = "";
		String assignee = "";
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		
		SimpleDateFormat today = new SimpleDateFormat("yyyy/MM/dd");
		String targetDate = today.format(cal.getTime());
		StringBuilder sb = new StringBuilder();
		
			int workCnt = 0;
			int histCnt = 0;
			
			sb.append("<p><strong>["+ devTeam +" 작업 기록] (총 workCnt건)</strong></p>" + System.getProperty("line.separator")) ;
			sb.append("<div class='table-wrap'>" + System.getProperty("line.separator")) ;
			sb.append("	<table class='confluenceTable'>                                 "  + System.getProperty("line.separator")) ;
			sb.append("		<tbody>                             "  + System.getProperty("line.separator")) ;
			sb.append("			<tr>                            "  + System.getProperty("line.separator")) ;
			sb.append("				<th class='confluenceTh'>ITS Key</th>            "  + System.getProperty("line.separator")) ;
			sb.append("				<th class='confluenceTh'>Summary</th>            "  + System.getProperty("line.separator")) ;
			sb.append("				<th class='confluenceTh'>담당자</th>             "  + System.getProperty("line.separator")) ;
			sb.append("				<th class='confluenceTh'>작업 기록 내용</th>     "  + System.getProperty("line.separator")) ;
			sb.append("				<th class='confluenceTh'>작업시간</th>           "  + System.getProperty("line.separator")) ;
			sb.append("			</tr>                           "  + System.getProperty("line.separator")) ;
			
			for(int i = 0; i < issueTotal; i++){
				
				int keyCnt = 0;
				boolean keyYn = false;
				reportObject = new ReportObject(issueQueryResult, i);
				workLogTotal = reportObject.getWorklog().getTotal();
				key = reportObject.getKey();
				summary = reportObject.getSummary();
				assignee = reportObject.getAssignee();
				workLogs = reportObject.getWorklogs();
				
				for(int z = 0; z < workLogTotal; z++){
					if(today.format(workLogs.get(z).getCreated()).equals(targetDate)){
						keyCnt = keyCnt + 1;
					}
				}
				
				if(workLogTotal > 0) {
					
					for(int j = 0; j < workLogTotal; j++) {
						
						if(today.format(workLogs.get(j).getCreated()).equals(targetDate)){
							
							workCnt = workCnt + 1;
							
							if(!keyYn){
							
								sb.append("         <tr>                               "+ System.getProperty("line.separator"));
								sb.append("         	<td rowspan='"+ keyCnt +"' class='confluenceTd'>"+ System.getProperty("line.separator"));
								sb.append("		 <span class='jira-issue'>                            "   					+ System.getProperty("line.separator")) ;
								sb.append("			<a href='http://sri-its.saraminhr.co.kr/browse/"+key+"' class='jira-issue-key external-link' target='_blank' data-ext-link-init='true'>"   	+ System.getProperty("line.separator")) ;
								sb.append("				<img class='icon' src='http://sri-its.saraminhr.co.kr/images/icons/issuetypes/story.png'/>                            "   					+ System.getProperty("line.separator")) ;
								sb.append(        key   					+ System.getProperty("line.separator")) ;
								sb.append("			</a>                            "   					+ System.getProperty("line.separator")) ;
								sb.append("		 </span>                            "   					+ System.getProperty("line.separator")) ;
								sb.append("				</td>  "+ System.getProperty("line.separator"));
								sb.append("             <td rowspan='"+ keyCnt +"' class='confluenceTd'>"+ StringEscapeUtils.escapeHtml(summary) +"</td>  "+ System.getProperty("line.separator"));
								sb.append("             <td rowspan='"+ keyCnt +"' class='confluenceTd'>"+ StringEscapeUtils.escapeHtml(assignee) +"</td>  "+ System.getProperty("line.separator"));
								sb.append("				<td class='confluenceTd'><pre>"+ StringEscapeUtils.escapeHtml(workLogs.get(j).getComment()) +"</pre></td>"	+ System.getProperty("line.separator")) ;
								sb.append("				<td class='confluenceTd'>"+ workLogs.get(j).getTimeSpent() +"</td>"	+ System.getProperty("line.separator")) ;
								sb.append("		</tr>                           "   					+ System.getProperty("line.separator")) ;
								
								keyYn = true;
								
							}else {
								sb.append("<tr>                               "+ System.getProperty("line.separator"));
								sb.append("		<td colspan='1' class='confluenceTd' style='font-family: Times,serif,sans-serif'><pre>"+ StringEscapeUtils.escapeHtml(workLogs.get(j).getComment()) +"</pre></td> "+ System.getProperty("line.separator"));
								sb.append("		<td colspan='1' class='confluenceTd'>"+ workLogs.get(j).getTimeSpent() +"</td>"+ System.getProperty("line.separator"));
								sb.append("</tr>                               "+ System.getProperty("line.separator"));
								
							}
							
						}
					}
				}			  	
			}
			
			sb.append("		</tbody>                            "   + System.getProperty("line.separator")) ;
			sb.append("	</table>                            "   	+ System.getProperty("line.separator")) ;
			sb.append("</div>" + System.getProperty("line.separator")) ;
			sb.append("<p><strong>["+ devTeam +" 일정 변경] (총 histCnt건)</strong></p>" + System.getProperty("line.separator")) ;
			sb.append("<div class='table-wrap'>                                 "   + System.getProperty("line.separator")) ;
			sb.append("	<table  class='confluenceTable'>                                 "   + System.getProperty("line.separator")) ;
			sb.append("		<tbody>                             "   + System.getProperty("line.separator")) ;
			sb.append("			<tr>                            "  + System.getProperty("line.separator") ) ;
			sb.append("				<th class='confluenceTh'>ITS Key</th>            "  + System.getProperty("line.separator") ) ;
			sb.append("				<th class='confluenceTh'>Summary</th>            "  + System.getProperty("line.separator") ) ;
			sb.append("				<th class='confluenceTh'>담당자</th>             "  + System.getProperty("line.separator") ) ;
			sb.append("				<th class='confluenceTh'>변경된 필드</th>        "  + System.getProperty("line.separator") ) ;
			sb.append("				<th class='confluenceTh'>변경 전 내용</th>       "   + System.getProperty("line.separator")) ;
			sb.append("				<th class='confluenceTh'>변경 후 내용</th>       "  + System.getProperty("line.separator") ) ;
			sb.append("			</tr>                           "  + System.getProperty("line.separator") ) ;
			
			for(int i = 0; i < issueTotal; i++) {
				
				int keyCnt = 0;
				boolean keyYn = false;

				reportObject = new ReportObject(issueQueryResult, i);
				changeLogTotal = reportObject.getChangeLog().getTotal();
				key = reportObject.getKey();
				summary = reportObject.getSummary();
				assignee = reportObject.getAssignee();
				
				for(int j = 0; j < changeLogTotal; j++){
					
					if(today.format(reportObject.getHistories().get(j).getCreated()).equals(targetDate)) {
						
						changeLogs = reportObject.getHistories().get(j).getItems();
						
						for(int x = 0; x < changeLogs.size(); x++) {
							
							if("작업시작일".equals(changeLogs.get(x).getField()) || "duedate".equals(changeLogs.get(x).getField())){
								keyCnt = keyCnt + 1;
							}
						}
					}
					
					
				}
				for(int j = 0; j < changeLogTotal; j++) {
					
					if(today.format(reportObject.getHistories().get(j).getCreated()).equals(targetDate)) {

						changeLogs = reportObject.getHistories().get(j).getItems();
						
						for(int z = 0; z < changeLogs.size(); z++){
							
							historyItem = changeLogs.get(z);
							
							if("작업시작일".equals(historyItem.getField()) || "duedate".equals(historyItem.getField())){
								
								String fromString = "";;
								String toString = "";
								String field = historyItem.getField();
								
								if("duedate".equals(historyItem.getField())) {
									field = "완료예정일";
								}
								
								try {
									fromString = StringUtils.split(historyItem.getFromString(), " ")[0];
								} catch (Exception e) {
									// TODO: handle exception
								}
								try {
									toString = StringUtils.split(historyItem.getToString(), " ")[0];
								} catch (Exception e) {
									// TODO: handle exception
								}
								
								if(!keyYn) {
									
									histCnt = histCnt + 1 ;
									
									sb.append("<tr>                            "   					+ System.getProperty("line.separator")) ;
									sb.append("		<td rowspan='"+keyCnt+"' class='confluenceTd'>                            "   					+ System.getProperty("line.separator")) ;
									sb.append("		 <span class='jira-issue'>                            "   					+ System.getProperty("line.separator")) ;
									sb.append("			<a href='http://sri-its.saraminhr.co.kr/browse/"+key+"' class='jira-issue-key external-link' target='_blank' data-ext-link-init='true'>"   	+ System.getProperty("line.separator")) ;
									sb.append("				<img class='icon' src='http://sri-its.saraminhr.co.kr/images/icons/issuetypes/story.png'/>                            "   					+ System.getProperty("line.separator")) ;
									sb.append(        key   					+ System.getProperty("line.separator")) ;
									sb.append("			</a>                            "   					+ System.getProperty("line.separator")) ;
									sb.append("		 </span>                            "   					+ System.getProperty("line.separator")) ;
									sb.append("		</td>                            "   					+ System.getProperty("line.separator")) ;
									sb.append("		<td rowspan='"+keyCnt+"' class='confluenceTd'>"+ StringEscapeUtils.escapeHtml(summary) +"</td>"   						+ System.getProperty("line.separator")) ;
									sb.append("		<td rowspan='"+keyCnt+"' class='confluenceTd'>"+ StringEscapeUtils.escapeHtml(assignee) +"</td>"  						+ System.getProperty("line.separator")) ;
									sb.append("		<td colspan='1' class='confluenceTd'>"+ field +"</td> "+ System.getProperty("line.separator"));
									sb.append("		<td colspan='1' class='confluenceTd'>"+ fromString +"</td>"+ System.getProperty("line.separator"));
									sb.append("		<td colspan='1' class='confluenceTd'>"+toString +"</td>"+ System.getProperty("line.separator"));
									sb.append("</tr>                           "   					+ System.getProperty("line.separator")) ;
									
									keyYn = true;
									
								}else {
									sb.append("<tr>                           "   					+ System.getProperty("line.separator")) ;
									sb.append("		<td colspan='1' class='confluenceTd'>"+ field +"</td> "+ System.getProperty("line.separator"));
									sb.append("		<td colspan='1' class='confluenceTd'>"+ fromString +"</td>"+ System.getProperty("line.separator"));
									sb.append("		<td colspan='1' class='confluenceTd'>"+ toString +"</td>"+ System.getProperty("line.separator"));
									sb.append("</tr>                           "   					+ System.getProperty("line.separator")) ;
									
								}							
							}						
						}
					}				
				}
			}
			
			sb.append("		</tbody>                            "   + System.getProperty("line.separator")) ;
			sb.append("	</table>                                "   + System.getProperty("line.separator")) ;
			sb.append("</div>                                 "   + System.getProperty("line.separator")) ;
			
			String bodyResult = sb.toString().replace("workCnt", Integer.toString(workCnt)).replace("histCnt", Integer.toString(histCnt));
			
			return bodyResult;
	}
} 