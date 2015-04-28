package osu.ceti.persuasionapi.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/try")
public class TryAPIUIController {
	
	@RequestMapping("activity/report")
	public String tryReportActivity() {
		return "tryReportActivity";
	}
	
	@RequestMapping("badges//getUserBadgeForClass")
	public String tryGetUserBadgeForClass() {
		return "tryGetUserBadgeForClass";
	}
	
	@RequestMapping("badges/getAllBadgesForUser")
	public String tryGetAllBadgesForUser() {
		return "tryGetAllBadgesForUser";
	}
	
	@RequestMapping("/user/attribute/update")
	public String tryUserAttributeUpdate() {
		return "tryUserAttributeUpdate";
	}
	
	@RequestMapping("/user/attribute/getUserAttributeValue")
	public String tryGetUserAttributeValue() {
		return "tryGetUserAttributeValue";
	}
	
	@RequestMapping("/user/social/getNotificationsAfterTime")
	public String tryGetUserSocialNotificationsAfterTime() {
		return "tryGetUserSocialNotificationsAfterTime";
	}
}
