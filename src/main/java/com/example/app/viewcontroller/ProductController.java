package com.example.app.viewcontroller;

import com.example.app.service.component.Product;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    private Product product;

    public ProductController(Product product) {
        this.product = product;
    }

    public Double getProductVersion(){
        return product.getVersion();
    }
}
