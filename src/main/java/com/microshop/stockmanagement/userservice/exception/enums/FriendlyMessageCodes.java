package com.microshop.stockmanagement.userservice.exception.enums;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCES(1002),
    USER_NOT_CREATED_EXCEPTION(1500),
    USER_SUCCESFULLY_CREATED(1501),
    USER_NOT_FOUND_EXCEPTION(1502),
    USER_SUCCESFULLY_UPDATED(1503),
    USER_SUCCESFULLY_DELETED(1504),
    USER_ALREADY_DELETED(1505);
    private final int value;

    FriendlyMessageCodes(int values){
        this.value=values;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
