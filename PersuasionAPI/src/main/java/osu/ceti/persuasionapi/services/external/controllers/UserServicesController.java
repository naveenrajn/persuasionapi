package osu.ceti.persuasionapi.services.external.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.external.UserServices;

@Controller
@RequestMapping("/user")
public class UserServicesController extends ControllerTemplate {
	
	@Autowired UserServices userServices;
	
	/**
	 * Creates a new user with the given Id
	 * @param userId
	 * @return success/failure
	 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse createUser(
			@RequestParam(value="user_id", required=true) String userId
			) {
		try {
			userServices.createUser(userId);
			
			return successResponse();
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
	
}
