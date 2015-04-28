package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.RuleAction;

/**
 * DAO for domain model RuleAction
 * @see osu.ceti.persuasionapi.data.model.RuleAction
 */
public class RuleActionDAO {

	private static final Logger log = Logger.getLogger(RuleActionDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(RuleAction transientInstance) {
		log.debug("persisting RuleAction instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RuleAction instance) {
		log.debug("attaching dirty RuleAction instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RuleAction instance) {
		log.debug("attaching clean RuleAction instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RuleAction persistentInstance) {
		log.debug("deleting RuleAction instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RuleAction merge(RuleAction detachedInstance) throws DatabaseException {
		log.debug("merging RuleAction instance");
		try {
			RuleAction result = (RuleAction) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("Failed to create/update rule action" + detachedInstance.toString()
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to update rule action for rule", e);
		}
	}

	public List findByExample(RuleAction instance) {
		log.debug("finding RuleAction instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.RuleAction")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void nullifyAllAssignmentsForBadge(Integer badgeId) throws DatabaseException {
		log.debug("setting all action mappings for badge to null");
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"update RuleAction set badge.badgeId=:newValue where badge.badgeId=:oldValue");
			query.setParameter("oldValue", badgeId);
			query.setParameter("newValue", null);
			query.executeUpdate();
			log.debug("setting all action mappings for badge to null. Badge Id: " + badgeId); 
		} catch (Exception e) {
			log.error("Failed to update null action mappings. Badge Id: " + badgeId 
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed update badge class: " + e.getMessage());
		}
	}

	public void deleteAllActionsForRule(Integer ruleId) throws DatabaseException {
		log.debug("deleting all rule actions for rule");
		try {
			String queryString = "delete from RuleAction where ruleId=:ruleId";
			Query query = sessionFactory.getCurrentSession().createQuery(queryString);
			query.setParameter("ruleId", ruleId);
			query.executeUpdate();
			log.debug("delete all rule actions for rule succesful. Rule ID: " + ruleId); 
		} catch (Exception e) {
			log.error("Failed to delete rule actions for rule: " + ruleId 
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to delete rule actions for rule: " + ruleId
					+ ". Error: " + e.getMessage());
		}
	}
}
