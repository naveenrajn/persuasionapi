package osu.ceti.persuasionapi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.client.Configuration;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.wrappers.GetUserAttributeReqest;
import osu.ceti.persuasionapi.services.wrappers.GetUserAttributeResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;
import osu.ceti.persuasionapi.services.wrappers.UpdateUserAttributeRequest;
import osu.ceti.persuasionapi.services.wrappers.model.UserAttribute;

@Component
public class UserAttributeService {
	
	final String UPDATE_USER_ATTRIBUTE_VALUES_SERVICE_PATH = "/user/attribute/update";
	final String GET_USER_ATTRIBUTE_VALUE_SERVICE_PATH = "/user/attribute/getUserAttributeValue";
	
	private static class GenericServiceResponseWrapper extends RestServiceResponse<Object>{
		@SuppressWarnings("unused")
		public GenericServiceResponseWrapper() {super();}
	};
	
	private static class GetUserAttributeResponseWrapper extends RestServiceResponse<GetUserAttributeResponse>{
		@SuppressWarnings("unused")
		public GetUserAttributeResponseWrapper() {super();}
	};

	/**
	 * Proxy to /user/attribute/update service of PersuasionAPI
	 * @param userId
	 * @param attributeValueMap
	 * @throws PersuasionAPIException
	 */
	public void updateMultipleAttributeForUser(String userId, 
			Map<String, String> attributeValueMap) throws PersuasionAPIException {
		UpdateUserAttributeRequest requestData = new UpdateUserAttributeRequest();
		requestData.setUserId(userId);
		
		List<UserAttribute> attributeList =  new ArrayList<UserAttribute>();
		for(String attributeName : attributeValueMap.keySet()) {
			UserAttribute attribute = new UserAttribute();
			attribute.setAttributeName(attributeName);
			attribute.setValue(attributeValueMap.get(attributeName));
			attributeList.add(attribute);
		}
		requestData.setAttributes(attributeList);
		
		RestServiceRequest<UpdateUserAttributeRequest> request = 
				new RestServiceRequest<UpdateUserAttributeRequest>();
		request.setData(requestData);
		
		String requestURL = Configuration.contextPath + UPDATE_USER_ATTRIBUTE_VALUES_SERVICE_PATH;
		RestServiceResponse<Object> response = Configuration.restTemplate.postForObject(
				requestURL, request, GenericServiceResponseWrapper.class);
		
		if(response.getResponseType().equalsIgnoreCase(RestServiceResponse.FAILURE)) {
			throw new PersuasionAPIException(response.getErrorCode(), response.getErrorMessage());
		}
		
		//No further action required. No response data expected from the service
		
	}
	
	/**
	 * Proxy variant(to update single attribute) to /user/attribute/update service of PersuasionAPI
	 * @param userId
	 * @param attributeName
	 * @param value
	 * @throws PersuasionAPIException
	 */
	public void updateUserAttribute(String userId, String attributeName, String value) 
			throws PersuasionAPIException {
		
		Map<String, String> attributeValueMap = new HashMap<String, String>();
		attributeValueMap.put(attributeName, value);
		
		updateMultipleAttributeForUser(userId, attributeValueMap);
		
		//No further action required. No response data expected from the service
		
	}
	
	/**
	 * Proxy to /user/attribute/getUserAttributeValue of PersuasionAPI
	 * @param userId
	 * @param attributeName
	 * @return
	 * @throws PersuasionAPIException
	 */
	public GetUserAttributeResponse getAttributeValueForUser(String userId, String attributeName) 
			throws PersuasionAPIException {
		GetUserAttributeReqest requestData = new GetUserAttributeReqest();
		requestData.setUserId(userId);
		requestData.setAttributeName(attributeName);
		RestServiceRequest<GetUserAttributeReqest> request = new RestServiceRequest<GetUserAttributeReqest>();
		request.setData(requestData);
		
		String requestURL = Configuration.contextPath + GET_USER_ATTRIBUTE_VALUE_SERVICE_PATH;
		RestServiceResponse<GetUserAttributeResponse> response = Configuration.restTemplate.postForObject(
				requestURL, request, GetUserAttributeResponseWrapper.class);
		
		if(response.getResponseType().equalsIgnoreCase(RestServiceResponse.FAILURE)) {
			throw new PersuasionAPIException(response.getErrorCode(), response.getErrorMessage());
		}
		
		return response.getData();
	}
	
}
