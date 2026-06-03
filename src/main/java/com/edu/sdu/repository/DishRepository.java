package com.edu.sdu.repository;

import com.edu.sdu.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜品仓库接口
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    /**
     * 根据商家ID查询菜品
     */
    List<Dish> findByRestaurantId(Integer restaurantId);
    
    /**
     * 根据分类ID查询菜品
     */
    List<Dish> findByCategoryId(Integer categoryId);
    
    /**
     * 根据商家ID和分类ID查询菜品
     */
    List<Dish> findByRestaurantIdAndCategoryId(Integer restaurantId, Integer categoryId);
}
