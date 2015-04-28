package osu.ceti.persuasionapi.services;

import java.util.Date;

import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.client.Configuration;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.services.wrappers.GetSocialNotificationsRequest;
import osu.ceti.persuasionapi.services.wrappers.GetSocialNotificationsResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

@Component
public class UserSocialNotificationService {
	
	final String GET_SOCIAL_NOTIFICATION_SERVICE_PATH = "/user/social/getNotificationsAfterTime";
	
	private static class GetSocialNotificationsResponseWrapper 
		extends RestServiceResponse<GetSocialNotificationsResponse> {
		@SuppressWarnings("unused")
		public GetSocialNotificationsResponseWrapper() {super();}
	};

	/**
	 * Proxy to /user/social/getNotificationsAfterTime service of PersuasionAPI
	 * @param userId
	 * @param timeStamp
	 * @return
	 * @throws PersuasionAPIException
	 */
	public GetSocialNotificationsResponse getNotificationsAfterTime(String userId, 
			Date timeStamp) throws PersuasionAPIException {
		GetSocialNotificationsRequest requestData = new GetSocialNotificationsRequest();
		requestData.setUserId(userId);
		requestData.setTimestamp(StringHelper.dateToString(timeStamp));
		
		RestServiceRequest<GetSocialNotificationsRequest> request = 
				new RestServiceRequest<GetSocialNotificationsRequest>();
		request.setData(requestData);
		
		String requestURL = Configuration.contextPath + GET_SOCIAL_NOTIFICATION_SERVICE_PATH;
		RestServiceResponse<GetSocialNotificationsResponse> response = Configuration.restTemplate.postForObject(
				requestURL, request, GetSocialNotificationsResponseWrapper.class);
		
		if(response.getResponseType().equalsIgnoreCase(RestServiceResponse.FAILURE)) {
			throw new PersuasionAPIException(response.getErrorCode(), response.getErrorMessage());
		}

		return response.getData();
	}

}
