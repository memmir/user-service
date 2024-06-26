package com.microshop.stockmanagement.userservice.service;


import com.microshop.stockmanagement.userservice.entity.User;
import com.microshop.stockmanagement.userservice.enums.Language;
import com.microshop.stockmanagement.userservice.exception.enums.FriendlyMessageCodes;
import com.microshop.stockmanagement.userservice.exception.exceptions.UserAlreadyDeletedException;
import com.microshop.stockmanagement.userservice.exception.exceptions.UserNotCreatedException;
import com.microshop.stockmanagement.userservice.exception.exceptions.UserNotFoundException;
import com.microshop.stockmanagement.userservice.repository.UserRepository;
import com.microshop.stockmanagement.userservice.request.UserCreateRequest;
import com.microshop.stockmanagement.userservice.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                    .userPhoneNumber(userCreateRequest.getUserPhoneNumber())
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
            throw new UserNotCreatedException(language, FriendlyMessageCodes.USER_NOT_CREATED_EXCEPTION, "user request " + userCreateRequest.toString());
        }
    }

    @Override
    public User getUser(Language language, Long userId) {
        log.debug("[{}][getUser] -> request: {}", this.getClass().getSimpleName(), userId);
        User user = userRepository.getByUserIdAndDeletedFalse(userId);
        if(Objects.isNull(user)){
            throw new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "User not found for user id: "+ userId);
        }
        log.debug("[{}][getUser] -> response: {}",this.getClass().getSimpleName(),userId);

        return user;
    }

    @Override
    public List<User> getUsers(Language language) {
        log.debug("[{}][getUsers] -> request: {}",this.getClass().getSimpleName());

        List<User> userList = userRepository.getAllByDeletedFalse();

        if(userList.isEmpty()){
            throw new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "Users not found");
        }
        log.debug("[{}][getUsers] -> response: {}",this.getClass().getSimpleName(), userList);
        return userList;
    }

    @Override
    public User updateUser(Language language, Long userId, UserUpdateRequest userUpdateRequest) {
        log.debug("[{}][updateUser] -> request: {}", this.getClass().getSimpleName(), userId, userUpdateRequest);

       User user = getUser(language,userId);

       user.setUserAddress(userUpdateRequest.getUserAddress());
       user.setUserEmail(userUpdateRequest.getUserEmail());
       user.setUserPassword(userUpdateRequest.getUserPassword());
       user.setUserPhoneNumber(user.getUserPhoneNumber());
       user.setUserUpdatedDate(new Date());

       User userResponse = userRepository.save(user);

       log.debug("[{}][updateUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

       return user;

    }

    @Override
    public User deleteUser(Language language, Long userId) {
        log.debug("[{}][deleteUser] -> request: {}", this.getClass().getSimpleName(), userId);

        User user;

        try {
            user = getUser(language, userId);
            user.setDeleted(true);
            user.setUserUpdatedDate(new Date());
            User userResponse = userRepository.save(user);

            log.debug("[{}][deleteUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

            return userResponse;

        }catch (UserNotFoundException userNotFoundException){
            throw new UserAlreadyDeletedException(language, FriendlyMessageCodes.USER_ALREADY_DELETED,"User already deleted user id: " + userId);
        }
    }
}
