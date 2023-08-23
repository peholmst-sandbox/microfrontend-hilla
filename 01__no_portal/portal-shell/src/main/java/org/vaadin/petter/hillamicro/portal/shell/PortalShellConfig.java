package org.vaadin.petter.hillamicro.portal.shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.vaadin.petter.hillamicro.portal.shell.endpoint.FrontendNotificationReceiver;
import org.vaadin.petter.hillamicro.portal.shell.endpoint.FrontendNotificationSender;
import org.vaadin.petter.hillamicro.portal.shell.endpoint.PortalShellEndpoint;
import reactor.core.publisher.Sinks;

@Configuration
public class PortalShellConfig {

    private static final Logger log = LoggerFactory.getLogger(PortalShellConfig.class);

    public PortalShellConfig() {
        log.info("Initializing portal shell");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("portal_notifications_fanout");
    }

    @Bean
    public Queue incomingNotificationsQueue() {
        return QueueBuilder.nonDurable().exclusive().autoDelete().build();
    }

    @Bean
    public Binding binding(Queue incomingNotificationsQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(incomingNotificationsQueue).to(fanoutExchange);
    }

    @Bean
    public FrontendNotificationSender frontendNotificationSender(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setExchange("portal_notifications_fanout");
        return new FrontendNotificationSender(objectMapper, rabbitTemplate);
    }

    @Bean
    public FrontendNotificationReceiver frontendNotificationReceiver(ObjectMapper objectMapper) {
        return new FrontendNotificationReceiver(objectMapper, Sinks.many().multicast().directBestEffort());
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                   Queue incomingNotificationsQueue,
                                                                   FrontendNotificationReceiver frontendNotificationReceiver) {
        var container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(incomingNotificationsQueue.getName());
        container.setMessageListener(frontendNotificationReceiver);
        return container;
    }

    @Bean
    public PortalShellEndpoint portalShellEndpoint(DiscoveryClient discoveryClient, Environment environment,
                                                   FrontendNotificationSender frontendNotificationSender,
                                                   FrontendNotificationReceiver frontendNotificationReceiver) {
        return new PortalShellEndpoint(discoveryClient, environment, frontendNotificationSender, frontendNotificationReceiver);
    }
}
