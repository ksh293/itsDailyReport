package kr.co.saramin.itsDailyReport.module;

public class RestApiUrl {

	private String jiraDefaultUrl = "/rest/api/2/";
	private String ConfluenceDefaultUrl = "/rest/api/";
	
	/**
	 * JIRA REST API를 호출하기 위한 URL을 세팅한다.
	 * JQL 쿼리를 날리는 URL
	 * @return
	 */
	public String getJiraQueryUrl(){
		String queryUrl = this.jiraDefaultUrl + "search?jql=";
		return queryUrl;
	}
	
	/**
	 * JIRA REST API를 호출하기 위한 URL을 세팅한다.
	 * 특정 이슈를 조회하는 URL (건별 조회)
	 * @return
	 */
	public String getJiraIssueUrl(){
		String queryUrl = this.jiraDefaultUrl + "issue/";
		return queryUrl;
	}
	
	/**
	 * Confluence REST API를 호출하기 위한 URL을 세팅한다.
	 * WIKI의 페이지 관련 URL이며, 새로운 페이지를 생성한다.
	 * @return
	 */
	public String getConfluenceCreatePageUrl(){
		String createNewPageUrl = this.ConfluenceDefaultUrl + "content/";
		return createNewPageUrl;
	}
}
