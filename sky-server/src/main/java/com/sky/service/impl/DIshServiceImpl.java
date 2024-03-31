package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DIshServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * Save the dish with flavor
     * @param dishDTO
     */
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO, dish);

        // add one to the dish
        dishMapper.insert(dish);

        // Get the dish id
        Long DishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(flavor -> {
                flavor.setDishId(DishId);
            });
            // add N to the dish_flavor
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * Page query
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * Delete dishes
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        // if the dish is in the selling status, it cannot be deleted
        for (Long id : ids) {
            Dish dish = dishMapper.getbyId(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // if the dish is related to the meal set, it cannot be deleted
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }


        // delete the dish
        // delete the dish_flavor
        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }

    }

    /**
     * Get the dish by id
     * @param id
     * @return
     */
    public DishVO getByIdWithFlavor(Long id) {
        // get the dish data
        Dish dish = dishMapper.getbyId(id);
        // get the dish_flavor data
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        // encapsulate the data into DishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * Update the dish with flavor
     * @param dishDTO
     */
    public void updateWithFlavor(DishDTO dishDTO) {
        // update the dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        // delete the dish_flavor
        dishFlavorMapper.deleteByDishId(dish.getId());

        // add the dish_flavor
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * Start or stop the dish
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }
}
