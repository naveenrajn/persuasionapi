package osu.ceti.persuasionapi.data.model;

/**
 * Composite key to persuasion_api.user_bage_mappings
 * @see osu.ceti.persuasionapi.data.model.UserBadgeMappings
 */
public class UserBadgeMappingsId implements java.io.Serializable {

	private Users users;
	private String badgeClass;

	public UserBadgeMappingsId() {
	}

	public UserBadgeMappingsId(Users users, String badgeClass) {
		this.users = users;
		this.badgeClass = badgeClass;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
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
		if (!(other instanceof UserBadgeMappingsId))
			return false;
		UserBadgeMappingsId castOther = (UserBadgeMappingsId) other;

		return ((this.getUsers() == castOther.getUsers()) || (this.getUsers() != null
				&& castOther.getUsers() != null && this.getUsers().equals(
				castOther.getUsers())))
				&& ((this.getBadgeClass() == castOther.getBadgeClass()) || (this
						.getBadgeClass() != null
						&& castOther.getBadgeClass() != null && this
						.getBadgeClass().equals(castOther.getBadgeClass())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUsers() == null ? 0 : this.getUsers().hashCode());
		result = 37
				* result
				+ (getBadgeClass() == null ? 0 : this.getBadgeClass()
						.hashCode());
		return result;
	}

}
