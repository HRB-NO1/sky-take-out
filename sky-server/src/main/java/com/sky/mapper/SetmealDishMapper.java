package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * Get the setmeal Ids by dish Ids
     * @param dishIDs
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIDs);
}
