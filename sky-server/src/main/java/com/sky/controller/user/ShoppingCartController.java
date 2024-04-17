package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "Shopping Cart API")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Add the shopping cart
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("Add the shopping cart")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("Add the shopping cart: {}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * Show the shopping cart
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("List the shopping cart")
    public Result<List<ShoppingCart>> list() {
        List<ShoppingCart> shoppingCartList = shoppingCartService.listShoppingCart();
        return Result.success(shoppingCartList);
    }

    /**
     * Clear the shopping cart
     * @return
     */
    @DeleteMapping("/clean")
    @ApiOperation("Clear the shopping cart")
    public Result clear() {
        shoppingCartService.clearShoppingCart();
        return Result.success();
    }

    /**
     * decrease the quantity of the dish in the shopping cart
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/sub")
    @ApiOperation("Decrease the quantity of the dish in the shopping cart")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("Decrease the quantity of the dish in the shopping cart: {}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
