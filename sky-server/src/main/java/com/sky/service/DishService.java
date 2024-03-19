package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

public interface DishService {

    /**
     * Save the dish with flavor
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * Page query
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
