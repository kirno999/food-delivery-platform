package com.edu.sdu.service;

import com.edu.sdu.entity.Order;
import com.edu.sdu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 订单业务服务类
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    
    /**
     * 创建订单
     */
    public Order createOrder(Order order) {
        // 生成订单号
        order.setOrderNo(generateOrderNo());
        return orderRepository.save(order);
    }
    
    /**
     * 获取订单详情
     */
    public Order getOrderInfo(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
    
    /**
     * 根据订单号查询
     */
    public Order getOrderByNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo).orElse(null);
    }
    
    /**
     * 获取用户订单列表
     */
    public List<Order> getUserOrders(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
    
    /**
     * 获取商家订单列表
     */
    public List<Order> getRestaurantOrders(Integer restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }
    
    /**
     * 获取骑手订单列表
     */
    public List<Order> getRiderOrders(Integer riderId) {
        return orderRepository.findByRiderId(riderId);
    }
    
    /**
     * 更新订单状态
     */
    public Order updateOrderStatus(Integer orderId, Integer status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            // 根据状态更新相应时间
            switch (status) {
                case 1: // 待接单
                    break;
                case 2: // 待取餐
                    order.setAcceptTime(LocalDateTime.now());
                    break;
                case 3: // 配送中
                    order.setPickupTime(LocalDateTime.now());
                    break;
                case 4: // 已完成
                    order.setDeliveryTime(LocalDateTime.now());
                    order.setFinishTime(LocalDateTime.now());
                    break;
                case 5: // 已取消
                    order.setCancelTime(LocalDateTime.now());
                    break;
            }
            return orderRepository.save(order);
        }
        return null;
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
