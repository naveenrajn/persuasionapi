package osu.ceti.persuasionapi.data.model;

import java.util.Date;

public class UserBadgeMapping implements java.io.Serializable {

	private UserBadgeMappingId id;
	private Badge badge;
	private Date processedTime;

	public UserBadgeMapping() {
	}

	public UserBadgeMapping(UserBadgeMappingId id, Badge badge,
			Date processedTime) {
		this.id = id;
		this.badge = badge;
		this.processedTime = processedTime;
	}

	public UserBadgeMappingId getId() {
		return this.id;
	}

	public void setId(UserBadgeMappingId id) {
		this.id = id;
	}

	public Badge getBadge() {
		return this.badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public Date getProcessedTime() {
		return this.processedTime;
	}

	public void setProcessedTime(Date processedTime) {
		this.processedTime = processedTime;
	}

}
