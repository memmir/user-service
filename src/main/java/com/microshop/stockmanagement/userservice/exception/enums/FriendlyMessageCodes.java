package com.microshop.stockmanagement.userservice.exception.enums;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCES(1002),
    PRODUCT_NOT_CREATED_EXCEPTION(1500),
    PRODUCT_SUCCESFULLY_CREATED(1501),
    PRODUCT_NOT_FOUND_EXCEPTION(1502),
    PRODUCT_SUCCESFULLY_UPDATED(1503),
    PRODUCT_SUCCESFULLY_DELETED(1504),
    PRODUCT_ALREADY_DELETED(1505);
    private final int value;

    FriendlyMessageCodes(int values){
        this.value=values;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
