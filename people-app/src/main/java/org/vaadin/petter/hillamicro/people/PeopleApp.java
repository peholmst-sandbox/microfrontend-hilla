package org.vaadin.petter.hillamicro.people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.NoTheme;

@SpringBootApplication
@NoTheme
public class PeopleApp implements AppShellConfigurator {
    
    public static void main(String[] args) {
        SpringApplication.run(PeopleApp.class, args);
    }
}
