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

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<UserResponse> createUser(@PathVariable("language") Language language, @RequestBody UserCreateRequest userCreateRequest){

        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), userCreateRequest);
        User user = iUserService.createUser(language, userCreateRequest);
        UserResponse userResponse = convertUserResponse(user);
        log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), userResponse);

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







    //Setleme işlemini birden fazla kez yapacağımız için setleme işlemlerinin gerçekleştiği bir methoda dönüştürüyoruz.
    private UserResponse convertUserResponse(User user) {
        return  UserResponse.builder()
                .userName(user.getUserName())
                .userSurname(user.getUserSurname())
                .userAddress(user.getUserAddress())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userCreatedDate(user.getUserCreatedDate().getTime())
                .userUpdatedDate(user.getUserUpdatedDate().getTime())
                .build();
    }
}
