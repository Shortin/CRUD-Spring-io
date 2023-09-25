package com.example.demo.service;

import com.example.demo.model.Products;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository productsRepositort;

    public List<Products> getAllProducts() {
        List<Products> productsList = new ArrayList<>();
        productsRepositort.findAll().forEach(products -> productsList.add(products));

        return productsList;
    }

    public Products getProductById(Long id) {
        return productsRepositort.findById(id).get();
    }

    public boolean saveOrUpdateProduct(Products product) {
        Products updatedProduct = productsRepositort.save(product);

        if (productsRepositort.findById(updatedProduct.getId()) != null) {
            return true;
        }

        return false;
    }

    public boolean deleteProduct(Long id) {
        productsRepositort.deleteById(id);

        if (productsRepositort.findById(id) != null) {
            return true;
        }

        return false;
    }
}