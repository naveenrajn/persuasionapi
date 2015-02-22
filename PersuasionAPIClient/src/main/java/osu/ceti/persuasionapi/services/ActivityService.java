package osu.ceti.persuasionapi.services;

import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.client.Configuration;
import osu.ceti.persuasionapi.client.exceptions.InvalidConfigurationException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.wrappers.ReportActivityRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

@Component
public class ActivityService {
	
	final String ACTIVITY_REPORT_SERVICE_PATH = "/activity/report";
	
	private static class ReportActivityResponseWrapper extends RestServiceResponse<Object>{
		@SuppressWarnings("unused")
		public ReportActivityResponseWrapper() {super();}
	};
	
	/**
	 * Proxy to /activity/report service of PersuasionAPI
	 * @param userId
	 * @param activityName
	 * @param activityValue
	 * @throws PersuasionAPIException
	 */
	public void reportActivity(String userId, String activityName, String activityValue)
			throws PersuasionAPIException {
		
		if(!Configuration.isValid()) {
			PersuasionAPIException e = new InvalidConfigurationException();
			throw e;
		}
		
		ReportActivityRequest requestData = new ReportActivityRequest(userId, 
				activityName, activityValue);
		
		RestServiceRequest<ReportActivityRequest> request = 
				new RestServiceRequest<ReportActivityRequest>();
		request.setData(requestData);
		
		String requestURL = Configuration.contextPath + ACTIVITY_REPORT_SERVICE_PATH;
		RestServiceResponse<Object> response = Configuration.restTemplate.postForObject(
				requestURL, request, ReportActivityResponseWrapper.class);
		
		if(response.getResponseType().equalsIgnoreCase(RestServiceResponse.FAILURE)) {
			throw new PersuasionAPIException(response.getErrorCode(), response.getErrorMessage());
		}
		
		//No further action required. No response data expected from the service
	}
}
