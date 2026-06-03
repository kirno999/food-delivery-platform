package com.edu.sdu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 配送记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_record")
public class DeliveryRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Integer orderId; // 关联订单
    
    @Column(nullable = false)
    private Integer riderId; // 配送骑手
    
    @Column
    private LocalDateTime pickupTime; // 骑手到店取餐时间
    
    @Column
    private LocalDateTime deliveryTime; // 送达用户时间
    
    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal distance; // 配送里程（KM）
    
    @Column
    private Integer status; // 配送状态
    
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    
    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        if (this.distance == null) {
            this.distance = BigDecimal.ZERO;
        }
    }
}
