package osu.ceti.persuasionapi.services.external.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.data.model.UserBadgeMappings;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.external.BadgeServices;
import osu.ceti.persuasionapi.services.internal.wrappers.UserBadgeResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

@Controller
@RequestMapping("/badges")
public class BadgeServicesController extends ControllerTemplate {
	
	private static final Log log = LogFactory.getLog(ActivityServicesController.class);
	
	@Autowired BadgeServices badgeServices;
	
	/**
	 * Fetches and returns the current badge level for the user for a given badge class
	 * @param userId
	 * @param badgeClass
	 * @return
	 * @throws DatabaseException 
	 */
	@RequestMapping(value="/report", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse getUserBadgeForBadgeClass(
			@RequestParam(value="user_id", required=true) String userId,
			@RequestParam(value="badge_class", required=true) String badgeClass) {
		try {
			UserBadgeMappings userBadgeMapping = badgeServices
					.getUserBadgeForBadgeClass(userId, badgeClass);
			
			//TODO: Handle exception according to API error handling mechanism
			if(userBadgeMapping == null) {
				String errorMessage = "No badge assigned for user " + userId
						+ " for class " + badgeClass;
				failureResponse("1001", errorMessage);
			}
			
			UserBadgeResponse responseContent = new UserBadgeResponse();
			responseContent.setUser_id(userId);
			responseContent.setBadge_class(badgeClass);
			responseContent.setBadge_level(String.valueOf(userBadgeMapping.getBadges().getBadgeLevel()));
			responseContent.setBadge_name(userBadgeMapping.getBadges().getBadgeName());
			//TODO: Change this to return badge URL after modifying the database column from BLOB to string
			responseContent.setBadge_image("");
			
			return successResponse(responseContent);
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
}
