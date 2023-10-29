package org.zjudevelop.playerbackbend.pojo.exception;

import org.apache.logging.log4j.message.Message;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;

public class PasswordErrorException extends BaseException{
    public PasswordErrorException() {
        super(MessageConstant.PASSWORD_ERROR);
    }

    public PasswordErrorException(String message) {
        super(message);
    }
}
