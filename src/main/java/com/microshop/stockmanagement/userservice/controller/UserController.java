package com.microshop.stockmanagement.userservice.controller;


import com.microshop.stockmanagement.userservice.entity.User;
import com.microshop.stockmanagement.userservice.enums.Language;
import com.microshop.stockmanagement.userservice.exception.enums.FriendlyMessageCodes;
import com.microshop.stockmanagement.userservice.exception.utils.FriendlyMessageUtils;
import com.microshop.stockmanagement.userservice.request.UserCreateRequest;
import com.microshop.stockmanagement.userservice.request.UserUpdateRequest;
import com.microshop.stockmanagement.userservice.response.FriendlyMessage;
import com.microshop.stockmanagement.userservice.response.InternalApiResponse;
import com.microshop.stockmanagement.userservice.response.UserResponse;
import com.microshop.stockmanagement.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<UserResponse> createUser(@PathVariable("language") Language language, @RequestBody UserCreateRequest userCreateRequest){

        log.debug("[{}][createUser] -> request: {}", this.getClass().getSimpleName(), userCreateRequest);
        User user = iUserService.createUser(language, userCreateRequest);
        UserResponse userResponse = convertUserResponse(user);
        log.debug("[{}][createUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCES))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.USER_SUCCESFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(userResponse)
                .build();
    }

    @Operation(description = "This endpoint get all users.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{userId}")
    public InternalApiResponse<UserResponse> getUser(@PathVariable("language") Language language,@PathVariable("userId") Long userId){

        log.debug("[{}][getUser] -> request: {}", this.getClass().getSimpleName(), userId);
        User user = iUserService.getUser(language,userId);
        UserResponse userResponse = convertUserResponse(user);
        log.debug("[{}][getUser] -> response: {}",this.getClass().getSimpleName(),userResponse);

        return InternalApiResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{userId}")
    public InternalApiResponse<UserResponse> updateUser(@PathVariable("language") Language language, @PathVariable("userId") Long userId, @RequestBody UserUpdateRequest userUpdateRequest)
    {
        log.debug("[{}][updateUser] -> request: {}", this.getClass().getSimpleName(), userId, userUpdateRequest);
        User user = iUserService.updateUser(language, userId, userUpdateRequest);

        UserResponse userResponse = convertUserResponse(user); //todo burada tekrar bir setleme işlemi yapılabilir.

        log.debug("[{}][updateUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCES))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_SUCCESFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value= "/{language}/users")
    public InternalApiResponse<List<UserResponse>> getAllUsers(@PathVariable("language") Language language){
        log.debug("[{}][getAllUsers] -> request: {}", this.getClass().getSimpleName());

        List<User> userList = iUserService.getUsers(language);
        List<UserResponse> userResponses = convertUserResponseList(userList);

        log.debug("[{}][getAllUsers] -> response: {}", this.getClass().getSimpleName(), userResponses);
        return InternalApiResponse.<List<UserResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponses)
                .build();

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delte/{userId}")
    public InternalApiResponse<UserResponse> deleteUser(@PathVariable("language") Language language, @PathVariable("userId") Long userId){

        log.debug("[{}][deleteUser] -> request: {}", this.getClass().getSimpleName(), userId);

        User user = iUserService.deleteUser(language,userId);
        UserResponse userResponse = convertUserResponse(user);

        log.debug("[{}][deleteUser] -> response: {}", this.getClass().getSimpleName(), userResponse );

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCES))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.USER_SUCCESFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();


    }

    private List<UserResponse> convertUserResponseList(List<User> userList) {
        return userList.stream()
                .map(arg -> UserResponse.builder()
                        .userId(arg.getUserId())
                        .userName(arg.getUserName())
                        .userSurname(arg.getUserSurname())
                        .userEmail(arg.getUserEmail())
                        .userPhoneNumber(arg.getUserPhoneNumber())
                        .userUpdatedDate(arg.getUserUpdatedDate().getTime())
                        .userCreatedDate(arg.getUserCreatedDate().getTime())
                        .build())
                .collect(Collectors.toList());
    }


    //Setleme işlemini birden fazla kez yapacağımız için setleme işlemlerinin gerçekleştiği bir methoda dönüştürüyoruz.
    private UserResponse convertUserResponse(User user) {
        return  UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userSurname(user.getUserSurname())
                .userAddress(user.getUserAddress())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userPhoneNumber(user.getUserPhoneNumber())
                .userCreatedDate(user.getUserCreatedDate().getTime())
                .userUpdatedDate(user.getUserUpdatedDate().getTime())
                .build();
    }
}
