package com.microshop.stockmanagement.userservice.service;


import com.microshop.stockmanagement.userservice.entity.User;
import com.microshop.stockmanagement.userservice.enums.Language;
import com.microshop.stockmanagement.userservice.exception.enums.FriendlyMessageCodes;
import com.microshop.stockmanagement.userservice.exception.exceptions.UserNotCreatedException;
import com.microshop.stockmanagement.userservice.repository.UserRepository;
import com.microshop.stockmanagement.userservice.request.UserCreateRequest;
import com.microshop.stockmanagement.userservice.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;


    @Override
    public User createUser(Language language, UserCreateRequest userCreateRequest) {
        log.debug("[{}][createUser] -> request: {}", this.getClass().getSimpleName(), userCreateRequest);
        try{
            User user = User.builder()
                    .userName(userCreateRequest.getUserName())
                    .userSurname(userCreateRequest.getUserSurname())
                    .userAddress(userCreateRequest.getUserAddress())
                    .userEmail(userCreateRequest.getUserEmail())
                    .userPassword(userCreateRequest.getUserPassword())
                    .userCreatedDate(new Date())
                    .userUpdatedDate(new Date())
                    .deleted(false)
                    .build();
            User userResponse = userRepository.save(user);
            log.debug("[{}][createUser] -> response: {}",this.getClass().getSimpleName(),userResponse);
            return userResponse;
        }
        catch(Exception e){
            throw new UserNotCreatedException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "product request " + userCreateRequest.toString());
        }
    }

    @Override
    public User getUser(Language language, Long userId) {
        return null;
    }

    @Override
    public List<User> getUsers(Language language) {
        return null;
    }

    @Override
    public User updateUser(Language language, Long userId, UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public User deleteUser(Language language, Long userId) {
        return null;
    }
}
