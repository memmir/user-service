package com.microshop.stockmanagement.userservice.request;

import lombok.Data;

@Data
public class UserUpdateRequest {


    private String userEmail;
    private String userPassword;
    private String userAddress;
    private String userPhoneNumber;
}
