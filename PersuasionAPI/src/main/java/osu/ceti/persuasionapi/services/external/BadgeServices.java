package osu.ceti.persuasionapi.services.external;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.operations.UserBadgeOperations;
import osu.ceti.persuasionapi.data.model.UserBadgeMappings;

@Service
@Transactional
public class BadgeServices {
	
	private static final Log log = LogFactory.getLog(ActivityServices.class);
	
	@Autowired UserBadgeOperations userBadgeOperations;

	public UserBadgeMappings getUserBadgeForBadgeClass(String userId, String badgeClass)
			throws PersuasionAPIException {
		try {
			return userBadgeOperations.getSingleClassBadgeForUser(userId, badgeClass);
		} catch (PersuasionAPIException e) {
			log.error("Failed to process getUserBadgeForBadgeClass");
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while processing getUserBadgeForBadgeClass"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to retrieve badge details for user");
		}
	}

}
