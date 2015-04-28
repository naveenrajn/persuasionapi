package osu.ceti.persuasionapi.core.operations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.data.access.UserAttributeDAO;
import osu.ceti.persuasionapi.data.access.UserAttributeValueDAO;
import osu.ceti.persuasionapi.data.model.User;
import osu.ceti.persuasionapi.data.model.UserAttribute;
import osu.ceti.persuasionapi.data.model.UserAttributeValue;
import osu.ceti.persuasionapi.data.model.UserAttributeValueId;

@Component
public class UserAttributeOperations {

	@Autowired UserAttributeDAO userAttributeDAO;
	@Autowired UserAttributeValueDAO userAttributeValueDAO;
	
	/**
	 * Looks up the database to check if an attribute already exists
	 * If already available, fetches and returns the handle
	 * If not, creates a new one and returns the handle
	 * @param attributeName
	 * @param attributeDescription
	 * @return
	 * @throws DatabaseException
	 */
	public UserAttribute findOrCreateUserAttribute(String attributeName, String attributeDescription) throws DatabaseException {
		UserAttribute userAttribute = new UserAttribute();
		userAttribute.setAttributeName(attributeName);
		userAttribute.setAttributeDesc(attributeDescription);
		
		userAttributeDAO.merge(userAttribute);
		
		return userAttribute;
	}
	
	/**
	 * Looks up the database for user with the given user ID and returns the handle
	 * @param attributeName
	 * @return user handle if found, null if not
	 * @throws DatabaseException 
	 */
	public UserAttribute lookupUserAttribute(String attributeName) throws DatabaseException {
		return userAttributeDAO.findById(attributeName);
	}
	
	/**
	 * Updates the value of an attribute for the given user
	 * @param userId
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public UserAttributeValue updateUserAttributeValue(String userId, String attributeName, String value) {
		User user = new User(userId);
		UserAttribute userAttribute = new UserAttribute(attributeName);
		
		UserAttributeValueId id = new UserAttributeValueId(user, userAttribute);
		//TODO: Modify this to use DB timestamp
		UserAttributeValue userAttributeValue = new UserAttributeValue(id, value, new Date());
		
		userAttributeValueDAO.merge(userAttributeValue);
		return userAttributeValue;
	}
	
	/**
	 * Fetches all attribute values for the given user
	 * @param userId
	 * @return
	 */
	public Map<String, String> getAllAttributesForUser(String userId) {
		List<UserAttributeValue> values = userAttributeValueDAO.getAllAttributeValuesForUser(userId);
		Map<String, String> attributeValueMap = new HashMap<String, String>();
		for(UserAttributeValue value : values) {
			attributeValueMap.put(value.getId().getUserAttribute().getAttributeName(), 
					value.getValue());
		}
		return attributeValueMap;
	}

	/**
	 * Fetches value of a given attribute for the given user
	 * @param user2
	 * @param userAttribute2
	 * @return
	 * @throws DatabaseException
	 */
	public UserAttributeValue getUserAttribute(User user, UserAttribute userAttribute) throws DatabaseException {
		UserAttributeValueId id = new UserAttributeValueId();
		id.setUser(user);
		id.setUserAttribute(userAttribute);
		
		if(user==null || userAttribute==null) return null;
		
		UserAttributeValue userAttributeValue = userAttributeValueDAO.findById(id);
		return userAttributeValue;
	}
	
}
