package com.ecommerce.project.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository repo;
    
    @Autowired
    private ProductRepository productRepository;

    // ADD TO CART
    @Transactional
    public CartItem add(CartItem item) {

        if (item == null || item.getProduct() == null || item.getProduct().getId() == null) {
            throw new IllegalArgumentException("Invalid product data");
        }

        if (item.getUser() == null || item.getUser().getId() == null) {
            throw new IllegalArgumentException("User information is required");
        }

        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        Long productId = item.getProduct().getId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existing = repo.findByUser_IdAndProduct_Id(
                item.getUser().getId(), productId
        ).orElse(null);

        if (existing != null) {

            int newQty = existing.getQuantity() + item.getQuantity();

            if (product.getStock() < newQty) {
                throw new RuntimeException("Out of stock");
            }

            existing.setQuantity(newQty);
            return repo.save(existing);
        }

        if (product.getStock() < item.getQuantity()) {
            throw new RuntimeException("Out of stock");
        }

        item.setProduct(product);

        return repo.save(item);
    }
    
    // GET USER CART
    public List<CartItem> get(User user) {
    	if (user == null || user.getId() == null) {
    	    throw new IllegalArgumentException("Invalid user");
    	}
    	return repo.findByUser_Id(user.getId())
    	           .stream()
    	           .toList();    
    }

    // REMOVE ITEM
    @Transactional
    public void remove(Long id, User user){
        CartItem item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // 🔴 SECURITY CHECK
        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        repo.delete(item);
    }
}
