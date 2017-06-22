package kr.co.saramin.itsDailyReport.entity;

import java.util.List;


public class ReportObject {
	
	Issue issue;
	ChangeLog changeLog;
	String key;			// key
	String summary;		// 요약
	String assignee;	// 담당자
	String reporter;	// 보고자
	String status;		// 진행상태
	String created;		// 생성일
	String updated;		// 업데이트
	String started; 	// 작업시작일
	String duedate;		// 마감일자
	WorkLog worklog;	// 작업기록
	List<WorkLogs> worklogs;		// 작업기록 상세
	List<Histories> histories;	// 변경이력
	
	public ReportObject(IssueQueryResult issueQueryResult, int idx) {
		issue = (Issue) issueQueryResult.getIssues().get(idx);
		changeLog = issue.getChangelog();
		key = issue.getKey();
		summary = issue.getFields().getSummary();
		assignee = (String)issue.getFields().getAssignee().get("displayName");
		reporter = (String)issue.getFields().getReporter().get("displayName");
		status = (String)issue.getFields().getStatus().get("name");
		created = issue.getFields().getCreated();
		updated = issue.getFields().getUpdated();
		started = issue.getFields().getCustomfield_10600();
		duedate = issue.getFields().getDuedate();
		worklog = issue.getFields().getWorklog();
		worklogs = issue.getFields().getWorklog().getWorklogs();
		histories = issue.getChangelog().getHistories();
	}


	public Issue getIssue() {
		return issue;
	}
	public String getKey() {
		return key;
	}
	public String getSummary() {
		return summary;
	}
	public String getAssignee() {
		return assignee;
	}
	public String getReporter() {
		return reporter;
	}
	public String getStatus() {
		return status;
	}
	public String getCreated() {
		return created;
	}
	public String getUpdated() {
		return updated;
	}
	public String getStarted() {
		return started;
	}
	public String getDuedate() {
		return duedate;
	}
	public List<WorkLogs> getWorklogs() {
		return worklogs;
	}
	public List<Histories> getHistories() {
		return histories;
	}
	public WorkLog getWorklog() {
		return worklog;
	}
	public ChangeLog getChangeLog() {
		return changeLog;
	}
	
}
