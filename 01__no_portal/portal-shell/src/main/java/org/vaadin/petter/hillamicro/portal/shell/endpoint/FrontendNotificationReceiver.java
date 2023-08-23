package org.vaadin.petter.hillamicro.portal.shell.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;

public class FrontendNotificationReceiver implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(FrontendNotificationReceiver.class);
    private final ObjectMapper objectMapper;
    private final Sinks.Many<FrontendNotification> sink;

    public FrontendNotificationReceiver(ObjectMapper objectMapper, Sinks.Many<FrontendNotification> sink) {
        this.objectMapper = objectMapper;
        this.sink = sink;
    }

    @Override
    public void onMessage(Message message) {
        try {
            FrontendNotification notification = objectMapper.readerFor(FrontendNotification.class).readValue(message.getBody());
            log.debug("Received {}", notification);
            sink.tryEmitNext(notification).orThrow();
        } catch (IOException ex) {
            log.error("Error decoding message", ex);
        } catch (Sinks.EmissionException ex) {
            log.warn("Error emitting notification", ex);
        }
    }

    public Flux<FrontendNotification> notifications() {
        return sink.asFlux();
    }
}
