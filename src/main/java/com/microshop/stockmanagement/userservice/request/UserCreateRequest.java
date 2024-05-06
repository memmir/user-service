package com.microshop.stockmanagement.userservice.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPassword;
    private String userAddress;

}
