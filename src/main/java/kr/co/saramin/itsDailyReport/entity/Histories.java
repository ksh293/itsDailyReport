package kr.co.saramin.itsDailyReport.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Histories {

	Map<String, Object> author;	// 작성자
	Date created;	// 작성일
	List<HistoryItem> items;	//상세
	
	public Map<String, Object> getAuthor() {
		return author;
	}
	public void setAuthor(Map<String, Object> author) {
		this.author = author;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<HistoryItem> getItems() {
		return items;
	}
	public void setItems(List<HistoryItem> items) {
		this.items = items;
	}
	
	
}
