package osu.ceti.persuasionapi.services.internal.wrappers;

import java.util.List;

public class AllUserBadgesReponse {

	String user_id;
	List<UserBadge> user_badges;
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public List<UserBadge> getUser_badges() {
		return user_badges;
	}

	public void setUser_badges(List<UserBadge> user_badges) {
		this.user_badges = user_badges;
	}

	class UserBadge {
		String badge_class;
		String badge_level;
		String badge_name;
		String badge_image;

		public String getBadge_class() {
			return badge_class;
		}
		public void setBadge_class(String badge_class) {
			this.badge_class = badge_class;
		}
		public String getBadge_level() {
			return badge_level;
		}
		public void setBadge_level(String badge_level) {
			this.badge_level = badge_level;
		}
		public String getBadge_name() {
			return badge_name;
		}
		public void setBadge_name(String badge_name) {
			this.badge_name = badge_name;
		}
		public String getBadge_image() {
			return badge_image;
		}
		public void setBadge_image(String badge_image) {
			this.badge_image = badge_image;
		}
	}
}
