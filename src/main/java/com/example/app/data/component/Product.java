package com.example.app.data.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.properties")
public class Product {

    @Value("${product.title}")
    private String title;

    @Value("${product.version}")
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
