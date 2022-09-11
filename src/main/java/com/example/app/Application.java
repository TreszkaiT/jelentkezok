package com.example.app;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
//@Theme(value = Lumo.class, variant = Lumo.DARK)
/*
@Configuration
@EnableAutoConfiguration
@ComponentScan
*/
@SpringBootApplication
@Theme(value = "adatok")
//@Theme(value = "custom-theme")
@PWA(
        name = "Adatok bevitele App",
        shortName = "adatok",
        offlinePath="offline.html",
        offlineResources = {"images/logo.png", "images/offline.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
@ConfigurationProperties
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    //@Autowired
    //private Product product;

    public static void main(String[] args) {
        ConfigurableApplicationContext ct = SpringApplication.run(Application.class, args);
        //Product pr = ct.getBean("product", Product.class);
        //System.out.println(pr);
    }

    //@Override
    //public void run(String... args) throws Exception{
    //    System.out.println(product.getTitle());
    //    System.out.println(product.getVersion());
    //}

    // bármelyik Service rétegbe csinálok egy ilyet és behúzom a repository-kat. Javaban meg összeállítom az Entitásokat, amiket szeretnék. azokat perzisztálom
    /*@PostConstruct
    public void init(){

    }*/


}
