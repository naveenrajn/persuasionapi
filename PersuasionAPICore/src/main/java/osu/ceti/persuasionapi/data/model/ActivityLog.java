package osu.ceti.persuasionapi.data.model;

import java.util.Date;

public class ActivityLog implements java.io.Serializable {

	private ActivityLogId id;
	private int count;
	private String value;
	private Date logTime;

	public ActivityLog() {
	}

	public ActivityLog(ActivityLogId id, int count, Date logTime) {
		this.id = id;
		this.count = count;
		this.logTime = logTime;
	}

	public ActivityLog(ActivityLogId id, int count, String value, Date logTime) {
		this.id = id;
		this.count = count;
		this.value = value;
		this.logTime = logTime;
	}

	public ActivityLogId getId() {
		return this.id;
	}

	public void setId(ActivityLogId id) {
		this.id = id;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}
