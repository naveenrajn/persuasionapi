package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps to persuasion_api.badges
 */
public class Badges implements java.io.Serializable {

	private Integer badgeId;
	private String badgeClass;
	private int badgeLevel;
	private String badgeName;
	private String badgeDesc;
	private byte[] image;
	private String emailMsg;
	private String publicRecognition;
	private Set userBadgeMappingses = new HashSet(0);
	private Set badgeRuleMappingses = new HashSet(0);

	public Badges() {
	}

	public Badges(String badgeClass, int badgeLevel, String badgeName) {
		this.badgeClass = badgeClass;
		this.badgeLevel = badgeLevel;
		this.badgeName = badgeName;
	}

	public Badges(String badgeClass, int badgeLevel, String badgeName,
			String badgeDesc, byte[] image, String emailMsg,
			String publicRecognition, Set userBadgeMappingses,
			Set badgeRuleMappingses) {
		this.badgeClass = badgeClass;
		this.badgeLevel = badgeLevel;
		this.badgeName = badgeName;
		this.badgeDesc = badgeDesc;
		this.image = image;
		this.emailMsg = emailMsg;
		this.publicRecognition = publicRecognition;
		this.userBadgeMappingses = userBadgeMappingses;
		this.badgeRuleMappingses = badgeRuleMappingses;
	}

	public Integer getBadgeId() {
		return this.badgeId;
	}

	public void setBadgeId(Integer badgeId) {
		this.badgeId = badgeId;
	}

	public String getBadgeClass() {
		return this.badgeClass;
	}

	public void setBadgeClass(String badgeClass) {
		this.badgeClass = badgeClass;
	}

	public int getBadgeLevel() {
		return this.badgeLevel;
	}

	public void setBadgeLevel(int badgeLevel) {
		this.badgeLevel = badgeLevel;
	}

	public String getBadgeName() {
		return this.badgeName;
	}

	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}

	public String getBadgeDesc() {
		return this.badgeDesc;
	}

	public void setBadgeDesc(String badgeDesc) {
		this.badgeDesc = badgeDesc;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getEmailMsg() {
		return this.emailMsg;
	}

	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}

	public String getPublicRecognition() {
		return this.publicRecognition;
	}

	public void setPublicRecognition(String publicRecognition) {
		this.publicRecognition = publicRecognition;
	}

	public Set getUserBadgeMappingses() {
		return this.userBadgeMappingses;
	}

	public void setUserBadgeMappingses(Set userBadgeMappingses) {
		this.userBadgeMappingses = userBadgeMappingses;
	}

	public Set getBadgeRuleMappingses() {
		return this.badgeRuleMappingses;
	}

	public void setBadgeRuleMappingses(Set badgeRuleMappingses) {
		this.badgeRuleMappingses = badgeRuleMappingses;
	}

}
