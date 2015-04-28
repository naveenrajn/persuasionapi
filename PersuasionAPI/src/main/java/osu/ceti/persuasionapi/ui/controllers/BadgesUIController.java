package osu.ceti.persuasionapi.ui.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.services.internal.BadgeServicesInternal;

@Controller
@RequestMapping("/config")
public class BadgesUIController {
	
	@Autowired BadgeServicesInternal badgeServices;

	@RequestMapping("/badges")
	public String configureBadges(ModelMap model) {
		try {
			Map<String, List<Badge>> badgeList = badgeServices.getAllBadgesGroupedByBadgeClass();
			model.addAttribute("badges", badgeList);
		} catch (PersuasionAPIException e) {
			System.out.println("PersuasionAPIException");
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
			System.out.println("OtherException");
			model.addAttribute("errorCode", "Unexpected Error: ");
			model.addAttribute("errorMessage", "Please check logs for more detail. Error message reference: " + e.getMessage());
		}
		return "badges";
	}
}
