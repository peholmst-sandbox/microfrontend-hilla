package org.vaadin.petter.hillamicro.portal.shell.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class FrontendNotificationSender {

    private static final Logger log = LoggerFactory.getLogger(FrontendNotificationSender.class);
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public FrontendNotificationSender(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(FrontendNotification frontendNotification) {
        try {
            var json = objectMapper.writer().writeValueAsString(frontendNotification);
            rabbitTemplate.convertAndSend(json);
        } catch (JsonProcessingException ex) {
            log.error("Could not convert message to JSON", ex);
        }
    }
}
