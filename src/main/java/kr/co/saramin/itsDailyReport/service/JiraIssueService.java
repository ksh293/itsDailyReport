package kr.co.saramin.itsDailyReport.service;

import java.io.IOException;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import kr.co.saramin.itsDailyReport.entity.IssueQueryResult;
import kr.co.saramin.itsDailyReport.entity.IssueSearchResult;
import kr.co.saramin.itsDailyReport.entity.WorkLog;
import kr.co.saramin.itsDailyReport.module.RestApiUrl;
import kr.co.saramin.itsDailyReport.module.RestHttpClient;

public class JiraIssueService {
	
	private RestHttpClient client = null;
	
	private final static String JIRA = "jira";
	
	/**
	 * 서비스 객체 생성 시 JIRA 호스트 인증 정보를 셋팅한 HTTP Client 객체 생성
	 * @throws Exception
	 */	
	public JiraIssueService() throws Exception {
		client = new RestHttpClient(JIRA);
	}
	
	/**
	 * jql 쿼리를 날려 api를 호출한다.
	 * @param query
	 * @return IssueQueryResult
	 * @throws IOException
	 * 
	 */
	public IssueQueryResult getIssuesFromQuery(final String query) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String content = URLEncoder.encode(query, "UTF-8");
		
		RestApiUrl resourceUrl = new RestApiUrl();
		
		String resource = resourceUrl.getJiraQueryUrl() + query;
		
		client.setResourceName(resource);
		
		ClientResponse response = client.get();
		
		content = response.getEntity(String.class);
		
		IssueQueryResult issues = mapper.readValue(content, new TypeReference<IssueQueryResult>(){});
		
		return issues;
	}
	
	/**
	 * issue의 key를 조건으로 건건이 조회한다.
	 * @param query
	 * @return IssueSearchResult
	 * @throws IOException
	 * 
	 */
	
	public IssueSearchResult getIssuesResult(final String query) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String content = URLEncoder.encode(query, "UTF-8");
		
		RestApiUrl resourceUrl = new RestApiUrl();
		
		String resource = resourceUrl.getJiraIssueUrl() + query;
		
		client.setResourceName(resource);
		
		ClientResponse response = client.get();
		
		content = response.getEntity(String.class);
		
		IssueSearchResult issues = mapper.readValue(content, new TypeReference<IssueSearchResult>() {});
		
		return issues;
		
	}
	/**
	 * IssueQueryResult의 Issue 데이터에 이슈 건별 작업기록 데이터를 세팅한다
	 * @param IssueQueryResult
	 * @return 
	 * @throws Exception
	 * 
	 */
		
	public void addWorkLogFields(IssueQueryResult issueQueryResult) throws Exception {
			
		String key = "";
		
		for(int i = 0; i < issueQueryResult.getTotal(); i++) {
			
			key = issueQueryResult.getIssues().get(i).getKey();
			
			IssueSearchResult issueSearchResult = this.getIssuesResult(key);
			
			WorkLog workLog = issueSearchResult.getFields().getWorklog();
			
			issueQueryResult.getIssues().get(i).getFields().setWorklog(workLog);
		}
	}

}
