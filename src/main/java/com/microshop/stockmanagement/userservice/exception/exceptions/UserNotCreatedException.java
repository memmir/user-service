package com.microshop.stockmanagement.userservice.exception.exceptions;


import com.microshop.stockmanagement.userservice.enums.Language;
import com.microshop.stockmanagement.userservice.exception.enums.IFriendlyMessageCode;
import com.microshop.stockmanagement.userservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log satırı için ekleniyor
@Getter
public class UserNotCreatedException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;


    public UserNotCreatedException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[ProductNotCreatedException] -> message: {} developer message: {} ", FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode),message);
    }


}
