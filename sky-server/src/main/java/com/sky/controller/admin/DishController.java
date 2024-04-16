package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @Autowired
    private RedisTemplate redisTemplate;

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

        // clear the redis cache
        String key = "dish_" + dishDTO.getCategoryId();
        clearRedisCache(key);

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

    /**
     * Delete dishes
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete the dish")
    public Result deleteBatch(@RequestParam List<Long> ids) {
        log.info("Delete the dish: {}", ids);
        dishService.deleteBatch(ids);

        // clear all the redis cache
        clearRedisCache("dish_*");


        return Result.success();
    }

    /**
     * Query the dish by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Query the dish by id")
    public Result<DishVO> getbyId(@PathVariable Long id) {
        log.info("Query the dish by id: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * Update the dish
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update the dish")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("Update the dish: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        // clear all the redis cache
        clearRedisCache("dish_*");


        return Result.success();
    }

    /**
     * Start or stop the dish
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop the dish")
    public Result startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("Start or stop the dish: {}", id);
        dishService.startOrStop(status, id);

        // clear all the redis cache
        clearRedisCache("dish_*");

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * Clear the redis cache
     * @param pattern
     */
    private void clearRedisCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
