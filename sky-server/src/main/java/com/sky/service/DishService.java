package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    /**
     * Delete dishes
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * Get the dish by id
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * Update the dish with flavor
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * Start or stop the dish
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
