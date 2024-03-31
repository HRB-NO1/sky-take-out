package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("AdminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "ShopController")
@Slf4j
public class ShopController {

    public static final String SHOP_STATUS = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("Set the shop status")
    public Result setShopStatus(@PathVariable Integer status) {
        log.info("Set the shop status: {}", status == 1 ? "open" : "close");
        redisTemplate.opsForValue().set(SHOP_STATUS, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("Get the shop status")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("Get the shop status: {}", status == 1 ? "open" : "close");
        return Result.success(status);
    }

}
