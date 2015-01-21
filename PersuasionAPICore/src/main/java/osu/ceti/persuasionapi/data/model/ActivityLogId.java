package osu.ceti.persuasionapi.data.model;

/**
 * Composite key to persuasion_api.activity_log
 * @see osu.ceti.persuasionapi.data.model.ActivityLog
 */
public class ActivityLogId implements java.io.Serializable {

	private Users users;
	private Activities activities;

	public ActivityLogId() {
	}

	public ActivityLogId(Users users, Activities activities) {
		this.users = users;
		this.activities = activities;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Activities getActivities() {
		return this.activities;
	}

	public void setActivities(Activities activities) {
		this.activities = activities;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ActivityLogId))
			return false;
		ActivityLogId castOther = (ActivityLogId) other;

		return ((this.getUsers() == castOther.getUsers()) || (this.getUsers() != null
				&& castOther.getUsers() != null && this.getUsers().equals(
				castOther.getUsers())))
				&& ((this.getActivities() == castOther.getActivities()) || (this
						.getActivities() != null
						&& castOther.getActivities() != null && this
						.getActivities().equals(castOther.getActivities())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUsers() == null ? 0 : this.getUsers().hashCode());
		result = 37
				* result
				+ (getActivities() == null ? 0 : this.getActivities()
						.hashCode());
		return result;
	}

}
