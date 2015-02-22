package osu.ceti.persuasionapi.services;

import java.util.List;

import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.client.Configuration;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.wrappers.GetUserBadgeRequest;
import osu.ceti.persuasionapi.services.wrappers.GetUserBadgeResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

@Component
public class BadgeService {
	
	final String GET_USER_BADGE_SERVICE_PATH = "/badges/getUserBadgeForClass";
	final String GET_ALL_USER_BADGE_SERVICE_PATH = "/badges/getAllBadgesForUser";
	
	private static class GetUserBadgeResponseWrapper extends RestServiceResponse<GetUserBadgeResponse>{
		@SuppressWarnings("unused")
		public GetUserBadgeResponseWrapper() {super();}
	};
	
	private static class GetAllUserBadgesResponseWrapper extends RestServiceResponse<List<GetUserBadgeResponse>>{
		@SuppressWarnings("unused")
		public GetAllUserBadgesResponseWrapper() {super();}
	};
	
	/**
	 * Proxy to /badge/getUserBadgeForClass service of PersuasionAPI
	 * @param userId
	 * @param badgeClass
	 * @return
	 * @throws PersuasionAPIException
	 */
	public GetUserBadgeResponse getUserBadge(String userId, String badgeClass) throws PersuasionAPIException {
		GetUserBadgeRequest requestData = new GetUserBadgeRequest(userId, badgeClass);
		
		RestServiceRequest<GetUserBadgeRequest> request = new RestServiceRequest<GetUserBadgeRequest>();
		request.setData(requestData);
		
		String requestURL = Configuration.contextPath + GET_USER_BADGE_SERVICE_PATH;
		RestServiceResponse<GetUserBadgeResponse> response = Configuration.restTemplate.postForObject(
				requestURL, request, GetUserBadgeResponseWrapper.class);
		
		if(response.getResponseType().equalsIgnoreCase(RestServiceResponse.FAILURE)) {
			throw new PersuasionAPIException(response.getErrorCode(), response.getErrorMessage());
		}

		return response.getData();
	}
	
	/**
	 * Proxy to /badges/getAllBadgesForUser service of PersuasionAPI
	 * @param userId
	 * @return
	 * @throws PersuasionAPIException
	 */
	public List<GetUserBadgeResponse> getAllBadgesForUser(String userId) throws PersuasionAPIException {
		RestServiceRequest<String> request = new RestServiceRequest<String>();
		request.setData(userId);
		
		String requestURL = Configuration.contextPath + GET_ALL_USER_BADGE_SERVICE_PATH;
		RestServiceResponse<List<GetUserBadgeResponse>> response = Configuration.restTemplate.postForObject(
				requestURL, request, GetAllUserBadgesResponseWrapper.class);
		
		if(response.getResponseType().equalsIgnoreCase(RestServiceResponse.FAILURE)) {
			throw new PersuasionAPIException(response.getErrorCode(), response.getErrorMessage());
		}
		
		return response.getData();
	}
	
}
