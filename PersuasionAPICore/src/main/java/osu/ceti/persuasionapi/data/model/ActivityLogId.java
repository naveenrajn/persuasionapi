package osu.ceti.persuasionapi.data.model;

public class ActivityLogId implements java.io.Serializable {

	private User user;
	private Activity activity;

	public ActivityLogId() {
	}

	public ActivityLogId(User user, Activity activity) {
		this.user = user;
		this.activity = activity;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return this.activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ActivityLogId))
			return false;
		ActivityLogId castOther = (ActivityLogId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getActivity() == castOther.getActivity()) || (this
						.getActivity() != null
						&& castOther.getActivity() != null && this
						.getActivity().equals(castOther.getActivity())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37 * result
				+ (getActivity() == null ? 0 : this.getActivity().hashCode());
		return result;
	}

}
