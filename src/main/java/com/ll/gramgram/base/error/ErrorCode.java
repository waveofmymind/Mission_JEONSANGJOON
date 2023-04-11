package com.ll.gramgram.base.error;

import com.ll.gramgram.base.rsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SELF_LIKE_NOT_ALLOWED("F-1", "본인을 호감상대로 등록할 수 없습니다."),
    NOT_CONNECT("F-2","먼저 본인의 인스타그램 아이디를 입력해야 합니다."),
    NOT_THE_WRITER("F-3","작성자가 아닙니다."),
    DUPLICATE_USER_LIKE("F-4", "이미 호감표시를 한 상대입니다."),
    MAX_LIKEABLE_PERSON("F-5","호감 표시는 최대 10명까지만 할 수 있습니다." );

    private final String resultCode;
    private final String message;

    }
