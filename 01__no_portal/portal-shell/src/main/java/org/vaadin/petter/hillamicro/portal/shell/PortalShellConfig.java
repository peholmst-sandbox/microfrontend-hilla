package org.vaadin.petter.hillamicro.portal.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.vaadin.petter.hillamicro.portal.shell.endpoint.PortalShellEndpoint;

@Configuration
public class PortalShellConfig {

    private static final Logger log = LoggerFactory.getLogger(PortalShellConfig.class);

    public PortalShellConfig() {
        log.info("Initializing portal shell");
    }

    @Bean
    public PortalShellEndpoint portalShellEndpoint(DiscoveryClient discoveryClient, Environment environment) {
        return new PortalShellEndpoint(discoveryClient, environment);
    }
}
