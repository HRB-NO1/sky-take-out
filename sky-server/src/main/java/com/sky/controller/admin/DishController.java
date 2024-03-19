package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Dish controller
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "Dish Controller API")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * Save the dish
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add the dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("Add the dish: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * Page query
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Page query")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("Query the dish: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
}
