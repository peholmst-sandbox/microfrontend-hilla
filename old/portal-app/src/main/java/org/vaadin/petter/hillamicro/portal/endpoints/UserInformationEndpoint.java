package org.vaadin.petter.hillamicro.portal.endpoints;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.petter.hillamicro.portal.UserInformation;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

@Endpoint
@AnonymousAllowed
public class UserInformationEndpoint {
    
    public @Nonnull Optional<@Nonnull UserInformation> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
        .map(auth -> new UserInformation(auth.getName(), "DisplayName of " + auth.getName()));
    }
}
