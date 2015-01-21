package osu.ceti.persuasionapi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.operations.ActivityLogOperations;
import osu.ceti.persuasionapi.data.access.BadgeRuleMappingsDAO;
import osu.ceti.persuasionapi.data.model.BadgeRuleMappings;

public class TestMain {

	public static void main(String[] args) throws DatabaseException {
		// TODO Auto-generated method stub
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/persuasionapi-core-config.xml");
        BadgeRuleMappingsDAO badgeRuleMappingsDAO = context.getBean(BadgeRuleMappingsDAO.class);
        System.out.println("Size: " + badgeRuleMappingsDAO.getAllBadgeRuleMappings().size());
	}

}
