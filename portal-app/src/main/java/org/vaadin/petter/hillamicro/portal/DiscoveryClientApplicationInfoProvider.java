package org.vaadin.petter.hillamicro.portal;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.vaadin.petter.hillamicro.portal.shared.ApplicationInfo;
import org.vaadin.petter.hillamicro.portal.shared.ApplicationInfoProvider;

@Service
class DiscoveryClientApplicationInfoProvider implements ApplicationInfoProvider {

    private static final String METADATA_TITLE = "microfrontend.title";
    private static final String METADATA_ICON = "microfrontend.icon";
    private static final String METADATA_PATH = "microfrontend.path";
    private static final String METADATA_IMPORT_PATH = "microfrontend.importPath";
    private static final String METADATA_REQUIRES_AUTHENTICATION = "microfrontend.requiresAuthentication";
    private static final String METADATA_TAG = "microfrontend.tag";
    private static final String METADATA_MODULE_NAME = "microfrontend.moduleName";

    private final DiscoveryClient discoveryClient;

    DiscoveryClientApplicationInfoProvider(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<ApplicationInfo> getCurrentApplications() {
        return discoveryClient
                .getServices().stream()
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream().filter(this::isApplication)
                        .findFirst().stream())
                .map(this::toApplicationInfo)
                .toList();
    }

    private boolean isApplication(ServiceInstance serviceInstance) {
        return serviceInstance.getMetadata().containsKey(METADATA_TITLE)
             && serviceInstance.getMetadata().containsKey(METADATA_IMPORT_PATH);
    }

    private ApplicationInfo toApplicationInfo(ServiceInstance serviceInstance) {
        var title = serviceInstance.getMetadata().get(METADATA_TITLE);
        var path = Optional.ofNullable(serviceInstance.getMetadata().get(METADATA_PATH)).orElse("/" + serviceInstance.getServiceId().toLowerCase());
        var icon = Optional.ofNullable(serviceInstance.getMetadata().get(METADATA_ICON)).orElse("");
        var importPath = serviceInstance.getMetadata().get(METADATA_IMPORT_PATH);
        var requiresAuthentication = Boolean.parseBoolean(serviceInstance.getMetadata().getOrDefault(METADATA_REQUIRES_AUTHENTICATION, "true"));
        var tag = Optional.ofNullable(serviceInstance.getMetadata().get(METADATA_TAG)).orElse(serviceInstance.getServiceId().toLowerCase());
        var moduleName = Optional.ofNullable(serviceInstance.getMetadata().get(METADATA_MODULE_NAME)).orElse("./" + tag);
        return new ApplicationInfo(serviceInstance.getServiceId(), title, path, icon, importPath, tag, moduleName, requiresAuthentication);
    }

}
