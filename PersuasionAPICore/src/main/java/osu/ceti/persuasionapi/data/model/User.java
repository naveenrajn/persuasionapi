package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

public class User implements java.io.Serializable {

	private String userId;
	private Set userBadgeMappings = new HashSet(0);
	private Set userAttributeValues = new HashSet(0);
	private Set activityLogs = new HashSet(0);

	public User() {
	}

	public User(String userId) {
		this.userId = userId;
	}

	public User(String userId, Set userBadgeMappings, Set userAttributeValues,
			Set activityLogs) {
		this.userId = userId;
		this.userBadgeMappings = userBadgeMappings;
		this.userAttributeValues = userAttributeValues;
		this.activityLogs = activityLogs;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set getUserBadgeMappings() {
		return this.userBadgeMappings;
	}

	public void setUserBadgeMappings(Set userBadgeMappings) {
		this.userBadgeMappings = userBadgeMappings;
	}

	public Set getUserAttributeValues() {
		return this.userAttributeValues;
	}

	public void setUserAttributeValues(Set userAttributeValues) {
		this.userAttributeValues = userAttributeValues;
	}

	public Set getActivityLogs() {
		return this.activityLogs;
	}

	public void setActivityLogs(Set activityLogs) {
		this.activityLogs = activityLogs;
	}

}
