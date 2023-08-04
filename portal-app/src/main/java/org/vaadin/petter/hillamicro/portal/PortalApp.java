package org.vaadin.petter.hillamicro.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

@SpringBootApplication
@Theme("portal-app")
public class PortalApp implements AppShellConfigurator {
    
    public static void main(String[] args) {
        SpringApplication.run(PortalApp.class, args);
    }
}
