package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * Add N to the dish_flavor
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);
}
