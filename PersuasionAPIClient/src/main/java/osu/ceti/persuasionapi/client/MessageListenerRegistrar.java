package osu.ceti.persuasionapi.client;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import osu.ceti.persuasionapi.client.exceptions.InvalidConfigurationException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;

public class MessageListenerRegistrar {
	
	private static final Logger log = Logger.getLogger(MessageListenerRegistrar.class);

	/**
	 * Creates and registers a new listener for the given queue
	 * @param queueName
	 * @param listener class type
	 * @return the listener after registering
	 * @throws PersuasionAPIException
	 */
	public static MessageListener registerListener(String queueName, Class listener) 
			throws PersuasionAPIException {
		if(!Configuration.isValid()) {
			throw new InvalidConfigurationException();
		}
		
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) Configuration
				.applicationContext.getAutowireCapableBeanFactory();
		
		Object placeHolder = null;
	    try {
	        placeHolder = Configuration.applicationContext.getBean(queueName);
	    } catch (BeansException e) {
	    }
	    if(placeHolder==null) {
	        BeanDefinitionBuilder queueBuilder = 
	        		BeanDefinitionBuilder.rootBeanDefinition(ActiveMQQueue.class);
	        queueBuilder.addConstructorArgValue(queueName);
	        factory.registerBeanDefinition(queueName, queueBuilder.getBeanDefinition());
	    }
	    
	    //Creating listener bean
	    String listenerName = listenerName(queueName);
	    if(!MessageListener.class.isAssignableFrom(listener)) {
	    	//TODO: Throw appropriate error
	    	throw new PersuasionAPIException("", "Listener not a derivative of MessageListener");
	    }
	    BeanDefinitionBuilder listenerBuilder = 
	    		BeanDefinitionBuilder.rootBeanDefinition(listener);
	    listenerBuilder.addConstructorArgValue(queueName);
	    factory.registerBeanDefinition(listenerName, listenerBuilder.getBeanDefinition());

	    //Creating adapter bean
	    String beanName = listenerAdapterName(queueName);
	    BeanDefinitionBuilder listenerAdapterBuilder = 
	    		BeanDefinitionBuilder.rootBeanDefinition(MessageListenerAdapter.class);
	    listenerAdapterBuilder.addPropertyReference("delegate", listenerName);
	    listenerAdapterBuilder.addPropertyValue("defaultListenerMethod", null);
	    factory.registerBeanDefinition(beanName, listenerAdapterBuilder.getBeanDefinition());

	    //Creating container bean 
	    BeanDefinitionBuilder listenerContainerBuilder = BeanDefinitionBuilder
	            .rootBeanDefinition(DefaultMessageListenerContainer.class);
	    listenerContainerBuilder.addPropertyValue("destinationName", queueName);
	    //TODO: Move activemqConnectionFactory from xml to code configuration
	    listenerContainerBuilder.addPropertyReference("connectionFactory", 
	    		"activemqConnectionFactory");
	    listenerContainerBuilder.addPropertyReference("messageListener", beanName);
	    listenerContainerBuilder.addPropertyValue("cacheLevelName", "CACHE_AUTO");
	    listenerContainerBuilder.addPropertyValue("autoStartup", true);
	    listenerContainerBuilder.addPropertyValue("sessionAcknowledgeMode", 1);
	    String containerName = listenerContainerName(queueName);
	    factory.registerBeanDefinition(containerName, listenerContainerBuilder.getBeanDefinition());
	    DefaultMessageListenerContainer bean = (DefaultMessageListenerContainer) Configuration
	    		.applicationContext.getBean(containerName);
	    bean.afterPropertiesSet();
	    
	    return (MessageListener) Configuration.applicationContext.getBean(listenerName);
	}
	
	/**
	 * Unregisters and destroys the listeners for the given queue
	 * Cannot be used to start listening again; destroyed to prevent resource leak
	 * Re-register them using registerListener() in order to start listening again
	 * @param queueName
	 * @throws InvalidConfigurationException
	 */
	public static void unregisterListener(String queueName) throws InvalidConfigurationException {
		if(!Configuration.isValid()) {
			throw new InvalidConfigurationException();
		}
		
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) Configuration
				.applicationContext.getAutowireCapableBeanFactory();
		try {
			factory.removeBeanDefinition(listenerContainerName(queueName));
			factory.removeBeanDefinition(listenerAdapterName(queueName));
			factory.removeBeanDefinition(listenerName(queueName));
		} catch(NoSuchBeanDefinitionException e) {
			log.info("No listeners exists for queue: " + queueName);
		} catch(Exception e) {
			//TODO: Handle exception appropriately
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the listener for the given queue
	 * @param queueName
	 * @return listener
	 */
	public static MessageListener getListener(String queueName) {
		try {
			String listenerName = listenerName(queueName);
			return (MessageListener) Configuration.applicationContext.getBean(listenerName);
		} catch(NoSuchBeanDefinitionException e) {
			log.info("No listener exists for queue: " + queueName);
		} catch(Exception e) {
			//TODO: Handle exception appropriately
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Helper to build listener name from queue name
	 * @param queueName
	 * @return listener name
	 */
	private static String listenerName(String queueName) {
		return queueName + ".dynamicListener";
	}
	
	/**
	 * Helper to build listener adapter name from queue name
	 * @param queueName
	 * @return listener adapter name
	 */
	private static String listenerAdapterName(String queueName) {
		return queueName + ".dynamicAdapterListener";
	}
	
	/**
	 * Helper to build listener container name from queue name
	 * @param queueName
	 * @return listener container name
	 */
	private static String listenerContainerName(String queueName) {
		return queueName + ".listenerContainer";
	}

}
