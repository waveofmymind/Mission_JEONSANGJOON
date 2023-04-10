package com.ll.gramgram.base.error;

import com.ll.gramgram.base.error.exception.UnAuthorizedException;
import com.ll.gramgram.base.rq.Rq;
import com.ll.gramgram.base.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Rq rq;

    @ExceptionHandler(UnAuthorizedException.class)
    public String unAuthorziedException(final UnAuthorizedException e) {
        return rq.historyBack(RsData.failOf(e.getErrorCode()));
    }
}
