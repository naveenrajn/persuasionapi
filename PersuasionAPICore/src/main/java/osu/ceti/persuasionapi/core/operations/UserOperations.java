package osu.ceti.persuasionapi.core.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.UserDAO;
import osu.ceti.persuasionapi.data.model.User;

@Component
public class UserOperations {
	
	private static final Log log = LogFactory.getLog(UserOperations.class);
	
	@Autowired private UserDAO userDAO;
	
	/**
	 * Checks the database for the existence of a user with the given userId
	 * If user found in database, returns the handle
	 * If not found, creates one and returns the handle
	 * @param userId
	 * @param userType
	 * @return user database object
	 * @throws DatabaseException 
	 */
	public User findOrCreateUser(String userId) throws DatabaseException {
		//TODO: Will just merge() suffice? Do we need lookupUser() here?
		User user = lookupUser(userId);
		if(user == null) {
			user = new User(userId);
			userDAO.merge(user);
		}
		
		return user;
	}
	
	/**
	 * Looks up the database for user with the given user ID and returns the handle
	 * @param userId
	 * @return user handle if found, null if not
	 * @throws DatabaseException 
	 */
	public User lookupUser(String userId) throws DatabaseException {
		return userDAO.findById(userId);
	}
}
