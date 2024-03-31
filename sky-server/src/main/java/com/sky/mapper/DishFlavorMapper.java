package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * Add N to the dish_flavor
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * Delete the dish_flavor by dish id
     * @param dishId
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * Get the dish_flavor by dish id
     * @param DishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{DishId}")
    List<DishFlavor> getByDishId(Long DishId);
}
