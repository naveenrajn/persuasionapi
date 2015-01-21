package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps to persuasion_api.users
 */
public class Users implements java.io.Serializable {

	private String userId;
	private String userType;
	private Set userBadgeMappingses = new HashSet(0);
	private Set activityLogs = new HashSet(0);

	public Users() {
	}

	public Users(String userId) {
		this.userId = userId;
	}

	public Users(String userId, String userType, Set userBadgeMappingses,
			Set activityLogs) {
		this.userId = userId;
		this.userType = userType;
		this.userBadgeMappingses = userBadgeMappingses;
		this.activityLogs = activityLogs;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Set getUserBadgeMappingses() {
		return this.userBadgeMappingses;
	}

	public void setUserBadgeMappingses(Set userBadgeMappingses) {
		this.userBadgeMappingses = userBadgeMappingses;
	}

	public Set getActivityLogs() {
		return this.activityLogs;
	}

	public void setActivityLogs(Set activityLogs) {
		this.activityLogs = activityLogs;
	}

}
