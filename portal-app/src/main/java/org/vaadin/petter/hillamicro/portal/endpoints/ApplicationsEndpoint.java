package org.vaadin.petter.hillamicro.portal.endpoints;

import java.util.List;

import org.vaadin.petter.hillamicro.portal.shared.ApplicationInfo;
import org.vaadin.petter.hillamicro.portal.shared.ApplicationInfoProvider;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

@Endpoint
@AnonymousAllowed
public class ApplicationsEndpoint {
    
    private final ApplicationInfoProvider applicationInfoProvider;

    public ApplicationsEndpoint(ApplicationInfoProvider applicationInfoProvider) {
        this.applicationInfoProvider = applicationInfoProvider;
    }

    public @Nonnull List<@Nonnull ApplicationInfo> getApplications() {
        return applicationInfoProvider.getCurrentApplications();
    }
}
