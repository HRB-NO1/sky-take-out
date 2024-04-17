package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * Add the shopping cart
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * List the shopping cart
     * @return
     */
    List<ShoppingCart> listShoppingCart();

    /**
     * Clear the shopping cart
     */
    void clearShoppingCart();

    /**
     * Subtract the shopping cart
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
