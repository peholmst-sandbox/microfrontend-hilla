package org.vaadin.petter.hillamicro.hello.endpoints;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import reactor.core.publisher.Flux;

@Endpoint
@AnonymousAllowed
public class ServerTimeEndpoint {

    public @Nonnull Flux<@Nonnull LocalDateTime> subscribe() {
        return Flux.interval(Duration.ofSeconds(1)).map(l -> getServerTime());
    }

    public @Nonnull LocalDateTime getServerTime() {
        return LocalDateTime.now();
    }

    public @Nonnull String getServerUser() {
        var context = SecurityContextHolder.getContext();
        var auth = context.getAuthentication();
        if (auth == null) {
            return "n/a";
        } else {
            return auth.getName();
        }
    }
}
