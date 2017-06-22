package kr.co.saramin.itsDailyReport.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeLog {

	int startAt;
	int maxResults;
	int total;
	List<Histories> histories;
	
	public int getStartAt() {
		return startAt;
	}
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResult(int maxResult) {
		this.maxResults = maxResult;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Histories> getHistories() {
		return histories;
	}
	public void setHistories(List<Histories> histories) {
		this.histories = histories;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
}
