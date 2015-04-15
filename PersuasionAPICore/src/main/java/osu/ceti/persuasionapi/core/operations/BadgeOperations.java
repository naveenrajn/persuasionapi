package osu.ceti.persuasionapi.core.operations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.BadgeDAO;
import osu.ceti.persuasionapi.data.model.Badge;

@Component
public class BadgeOperations {
	
	private static final Logger log = Logger.getLogger(BadgeOperations.class);
	
	@Autowired BadgeDAO badgeDAO;

	public Badge lookupBadge(Integer badgeId) throws DatabaseException {
		return badgeDAO.findById(badgeId);
	}
}
