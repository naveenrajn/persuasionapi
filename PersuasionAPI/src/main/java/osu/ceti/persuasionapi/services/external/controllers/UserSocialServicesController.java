package osu.ceti.persuasionapi.services.external.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.UserSocialNotification;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.external.UserSocialServices;
import osu.ceti.persuasionapi.services.wrappers.GetSocialNotificationsRequest;
import osu.ceti.persuasionapi.services.wrappers.GetSocialNotificationsResponse;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;
import osu.ceti.persuasionapi.services.wrappers.model.SocialNotification;

@Controller
@RequestMapping("/user/social")
public class UserSocialServicesController extends ControllerTemplate {

	@Autowired UserSocialServices userSocialServices;

	@RequestMapping(value="/getNotificationsAfterTime", 
			method=RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public RestServiceResponse getNotificationsAfterTime(@RequestBody 
			RestServiceRequest<GetSocialNotificationsRequest> request) {
		try {
			GetSocialNotificationsRequest requestData = request.getData();
			validateFieldsForGetNotificationsAfterTime(requestData);
			
			Date startTime = null;
			try {
				System.out.println(requestData.getTimestamp());
				startTime = StringHelper.toDateFromCharString(requestData.getTimestamp());
			} catch(ParseException e) {
				//do nothing. allow startTime to be handled for null
			}
			if(startTime==null)
				throw new PersuasionAPIException("1001", "Invalid timestamp. Please verify the"
						+ " format: yyyy-mm-dd hh:MM:ss");
			
			List<UserSocialNotification> userSocialNotifications = userSocialServices
					.getUserSocialNotificationsAfterTime(requestData.getUserId(), startTime);

			GetSocialNotificationsResponse responseData = mapResponseBody(requestData.getUserId(), 
					userSocialNotifications);

			return successResponse(responseData);
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}
	
	private void validateFieldsForGetNotificationsAfterTime(GetSocialNotificationsRequest requestData)
			throws PersuasionAPIException {
		if(requestData == null) throw new PersuasionAPIException("1000", "Invalid request data");
		if(StringHelper.isEmpty(requestData.getUserId()))
			throw new PersuasionAPIException("1000", "A value is expected for userId");
		if(StringHelper.isEmpty(requestData.getTimestamp()))
			throw new PersuasionAPIException("1000", "A value is expected for timestamp");
	}

	private GetSocialNotificationsResponse mapResponseBody(String userId, 
			List<UserSocialNotification> userSocialNotifications) {
		GetSocialNotificationsResponse response = new GetSocialNotificationsResponse();
		response.setUserId(userId);
		List<SocialNotification> notifications = new ArrayList<SocialNotification>();
		for(UserSocialNotification notification : userSocialNotifications) {
			SocialNotification singleNotification = new SocialNotification();
			singleNotification.setNotificationText(notification.getNotificationText());
			singleNotification.setTimestamp(notification.getPostedTime());
			notifications.add(singleNotification);
		}
		response.setNotifications(notifications);
		return response;
	}
}
