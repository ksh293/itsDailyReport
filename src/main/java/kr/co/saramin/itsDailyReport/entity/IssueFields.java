package kr.co.saramin.itsDailyReport.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueFields {

	String summary;	// 요약[제목]
	Map<String, Object> reporter; // 보고자
	String updated; // 업데이트
	String created;	// 생성일자
	String duedate; // 마감일자
	String customfield_10600;	// 작업시작일
	Map<String, Object> status;	// 진행상태 (status.get("name"))
	Map<String, Object> assignee;	// 담당자 (assignee.get("displayName"))
	WorkLog worklog;	// 작업기록
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Map<String, Object> getReporter() {
		return reporter;
	}
	public void setReporter(Map<String, Object> reporter) {
		this.reporter = reporter;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getCustomfield_10600() {
		return customfield_10600;
	}
	public void setCustomfield_10600(String customfield_10600) {
		this.customfield_10600 = customfield_10600;
	}
	public Map<String, Object> getStatus() {
		return status;
	}
	public void setStatus(Map<String, Object> status) {
		this.status = status;
	}
	public Map<String, Object> getAssignee() {
		return assignee;
	}
	public void setAssignee(Map<String, Object> assignee) {
		this.assignee = assignee;
	}
	public WorkLog getWorklog() {
		return worklog;
	}
	public void setWorklog(WorkLog worklog) {
		this.worklog = worklog;
	}
	
}
