package osu.ceti.persuasionapi.services.external.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.data.model.UserAttributeValue;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.external.UserAttributeServices;
import osu.ceti.persuasionapi.services.wrappers.GetUserAttributeReqest;
import osu.ceti.persuasionapi.services.wrappers.GetUserAttributeResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;
import osu.ceti.persuasionapi.services.wrappers.UpdateUserAttributeRequest;
import osu.ceti.persuasionapi.services.wrappers.model.UserAttribute;

@Controller
@RequestMapping("/user/attribute")
public class UserAttributeServicesController extends ControllerTemplate {
	
	@Autowired UserAttributeServices userAttributeServices;
	
	/**
	 * Updates attribute values for the given user. Creates the user and attribute if not found
	 * @param request
	 * @return success/failure of the update
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public RestServiceResponse updateUserAttributeValues(@RequestBody 
			RestServiceRequest<UpdateUserAttributeRequest> request) {
		try {
			UpdateUserAttributeRequest requestData = request.getData();
			
			//TODO: Find if there is a way to do batch update
			for(UserAttribute attribute : requestData.getAttributes()) {
				userAttributeServices.updateUserAttribute(requestData.getUserId(), 
						attribute.getAttributeName(), attribute.getValue());
			}
			
		return successResponse();
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
	
	/**
	 * Fetches and returns the value of an attribute for a given user
	 * @param request
	 * @return attribute value
	 */
	@RequestMapping(value="/getUserAttributeValue", method=RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public RestServiceResponse getUserAttributeValue(@RequestBody
			RestServiceRequest<GetUserAttributeReqest> request) {
		try {
			GetUserAttributeReqest requestData = request.getData();
			
			UserAttributeValue attributeValue = userAttributeServices.getUserAttributeValue(
					requestData.getUserId(), requestData.getAttributeName());
			
			if(attributeValue == null) {
				return failureResponse("1002", "No attribute value found for the "
						+ "user/attribute combination: " + requestData.getUserId() 
						+ "/" + requestData.getAttributeName());
			}
			
			GetUserAttributeResponse responseData = new GetUserAttributeResponse();
			responseData.setUserId(requestData.getUserId());
			responseData.setAttributeName(requestData.getAttributeName());
			responseData.setValue(attributeValue.getValue());
			
			return successResponse(responseData);
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
	
}
