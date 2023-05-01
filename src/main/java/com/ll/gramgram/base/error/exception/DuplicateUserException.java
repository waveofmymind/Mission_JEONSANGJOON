package com.ll.gramgram.base.error.exception;

import com.ll.gramgram.base.error.ErrorCode;

public class DuplicateUserException extends BusinessException {

    public DuplicateUserException(ErrorCode errorCode) {
        super(errorCode,errorCode.getMessage());
    }

}
