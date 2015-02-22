package osu.ceti.persuasionapi;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import osu.ceti.persuasionapi.client.Configuration;

public class PersuasionAPIClient {

	/**
	 * Initializes the client configuration
	 * @param contextPath
	 */
	public void initialize(String contextPath) {
		Configuration.contextPath = contextPath;
		Configuration.applicationContext = new ClassPathXmlApplicationContext("spring/persuasionapi-client-config.xml");
		Configuration.restTemplate = Configuration.applicationContext.getBean(RestTemplate.class);
	}

}
