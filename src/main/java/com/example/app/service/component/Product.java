package com.example.app.service.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component          // = @Service is lehetne == ez egy Bean, amit már jegyez az ApplicationContextContainer, azza Mr. Container az Application.class-ban, így már Dependency Injection-nal behúzhatom pl. MainLayout.class-nak a Setter-ébe vagy a Constructorába
//@ConfigurationProperties(prefix = "application.properties")
@ConfigurationProperties(prefix = "product")         // automatikus összekötéshez: az application.properties-ben lévő prefixet adom meg, mely ennek az osztálynak a neve is: ez köti össze az application.properties-ben lévő product. -al kezdődő value-ket az itt lévő value-kkel
public class Product {

    //@Value("${product.title}")                // ebben a második esetben nem kell a @Value, mert a pom.xml-ben betett dependency: spring-boot-configuration-processor automatikusan összeköti a neveket, a változókkal az application.properties-ben
    private String title;

    //@Value("${product.version}")  //@Value("version")
    private Double version;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", version=" + version +
                '}';
    }
}
