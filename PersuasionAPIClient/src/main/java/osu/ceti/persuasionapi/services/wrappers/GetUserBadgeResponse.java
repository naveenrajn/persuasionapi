package osu.ceti.persuasionapi.services.wrappers;

public class GetUserBadgeResponse {

	private String userId;
	private String badgeClass;
	private int badgeLevel;
	private String badgeName;
	private String badgeDesc;
	private byte[] image;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBadgeClass() {
		return badgeClass;
	}
	public void setBadgeClass(String badgeClass) {
		this.badgeClass = badgeClass;
	}
	public int getBadgeLevel() {
		return badgeLevel;
	}
	public void setBadgeLevel(int badgeLevel) {
		this.badgeLevel = badgeLevel;
	}
	public String getBadgeName() {
		return badgeName;
	}
	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}
	public String getBadgeDesc() {
		return badgeDesc;
	}
	public void setBadgeDesc(String badgeDesc) {
		this.badgeDesc = badgeDesc;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
}
