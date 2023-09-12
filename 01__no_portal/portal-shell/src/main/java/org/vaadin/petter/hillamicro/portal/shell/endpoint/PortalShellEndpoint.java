package org.vaadin.petter.hillamicro.portal.shell.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class PortalShellEndpoint {

    private final DiscoveryClient discoveryClient;
    private final Environment environment;
    private final FrontendNotificationSender frontendNotificationSender;
    private final FrontendNotificationReceiver frontendNotificationReceiver;

    public PortalShellEndpoint(DiscoveryClient discoveryClient, Environment environment,
                               FrontendNotificationSender frontendNotificationSender,
                               FrontendNotificationReceiver frontendNotificationReceiver) {
        this.discoveryClient = Objects.requireNonNull(discoveryClient);
        this.environment = Objects.requireNonNull(environment);
        this.frontendNotificationSender = Objects.requireNonNull(frontendNotificationSender);
        this.frontendNotificationReceiver = Objects.requireNonNull(frontendNotificationReceiver);
    }

    public @Nonnull List<@Nonnull Frontend> getFrontends() {
        return discoveryClient
                .getServices()
                .stream()
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream().findFirst().stream())
                .filter(this::isFrontend)
                .map(this::serviceInstanceToFrontend)
                .sorted(Comparator.comparing(Frontend::frontendId))
                .toList();
    }

    private boolean isFrontend(ServiceInstance serviceInstance) {
        return serviceInstance.getMetadata().containsKey("frontend.title");
    }

    private Frontend serviceInstanceToFrontend(ServiceInstance serviceInstance) {
        var frontendId = serviceInstance.getServiceId();
        var title = serviceInstance.getMetadata().getOrDefault("frontend.title", frontendId);
        var url = serviceInstance instanceof EurekaServiceInstance eurekaServiceInstance ? eurekaServiceInstance.getInstanceInfo().getHomePageUrl() : serviceInstance.getUri().toString();
        var iconUrl = serviceInstance.getMetadata().get("frontend.icon");
        return new Frontend(frontendId, title, url, iconUrl);
    }

    public @Nonnull Frontend getSelf() {
        // TODO This will result in an exception if called before the registration with Eureka is complete.
        var frontendId = environment.getRequiredProperty("spring.application.name");
        return discoveryClient.getInstances(frontendId)
                .stream()
                .findFirst()
                .map(this::serviceInstanceToFrontend)
                .orElseThrow(() -> new IllegalStateException("Could not find self in Eureka"));
    }

    public void pushNotification(@Nonnull NotificationPriority priority, @Nonnull String message) {
        var self = getSelf();
        var notification = new FrontendNotification(self.frontendId(), self.url(), priority, message);
        frontendNotificationSender.send(notification);
    }

    public @Nonnull Flux<@Nonnull FrontendNotification> getNotifications() {
        return frontendNotificationReceiver.notifications();
    }

    public @Nonnull PortalUser getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(this::authenticationToPortalUser)
                .orElseThrow(() -> new AccessDeniedException("No current user"));
    }

    private PortalUser authenticationToPortalUser(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwt) {
            return new PortalUser(Optional.ofNullable((String) jwt.getTokenAttributes().get("preferred_username")).orElse(authentication.getName()));
        }
        return new PortalUser(authentication.getName());
    }
}
