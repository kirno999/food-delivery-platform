package com.edu.sdu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 地址实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Integer userId; // 用户ID
    
    @Column(length = 50)
    private String receiverName; // 收货人姓名
    
    @Column(length = 20)
    private String phone; // 联系电话
    
    @Column(length = 50)
    private String province; // 省份
    
    @Column(length = 50)
    private String city; // 城市
    
    @Column(length = 50)
    private String district; // 区域
    
    @Column(length = 200)
    private String detail; // 详细地址
    
    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private Integer isDefault; // 1=默认地址，0=普通地址
    
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    
    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        if (this.isDefault == null) {
            this.isDefault = 0;
        }
    }
}
