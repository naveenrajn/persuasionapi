package osu.ceti.persuasionapi.core.helpers;

import java.util.Map;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JMSMessageSender {

	public void sendJMSMessage(JmsTemplate jmsTemplate, final Map<String, String> object) {
		//TODO: Handle appropriate exception
		jmsTemplate.convertAndSend(object);
	}
}
