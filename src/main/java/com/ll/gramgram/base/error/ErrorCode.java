package com.ll.gramgram.base.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_THE_WRITER(HttpStatus.UNAUTHORIZED,"작성자가 아닙니다.");

    private final HttpStatus status;
    private final String message;

    }
