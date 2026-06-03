package com.edu.sdu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商家（餐厅）实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Integer userId; // 绑定的商家账号ID
    
    @Column(nullable = false, length = 100)
    private String name; // 店铺名称
    
    @Column(length = 200)
    private String logo; // 店铺头像图片路径
    
    @Column(length = 20)
    private String phone; // 商家联系电话
    
    @Column(length = 200)
    private String address; // 店铺实体地址
    
    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private Integer status; // 0待审核 1营业中 2已关停
    
    @Column(length = 100)
    private String businessHours; // 营业时间
    
    @Column(precision = 2, scale = 1, columnDefinition = "DECIMAL(2,1) DEFAULT 5.0")
    private BigDecimal avgRating; // 店铺平均分
    
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        if (this.avgRating == null) {
            this.avgRating = new BigDecimal("5.0");
        }
        if (this.status == null) {
            this.status = 0;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
