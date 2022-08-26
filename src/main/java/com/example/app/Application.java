package com.example.app;

import com.example.app.data.component.Product;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "adatok")
@PWA(name = "adatok", shortName = "adatok", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
@ConfigurationProperties
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        ConfigurableApplicationContext ct = SpringApplication.run(Application.class, args);
        //Product pr = ct.getBean("product", Product.class);
        //System.out.println(pr);
    }

    // bármelyik Service rétegbe csinálok egy ilyet és behúzom a repository-kat. Javaban meg összeállítom az Entitásokat, amiket szeretnék. azokat perzisztálom
    /*@PostConstruct
    public void init(){

    }*/

}
