package com.amore.project.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseEntity {

    /**
     * 처리 상태
     */
    private String status;

    /**
     * 응답 내용
     */
    private Object response;

    /**
     * 에러 메시지 코드
     */
    private String errorCode;

    /**
     * 에러 메시지 내용
     */
    private String errorMessage;

}
