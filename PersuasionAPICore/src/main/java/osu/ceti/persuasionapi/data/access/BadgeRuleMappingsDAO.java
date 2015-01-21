package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.BadgeRuleMappings;

/**
 * DAO for domain model BadgeRuleMappings
 * @see osu.ceti.persuasionapi.data.model.BadgeRuleMappings
 */
public class BadgeRuleMappingsDAO {

	private static final Log log = LogFactory.getLog(BadgeRuleMappingsDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public BadgeRuleMappings merge(BadgeRuleMappings detachedInstance) throws DatabaseException {
		log.debug("merging BadgeRuleMappings instance");
		try {
			BadgeRuleMappings result = (BadgeRuleMappings) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("merge BadgeRuleMappings to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to persist/merge BadgeRuleMappings to database", e);
		}
	}

	public List getAllBadgeRuleMappings() throws DatabaseException {
		log.debug("finding BadgeRuleMappings instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(BadgeRuleMappings.class)
					.addOrder(Order.asc("ruleOrder")).list();
			log.debug("get all badge rule mappings successful, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("Failed to retrieve all badge rule mappingsfrom database: "
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve all badge rule mappings "
					+ "from database", e);
		}
	}
}
