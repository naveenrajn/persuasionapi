package osu.ceti.persuasionapi.services.internal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/badges")
public class BadgesServiceInternalController {
	
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public void createBadge(
			@RequestParam(value="badge_class", required=true) String badgeClass,
			@RequestParam(value="badge_level", required=true) String badgeLevel,
			@RequestParam(value="badge_name", required=false) String badgeName,
			@RequestParam(value="badge_description") String badgeDescription,
			@RequestParam(value="badge_image_url") String imageURL,
			@RequestParam(value="html_email_message") String emailMessage,
			@RequestParam(value="public_recognition_post") String publicRecognitionPost
			) {
		
	}
}
