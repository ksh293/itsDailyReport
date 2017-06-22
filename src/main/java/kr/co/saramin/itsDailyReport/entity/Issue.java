package kr.co.saramin.itsDailyReport.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
	
	String expand;
	String id;
	String self;
	String key;
	IssueFields fields;
	ChangeLog changelog;
	
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSelf() {
		return self;
	}
	public void setSelf(String self) {
		this.self = self;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public IssueFields getFields() {
		return fields;
	}
	public void setFields(IssueFields fields) {
		this.fields = fields;
	}
	public ChangeLog getChangelog() {
		return changelog;
	}
	public void setChangelog(ChangeLog changelog) {
		this.changelog = changelog;
	}
	
	
}
