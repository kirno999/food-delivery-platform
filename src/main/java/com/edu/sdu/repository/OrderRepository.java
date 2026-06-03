package com.edu.sdu.repository;

import com.edu.sdu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 订单仓库接口
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * 根据订单号查询订单
     */
    Optional<Order> findByOrderNo(String orderNo);
    
    /**
     * 根据用户ID查询订单
     */
    List<Order> findByUserId(Integer userId);
    
    /**
     * 根据商家ID查询订单
     */
    List<Order> findByRestaurantId(Integer restaurantId);
    
    /**
     * 根据骑手ID查询订单
     */
    List<Order> findByRiderId(Integer riderId);
}
