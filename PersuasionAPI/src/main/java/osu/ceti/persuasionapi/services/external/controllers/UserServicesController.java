package osu.ceti.persuasionapi.services.external.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.external.UserServices;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

@Controller
@RequestMapping("/user")
public class UserServicesController extends ControllerTemplate {
	
	private static final Log log = LogFactory.getLog(UserServicesController.class);
	
	@Autowired UserServices userServices;
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse createUser(
			@RequestParam(value="user_id", required=true) String userId,
			@RequestParam(value="user_type", required=false, defaultValue="") String userType
			) {
		try {
			userServices.createUser(userId, userType);
			
			return successResponse();
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
		
	}
	
}
