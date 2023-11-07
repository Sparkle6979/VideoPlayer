package org.zjudevelop.playerbackbend.pojo.exception;

import org.zjudevelop.playerbackbend.pojo.MessageConstant;

public class AccountNotFoundException extends BaseException{
    public AccountNotFoundException() {
        super(MessageConstant.ACCOUNT_NOT_FOUND);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
