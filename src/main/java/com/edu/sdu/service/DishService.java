package com.edu.sdu.service;

import com.edu.sdu.entity.Dish;
import com.edu.sdu.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜品业务服务类
 */
@Service
@RequiredArgsConstructor
public class DishService {
    
    private final DishRepository dishRepository;
    
    /**
     * 获取菜品列表
     */
    public List<Dish> getDishList(Integer restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId);
    }
    
    /**
     * 获取分类菜品
     */
    public List<Dish> getDishByCategory(Integer restaurantId, Integer categoryId) {
        return dishRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId);
    }
    
    /**
     * 获取菜品详情
     */
    public Dish getDishInfo(Integer id) {
        return dishRepository.findById(id).orElse(null);
    }
    
    /**
     * 新增菜品
     */
    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }
    
    /**
     * 更新菜品
     */
    public Dish updateDish(Dish dish) {
        return dishRepository.save(dish);
    }
    
    /**
     * 删除菜品
     */
    public void deleteDish(Integer id) {
        dishRepository.deleteById(id);
    }
}
