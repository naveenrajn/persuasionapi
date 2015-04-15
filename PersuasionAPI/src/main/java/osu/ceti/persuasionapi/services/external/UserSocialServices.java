package osu.ceti.persuasionapi.services.external;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.core.operations.UserSocialFeedOperations;
import osu.ceti.persuasionapi.data.model.UserSocialNotification;

@Service
@Transactional
public class UserSocialServices {
	
	private static final Logger log = Logger.getLogger(UserSocialServices.class);

	@Autowired UserSocialFeedOperations userSocialFeedOperations;
	
	public List<UserSocialNotification> getUserSocialNotificationsAfterTime(String userId, String timeStamp) throws PersuasionAPIException {
		try {
			Date startTime = StringHelper.toDateFromCharString(timeStamp);
			return userSocialFeedOperations.getNotifications(userId, startTime);
		} catch (PersuasionAPIException e) {
			log.error("Failed to process getUserSocialNotificationsAfterTime");
			throw e;
		} catch (Exception e) {
			log.error("Caught exception while processing getUserSocialNotificationsAfterTime"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to retrieve social notifications for user");
		}
	}

}
