package osu.ceti.persuasionapi.services.external.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
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
			List<UserSocialNotification> userSocialNotifications = userSocialServices
					.getUserSocialNotificationsAfterTime(requestData.getUserId(),
							requestData.getTimeStamp());

			GetSocialNotificationsResponse responseData = mapResponseBody(requestData.getUserId(), 
					userSocialNotifications);

			return successResponse(responseData);
		} catch (PersuasionAPIException e) {
			return failureResponse("1001", e);
		}
	}

	private GetSocialNotificationsResponse mapResponseBody(String userId, 
			List<UserSocialNotification> userSocialNotifications) {
		GetSocialNotificationsResponse response = new GetSocialNotificationsResponse();
		response.setUserId(userId);
		List<SocialNotification> notifications = new ArrayList<SocialNotification>();
		for(UserSocialNotification notification : userSocialNotifications) {
			SocialNotification singleNotification = new SocialNotification();
			singleNotification.setNotificationText(notification.getNotificationText());
			singleNotification.setTimeStamp(notification.getPostedTime());
			notifications.add(singleNotification);
		}
		response.setNotifications(notifications);
		return response;
	}
}
