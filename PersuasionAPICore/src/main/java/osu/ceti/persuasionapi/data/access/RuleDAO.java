package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Rule;

/**
 * DAO for domain model Rule
 * @see osu.ceti.persuasionapi.data.model.Rule
 */
public class RuleDAO {

	private static final Log log = LogFactory.getLog(RuleDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(Rule transientInstance) {
		log.debug("persisting Rule instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Rule instance) {
		log.debug("attaching dirty Rule instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Rule instance) {
		log.debug("attaching clean Rule instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Rule persistentInstance) {
		log.debug("deleting Rule instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Rule merge(Rule detachedInstance) {
		log.debug("merging Rule instance");
		try {
			Rule result = (Rule) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Rule findById(java.lang.Integer id) throws DatabaseException {
		log.debug("getting Rule instance with id: " + id);
		try {
			Rule instance = (Rule) sessionFactory.getCurrentSession().get(
					Rule.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find Rule by Id failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve Rule from database", e);
		}
	}

	public List findByExample(Rule instance) {
		log.debug("finding Rule instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("osu.ceti.persuasionapi.data.model.Rule")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List getAllRules() {
		log.debug("retrieving all rules");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(Rule.class).list();
			log.debug("getAllRules, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			//TODO: Add appropriate exception handling
			log.error("getAllRules failed", re);
			throw re;
		}
	}
	
	public List getAllTopLevelRules() {
		log.debug("retrieving all rules");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(Rule.class).
					add(Restrictions.isNull("parentRule")).list();
			log.debug("getAllRules, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			//TODO: Add appropriate exception handling
			log.error("getAllRules failed", re);
			throw re;
		}
	}
}
