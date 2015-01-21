package osu.ceti.persuasionapi.data.access;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;

import osu.ceti.persuasionapi.data.model.RuleComparators;

/**
 * DAO for domain model RuleComparators
 * @see osu.ceti.persuasionapi.data.model.RuleComparators
 */
public class RuleComparatorsDAO {

	private static final Log log = LogFactory.getLog(RuleComparatorsDAO.class);

	@Autowired SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(RuleComparators transientInstance) {
		log.debug("persisting RuleComparators instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RuleComparators instance) {
		log.debug("attaching dirty RuleComparators instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RuleComparators instance) {
		log.debug("attaching clean RuleComparators instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RuleComparators persistentInstance) {
		log.debug("deleting RuleComparators instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RuleComparators merge(RuleComparators detachedInstance) {
		log.debug("merging RuleComparators instance");
		try {
			RuleComparators result = (RuleComparators) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RuleComparators findById(java.lang.String id) {
		log.debug("getting RuleComparators instance with id: " + id);
		try {
			RuleComparators instance = (RuleComparators) sessionFactory
					.getCurrentSession().get(
							"osu.ceti.persuasionapi.data.model.RuleComparators", id);
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

	public List findByExample(RuleComparators instance) {
		log.debug("finding RuleComparators instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.RuleComparators")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
