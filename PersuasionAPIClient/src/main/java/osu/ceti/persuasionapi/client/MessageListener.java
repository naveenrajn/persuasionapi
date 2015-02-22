package osu.ceti.persuasionapi.client;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;

public abstract class MessageListener implements javax.jms.MessageListener {

	String queueName = null;

	public MessageListener(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * Starts the listener to listed to 'queueName'
	 */
	public void start() {
		String containerName = queueName + ".listenerContainer";
		DefaultMessageListenerContainer bean = (DefaultMessageListenerContainer) Configuration
				.applicationContext.getBean(containerName);
		bean.start();
	}

	/**
	 * Stops listening to 'queueName'
	 * The listener instance will still be alive and can be started later
	 */
	public void stop() {
		String containerName = queueName + ".listenerContainer";
		DefaultMessageListenerContainer bean = (DefaultMessageListenerContainer) Configuration
				.applicationContext.getBean(containerName);
		bean.stop();
	}

	/**
	 * Calls the implementations handleMessage method
	 */
	public void onMessage(Message message) {
		try {
			if(message instanceof MapMessage) {
				MapMessage mapMessage = (MapMessage) message;
				SimpleMessageConverter messageConverter = new SimpleMessageConverter();
				Map<String, String> map = 
						(Map<String, String>) messageConverter.fromMessage(mapMessage);
				handleMessage(map);
			}
		} catch (MessageConversionException e) {
			// TODO Handle exception appropriately
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Handle exception appropriately
			e.printStackTrace();
		} finally {

		}
		//TODO: Handle error appropriately
		throw new RuntimeException("Message not map message");
	}

	/**
	 * To be provided by implementation - this will be called when a message arrives in 'queueName'
	 * @param messageContents
	 */
	abstract void handleMessage(final Map<String, String> messageContents);

}
