package com.ll.gramgram.base.error.exception;

import com.ll.gramgram.base.error.ErrorCode;

public class ExceedLikeablePersonLimitException extends BusinessException {

    public ExceedLikeablePersonLimitException(ErrorCode errorCode) {
        super(errorCode,errorCode.getMessage());
    }

}
