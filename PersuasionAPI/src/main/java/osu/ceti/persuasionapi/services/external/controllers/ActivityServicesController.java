package osu.ceti.persuasionapi.services.external.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.external.ActivityServices;
import osu.ceti.persuasionapi.services.wrappers.ReportActivityRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;

/**
 * Controller for ActivityServices
 * @see osu.ceti.persuasionapi.services.external.ActivityServices
 *
 */
@Controller
@RequestMapping("/activity")
//TODO: Might want to change the necessary methods to POST
public class ActivityServicesController extends ControllerTemplate {

	@Autowired ActivityServices activityServices;

	/**
	 * Record user activity completion
	 * @param userId
	 * @param activityName
	 * @param value
	 * @throws PersuasionAPIException 
	 */
	@RequestMapping(value="/report", method=RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public RestServiceResponse reportUserActivity(
			@RequestBody RestServiceRequest<ReportActivityRequest> request) {
		try {
			ReportActivityRequest requestData = (ReportActivityRequest) request.getData();
			//ReportActivityRequest requestData = request;
			activityServices.reportUserActivity(requestData.getUserId(), 
					requestData.getActivityName(), requestData.getValue());
			return successResponse();
		} catch(PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}

}
