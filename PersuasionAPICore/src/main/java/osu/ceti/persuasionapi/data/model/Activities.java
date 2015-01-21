package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps to persuasion_api.activities
 */
public class Activities implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer activityId;
	private String activityName;
	private String activityDesc;
	private Set activityLogs = new HashSet(0);

	public Activities() {
	}

	public Activities(String activityName) {
		this.activityName = activityName;
	}

	public Activities(String activityName, String activityDesc, Set activityLogs) {
		this.activityName = activityName;
		this.activityDesc = activityDesc;
		this.activityLogs = activityLogs;
	}

	public Integer getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return this.activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Set getActivityLogs() {
		return this.activityLogs;
	}

	public void setActivityLogs(Set activityLogs) {
		this.activityLogs = activityLogs;
	}

}
