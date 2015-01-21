package osu.ceti.persuasionapi.data.model;

import java.util.Date;

/**
 * Maps to persuasion_api.user_badge_mappings
 */
public class UserBadgeMappings implements java.io.Serializable {

	private UserBadgeMappingsId id;
	private Badges badges;
	private Date processedTime;

	public UserBadgeMappings() {
	}

	public UserBadgeMappings(UserBadgeMappingsId id, Badges badges,
			Date processedTime) {
		this.id = id;
		this.badges = badges;
		this.processedTime = processedTime;
	}

	public UserBadgeMappingsId getId() {
		return this.id;
	}

	public void setId(UserBadgeMappingsId id) {
		this.id = id;
	}

	public Badges getBadges() {
		return this.badges;
	}

	public void setBadges(Badges badges) {
		this.badges = badges;
	}

	public Date getProcessedTime() {
		return this.processedTime;
	}

	public void setProcessedTime(Date processedTime) {
		this.processedTime = processedTime;
	}

}
