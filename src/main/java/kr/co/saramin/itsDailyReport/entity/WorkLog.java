package kr.co.saramin.itsDailyReport.entity;

import java.util.List;

public class WorkLog {

	int startAt;
	int maxResults;
	int total;
	List<WorkLogs> worklogs;
	
	public int getStartAt() {
		return startAt;
	}
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<WorkLogs> getWorklogs() {
		return worklogs;
	}
	public void setWorklogs(List<WorkLogs> worklogs) {
		this.worklogs = worklogs;
	}
	
}
