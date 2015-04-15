package osu.ceti.persuasionapi.data.model;

public class UserAttributeValueId implements java.io.Serializable {

	private User user;
	private UserAttribute userAttribute;

	public UserAttributeValueId() {
	}

	public UserAttributeValueId(User user, UserAttribute userAttribute) {
		this.user = user;
		this.userAttribute = userAttribute;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAttribute getUserAttribute() {
		return this.userAttribute;
	}

	public void setUserAttribute(UserAttribute userAttribute) {
		this.userAttribute = userAttribute;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserAttributeValueId))
			return false;
		UserAttributeValueId castOther = (UserAttributeValueId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getUserAttribute() == castOther.getUserAttribute()) || (this
						.getUserAttribute() != null
						&& castOther.getUserAttribute() != null && this
						.getUserAttribute()
						.equals(castOther.getUserAttribute())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37
				* result
				+ (getUserAttribute() == null ? 0 : this.getUserAttribute()
						.hashCode());
		return result;
	}

}
