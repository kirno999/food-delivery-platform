package com.edu.sdu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Integer orderId; // 归属订单
    
    @Column(nullable = false)
    private Integer dishId; // 对应菜品
    
    @Column(nullable = false, length = 100)
    private String dishName; // 下单时菜品名称（冗余）
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dishPrice; // 下单时单价
    
    @Column(nullable = false)
    private Integer quantity; // 购买份数
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice; // 单品小计金额
    
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    
    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }
}
