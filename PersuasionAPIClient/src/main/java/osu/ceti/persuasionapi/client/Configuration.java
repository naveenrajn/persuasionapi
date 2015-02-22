package osu.ceti.persuasionapi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import osu.ceti.persuasionapi.core.helpers.StringHelper;

/**
 * Client configuration store
 *
 */
public class Configuration {
	
	public static String contextPath = null;
	public static RestTemplate restTemplate = null;
	public static ApplicationContext applicationContext = null;
	//TODO: Add authentication variables
	
	/**
	 * Checks if the configuration is valid
	 * i.e. whether it contains all required configuration items populated
	 * @return true/false
	 */
	public static boolean isValid() {
		if(StringHelper.isEmpty(contextPath)) {
			return false;
		}
		
		if(restTemplate == null) {
			return false;
		}
		
		if(applicationContext == null) {
			return false;
		}
		
		return true;
	}
	
}
