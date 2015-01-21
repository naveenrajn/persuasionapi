package osu.ceti.persuasionapi.services.internal.wrappers;

public class CreateBadgeRequest {
	
	String badge_class;
	String badge_level;
	String badge_name;
	String badge_description;
	String badge_image_url;
	String html_email_message;
	String public_recognition_post;

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
	public String getBadge_description() {
		return badge_description;
	}
	public void setBadge_description(String badge_description) {
		this.badge_description = badge_description;
	}
	public String getBadge_image_url() {
		return badge_image_url;
	}
	public void setBadge_image_url(String badge_image_url) {
		this.badge_image_url = badge_image_url;
	}
	public String getHtml_email_message() {
		return html_email_message;
	}
	public void setHtml_email_message(String html_email_message) {
		this.html_email_message = html_email_message;
	}
	public String getPublic_recognition_post() {
		return public_recognition_post;
	}
	public void setPublic_recognition_post(String public_recognition_post) {
		this.public_recognition_post = public_recognition_post;
	}
	
}
