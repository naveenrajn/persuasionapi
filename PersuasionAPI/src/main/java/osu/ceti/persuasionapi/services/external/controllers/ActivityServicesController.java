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
import osu.ceti.persuasionapi.services.external.ActivityServices;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

/**
 * Controller for ActivityServices
 * @see osu.ceti.persuasionapi.services.external.ActivityServices
 *
 */
@Controller
@RequestMapping("/activity")
//TODO: Might want to change the necessary methods to POST
public class ActivityServicesController extends ControllerTemplate {

	private static final Log log = LogFactory.getLog(ActivityServicesController.class);

	@Autowired ActivityServices activityServices;

	/**
	 * Record user activity completion
	 * @param userId
	 * @param activityName
	 * @param value
	 * @throws PersuasionAPIException 
	 */
	@RequestMapping(value="/report", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse reportUserActivity(
			@RequestParam(value="user_id", required=true) String userId,
			@RequestParam(value="activity_name", required=true) String activityName,
			@RequestParam(value="value", required=false, defaultValue="1") String value
			) {
		try {
			activityServices.reportUserActivity(userId, activityName, value);
			return successResponse();
		} catch(PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}

	@RequestMapping(value="/create", method=RequestMethod.GET)
	@ResponseBody
	public void createActivity() {

	}

}
