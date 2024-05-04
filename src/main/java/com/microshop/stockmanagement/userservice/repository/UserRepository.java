package com.microshop.stockmanagement.userservice.repository;

import com.microshop.stockmanagement.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUserIdAndDeletedFalse(Long UserId);

    List<User> getAllByDeletedFalse();
}
