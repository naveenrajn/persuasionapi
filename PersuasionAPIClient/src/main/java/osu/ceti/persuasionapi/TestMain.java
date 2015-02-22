package osu.ceti.persuasionapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import osu.ceti.persuasionapi.client.MessageListener;
import osu.ceti.persuasionapi.client.MessageListenerRegistrar;
import osu.ceti.persuasionapi.services.ActivityService;
import osu.ceti.persuasionapi.services.BadgeService;
import osu.ceti.persuasionapi.services.UserAttributeService;
import osu.ceti.persuasionapi.services.wrappers.GetUserAttributeResponse;
import osu.ceti.persuasionapi.services.wrappers.GetUserBadgeResponse;

/**
 * Test class. To be converted to junit tests
 * @author Naveenraj Nagarathinam
 *
 */
public class TestMain {

	public static void main(String[] args) {
		// TODO Convert all of these to unit tests
		try {
			
			PersuasionAPIClient client = new PersuasionAPIClient();
			client.initialize("http://localhost:8080/PersuasionAPI");
			
			MessageListenerRegistrar.unregisterListener("asd");
			
			//Test /badges/getAllBadgesForUser
			/*BadgeService badgeService = new BadgeService();
			List<GetUserBadgeResponse> response = badgeService.getAllBadgesForUser("2");
			System.out.println("# of badges the user has: " + response.size());
			for(GetUserBadgeResponse badge : response) {
				System.out.println("Badge: " + badge.getBadgeLevel());
			}*/
			
			//Test /badges/getUserBadgeForClass
			/*BadgeService badgeService = new BadgeService();
			GetUserBadgeResponse response = badgeService.getUserBadge("2", "reviewer");
			System.out.println("Get user badge: " + response.getBadgeLevel());*/
			
			/*String queueName = "osu.ceti.persuasionapi.internal.activityreported";
			MessageListenerRegistrar.registerListener(queueName, ActivityReportedMessageListener.class);
			MessageListener listener = MessageListenerRegistrar.getListener(queueName);
			listener.start();
			
			boolean flag = true;
			while(true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		        System.out.print("Enter String");
		        String s = br.readLine();
				if(flag) {
					System.out.println("Stopping listener");
					flag = false;
					listener.stop();
				}
				else {
					System.out.println("Starting listener");
					flag = true;
					listener.start();
				}
				
			}*/
			
			//Test /activity/report
			/*ActivityService activityService = new ActivityService();
			activityService.reportActivity("1", "review_posted", null);*/
			
			//Test /user/attribute/update
			/*UserAttributeService userAttributeService = new UserAttributeService();
			userAttributeService.updateUserAttribute("1", "user_type", "reviewer");*/
			
			//Test /user/attribute/getUserAttributeValue
			/*UserAttributeService userAttributeService = new UserAttributeService();
			GetUserAttributeResponse response = 
					userAttributeService.getAttributeValueForUser("1", "user_type");
			System.out.println("User Attribute value: " + response.getValue());*/
			
			System.out.println("No exception. Everything went well... :)");
		} catch(Exception e) {
			System.out.println("Type: " + e.getClass());
			System.out.println("message: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
