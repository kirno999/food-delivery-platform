package com.edu.sdu.repository;

import com.edu.sdu.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 商家仓库接口
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    /**
     * 根据用户ID查询商家
     */
    Optional<Restaurant> findByUserId(Integer userId);
    
    /**
     * 根据营业状态查询商家
     */
    List<Restaurant> findByStatus(Integer status);
}
