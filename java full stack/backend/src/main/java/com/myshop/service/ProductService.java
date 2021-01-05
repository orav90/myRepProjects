package com.myshop.service;

import com.myshop.dto.Product;
import com.myshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }


    public Product getProductById(String id) {
        //return products.stream().filter(prod -> prod.get_id().equals(id)).findFirst().get();
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException (
                HttpStatus.NOT_FOUND, "Foo Not Found"));
    }
}
