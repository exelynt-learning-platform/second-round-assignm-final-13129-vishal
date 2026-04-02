package com.ecommerce.project.service;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // ADD PRODUCT
    public Product add(Product product) {
        return repo.save(product);
    }

    // GET ALL PRODUCTS
    public List<Product> getAll() {
        return repo.findAll();
    }

    // UPDATE PRODUCT
    public Product update(Long id, Product product) {
        product.setId(id);
        return repo.save(product);
    }

    // DELETE PRODUCT
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
