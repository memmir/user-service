package com.microshop.stockmanagement.userservice.service;

import com.microshop.stockmanagement.userservice.entity.User;
import com.microshop.stockmanagement.userservice.enums.Language;
import com.microshop.stockmanagement.userservice.request.UserCreateRequest;
import com.microshop.stockmanagement.userservice.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {

    User createUser(Language language, UserCreateRequest userCreateRequest);

    User getUser(Language language, Long userId);

    List<User> getUsers(Language language);

    User updateUser(Language language, Long userId, UserUpdateRequest userUpdateRequest);

    User deleteUser(Language language, Long userId);
}
