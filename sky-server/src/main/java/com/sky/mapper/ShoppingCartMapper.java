package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * dynamic query
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * update the quantity of the dish in the shopping cart
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * insert the shopping cart
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart(user_id, dish_id, setmeal_id, name, image, amount, number, create_time, dish_flavor) " +
            "values(#{userId}, #{dishId}, #{setmealId}, #{name}, #{image}, #{amount}, #{number}, #{createTime}, #{dishFlavor})")
    void insert(ShoppingCart shoppingCart);

    /**
     * delete the shopping cart
     * @param shoppingCart
     */
    void delete(ShoppingCart shoppingCart);

    /**
     * batch insert shopping cart
     * @param currentId
     */
    @Delete("delete from shopping_cart where user_id = #{currentId}")
    void deleteByUserId(Long currentId);
}
