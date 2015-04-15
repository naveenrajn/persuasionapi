package osu.ceti.persuasionapi.data.model;

public class UserBadgeMappingId implements java.io.Serializable {

	private User user;
	private String badgeClass;

	public UserBadgeMappingId() {
	}

	public UserBadgeMappingId(User user, String badgeClass) {
		this.user = user;
		this.badgeClass = badgeClass;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBadgeClass() {
		return this.badgeClass;
	}

	public void setBadgeClass(String badgeClass) {
		this.badgeClass = badgeClass;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserBadgeMappingId))
			return false;
		UserBadgeMappingId castOther = (UserBadgeMappingId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getBadgeClass() == castOther.getBadgeClass()) || (this
						.getBadgeClass() != null
						&& castOther.getBadgeClass() != null && this
						.getBadgeClass().equals(castOther.getBadgeClass())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37
				* result
				+ (getBadgeClass() == null ? 0 : this.getBadgeClass()
						.hashCode());
		return result;
	}

}
