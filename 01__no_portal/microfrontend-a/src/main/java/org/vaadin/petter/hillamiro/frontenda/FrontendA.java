package org.vaadin.petter.hillamiro.frontenda;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.vaadin.petter.hillamicro.portal.shell.PortalShellConfig;

@SpringBootApplication
@Import(PortalShellConfig.class)
@Theme("frontend-a")
public class FrontendA implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(FrontendA.class, args);
    }
}
