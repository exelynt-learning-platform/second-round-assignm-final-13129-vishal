package com.ecommerce.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser_Id(Long userId);

    Optional<CartItem> findByUser_IdAndProduct_Id(Long userId, Long productId);
}