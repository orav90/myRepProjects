package com.myshop.controller;

import com.myshop.dto.Product;
import com.myshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/api/products")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/api/products/{id}")
    public Product getProducts(@PathVariable String id){
        return productService.getProductById(id);
    }


}
