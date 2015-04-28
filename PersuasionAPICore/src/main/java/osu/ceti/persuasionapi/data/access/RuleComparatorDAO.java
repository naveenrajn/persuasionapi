package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.RuleComparator;

/**
 * DAO for domain model RuleComparator
 * @see osu.ceti.persuasionapi.data.model.RuleComparator
 */
public class RuleComparatorDAO {

	private static final Logger log = Logger.getLogger(RuleComparatorDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(RuleComparator transientInstance) {
		log.debug("persisting RuleComparator instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RuleComparator instance) {
		log.debug("attaching dirty RuleComparator instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RuleComparator instance) {
		log.debug("attaching clean RuleComparator instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RuleComparator persistentInstance) {
		log.debug("deleting RuleComparator instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RuleComparator merge(RuleComparator detachedInstance) {
		log.debug("merging RuleComparator instance");
		try {
			RuleComparator result = (RuleComparator) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RuleComparator findById(java.lang.String id) {
		log.debug("getting RuleComparator instance with id: " + id);
		try {
			RuleComparator instance = (RuleComparator) sessionFactory
					.getCurrentSession().get(
							"osu.ceti.persuasionapi.data.model.RuleComparator",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RuleComparator instance) {
		log.debug("finding RuleComparator instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.RuleComparator")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List getAllRuleComparators() throws DatabaseException {
		log.debug("retrieving all RuleComparator instances");
		try {
			List results = 
					sessionFactory.getCurrentSession().createCriteria(RuleComparator.class).list();
			log.debug("retrieve all RuleComparator instance succesful, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("Failed to retrieve all RuleComparator instances"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve all Rule Comparators from database", e);
		}
	}
}
