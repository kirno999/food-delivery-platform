package com.edu.sdu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 购物车实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Integer userId; // 所属用户
    
    @Column(nullable = false)
    private Integer dishId; // 选购菜品
    
    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer quantity; // 选购份数
    
    @Column(name = "add_time")
    private LocalDateTime addTime; // 加入购物车时间
    
    @PrePersist
    public void prePersist() {
        this.addTime = LocalDateTime.now();
        if (this.quantity == null) {
            this.quantity = 1;
        }
    }
}
