package org.vaadin.petter.hillamicro.portal.shell.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@Endpoint
@AnonymousAllowed
public class PortalShellEndpoint {

    private final DiscoveryClient discoveryClient;
    private final Environment environment;

    public PortalShellEndpoint(DiscoveryClient discoveryClient, Environment environment) {
        this.discoveryClient = Objects.requireNonNull(discoveryClient);
        this.environment = Objects.requireNonNull(environment);
    }

    public @Nonnull List<@Nonnull Frontend> getFrontends() {
        return discoveryClient
                .getServices()
                .stream()
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream().findFirst().stream())
                .filter(this::isFrontend)
                .map(this::serviceInstanceToFrontend)
                .toList();
    }

    private boolean isFrontend(ServiceInstance serviceInstance) {
        return serviceInstance.getMetadata().containsKey("frontend.title");
    }

    private Frontend serviceInstanceToFrontend(ServiceInstance serviceInstance) {
        var frontendId = serviceInstance.getServiceId();
        var title = serviceInstance.getMetadata().getOrDefault("frontend.title", frontendId);
        var url = serviceInstance.getUri().toString();
        var iconUrl = serviceInstance.getMetadata().get("frontend.icon");
        return new Frontend(frontendId, title, url, iconUrl);
    }

    public @Nonnull Frontend getSelf() {
        var frontendId = environment.getRequiredProperty("spring.application.name");
        return discoveryClient.getInstances(frontendId)
                .stream()
                .findFirst()
                .map(this::serviceInstanceToFrontend)
                .orElseThrow(() -> new IllegalStateException("Could not find self in Eureka"));
    }

    public void pushNotification(@Nonnull NotificationPriority priority, @Nonnull String message) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public @Nonnull Flux<@Nonnull FrontendNotification> getNotifications() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
