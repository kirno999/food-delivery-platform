package com.edu.sdu.service;

import com.edu.sdu.entity.Restaurant;
import com.edu.sdu.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家业务服务类
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    
    /**
     * 获取所有商家
     */
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    
    /**
     * 获取营业中的商家
     */
    public List<Restaurant> getOperatingRestaurants() {
        return restaurantRepository.findByStatus(1);
    }
    
    /**
     * 获取商家详情
     */
    public Restaurant getRestaurantInfo(Integer id) {
        return restaurantRepository.findById(id).orElse(null);
    }
    
    /**
     * 根据用户ID获取商家信息
     */
    public Restaurant getRestaurantByUserId(Integer userId) {
        return restaurantRepository.findByUserId(userId).orElse(null);
    }
    
    /**
     * 新增商家
     */
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
    
    /**
     * 更新商家信息
     */
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
    
    /**
     * 删除商家
     */
    public void deleteRestaurant(Integer id) {
        restaurantRepository.deleteById(id);
    }
}
