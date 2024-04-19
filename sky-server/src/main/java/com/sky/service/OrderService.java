package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

public interface OrderService {

    /**
     * User submit Order
     *
     * @param ordersSubmitDTO OrdersSubmitDTO
     * @return OrderSubmitVO
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
