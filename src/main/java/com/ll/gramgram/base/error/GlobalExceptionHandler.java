package com.ll.gramgram.base.error;

import com.ll.gramgram.base.error.exception.BusinessException;
import com.ll.gramgram.base.error.exception.DuplicateUserException;
import com.ll.gramgram.base.error.exception.ExceedLikeablePersonLimitException;
import com.ll.gramgram.base.error.exception.UnAuthorizedException;
import com.ll.gramgram.base.rq.Rq;
import com.ll.gramgram.base.rsData.RsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final Rq rq;

    @ExceptionHandler(BusinessException.class)
    public String businessException(final BusinessException e) {
        return rq.historyBack(RsData.failOf(e.getErrorCode()));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public String unAuthorziedException(final UnAuthorizedException e) {
        return rq.historyBack(RsData.failOf(e.getErrorCode()));
    }

    @ExceptionHandler(DuplicateUserException.class)
    public String duplicateUserException(final DuplicateUserException e) {
        log.warn(e.getErrorCode().getMessage());
        return rq.historyBack(RsData.failOf(e.getErrorCode()));
    }

    @ExceptionHandler(ExceedLikeablePersonLimitException.class)
    public String exceedLikeablePersonLimitException(final ExceedLikeablePersonLimitException e) {
        log.warn(e.getErrorCode().getMessage());
        return rq.historyBack(RsData.failOf(e.getErrorCode()));
    }

}
