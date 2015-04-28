package osu.ceti.persuasionapi.services.external.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.external.BadgeServices;
import osu.ceti.persuasionapi.services.wrappers.GetUserBadgeRequest;
import osu.ceti.persuasionapi.services.wrappers.GetUserBadgeResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;

@Controller
@RequestMapping("/badges")
public class BadgeServicesController extends ControllerTemplate {
	
	@Autowired BadgeServices badgeServices;
	
	/**
	 * Fetches and returns the current badge level for the user for a given badge class
	 * @param request
	 * @return badge corresponding to userId and badgeClass
	 * @throws DatabaseException 
	 */
	@RequestMapping(value="/getUserBadgeForClass", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse getUserBadgeForBadgeClass(@RequestBody RestServiceRequest<GetUserBadgeRequest> request) {
		try {
			//TODO: Add authentication
			GetUserBadgeRequest requestData = request.getData();
			validateFieldsForGetUserBadgeForClass(requestData);
			
			Badge badge = badgeServices.getUserBadgeForBadgeClass(requestData.getUserId(), 
					requestData.getBadgeClass());
			
			if(badge == null) {
				String errorMessage = "No badge assignment found for user " + requestData.getUserId()
						+ " for class " + requestData.getBadgeClass();
				return failureResponse("1001", errorMessage);
			}
			
			GetUserBadgeResponse responseContent = 
					mapBadgeToResponse(requestData.getUserId(), badge);
					
			return successResponse(responseContent);
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
	
	private void validateFieldsForGetUserBadgeForClass(GetUserBadgeRequest requestData)
			throws PersuasionAPIException {
		if(requestData == null) throw new PersuasionAPIException("1000", "Invalid request data");
		if(StringHelper.isEmpty(requestData.getUserId()))
			throw new PersuasionAPIException("1000", "A value is expected for userId");
		if(StringHelper.isEmpty(requestData.getBadgeClass()))
			throw new PersuasionAPIException("1000", "A value is expected for badgeClass");
	}
	
	/**
	 * Fetches and returns all badges for a given user
	 * @param request
	 * @return list of badges the user has
	 */
	@RequestMapping(value="/getAllBadgesForUser", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse getAllBadgesForUser(@RequestBody RestServiceRequest<String> request) {
		try {
			//TODO: Add authentication
			String userId = request.getData();
			validateFieldsForGetAllBadgesForUser(userId);
			
			List<Badge> badgesList = badgeServices.getAllBadgesForUser(userId);
			List<GetUserBadgeResponse> responseData = new ArrayList<GetUserBadgeResponse>();
			for(Badge badge : badgesList) {
				responseData.add(mapBadgeToResponse(userId, badge));
			}
			return successResponse(responseData);
		} catch(PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
	
	private void validateFieldsForGetAllBadgesForUser(String userId) throws PersuasionAPIException {
		if(StringHelper.isEmpty(userId))
			throw new PersuasionAPIException("1000", "A value is expected for 'data' with User ID");
	}
	
	/**
	 * Helper mapping model class Badge to response wrapper class
	 * @param userId
	 * @param badge
	 * @return mapped wrapper object
	 */
	private GetUserBadgeResponse mapBadgeToResponse(String userId, Badge badge) {
		GetUserBadgeResponse response = new GetUserBadgeResponse();
		
		response.setUserId(userId);
		response.setBadgeClass(badge.getBadgeClass());
		response.setBadgeLevel(badge.getBadgeLevel());
		response.setBadgeName(badge.getBadgeName());
		response.setBadgeDesc(badge.getBadgeDesc());
		//TODO: Change this to return Badge Image URL after modifying so in the database
		response.setImage(badge.getImage());
		
		return response;
	}
}
