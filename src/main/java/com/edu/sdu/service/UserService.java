package com.edu.sdu.service;

import com.edu.sdu.entity.User;
import com.edu.sdu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户业务服务类
 */
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * 用户登录
     */
    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }
    
    /**
     * 用户注册
     */
    public User register(String username, String password, Integer userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserType(userType);
        return userRepository.save(user);
    }
    
    /**
     * 获取用户信息
     */
    public User getUserInfo(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
    
    /**
     * 更新用户信息
     */
    public User updateUserInfo(User user) {
        return userRepository.save(user);
    }
    
    /**
     * 根据用户类型查询用户
     */
    public List<User> getUsersByType(Integer userType) {
        return userRepository.findByUserType(userType);
    }
}
