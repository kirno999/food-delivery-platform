package com.edu.sdu.repository;

import com.edu.sdu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户仓库接口
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 根据用户名查询用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据用户类型查询用户
     */
    java.util.List<User> findByUserType(Integer userType);
}
