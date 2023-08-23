package org.vaadin.petter.hillamicro.frontendb;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.vaadin.petter.hillamicro.portal.shell.PortalShellConfig;

@SpringBootApplication
@Import(PortalShellConfig.class)
@Theme("frontend-b")
public class FrontendB implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(FrontendB.class, args);
    }
}
