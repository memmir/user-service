package com.microshop.stockmanagement.userservice.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private long userId;
    private String userName;
    private String userSurname;
    private String userEmail;
    //private String userPassord; todo password response olarak dönülmeli mi?
    private Long userCreatedDate;
    private Long userUpdatedDate;
}
