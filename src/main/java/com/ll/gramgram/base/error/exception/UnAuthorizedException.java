package com.ll.gramgram.base.error.exception;


import com.ll.gramgram.base.error.ErrorCode;

public class UnAuthorizedException extends BusinessException {
    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnAuthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
