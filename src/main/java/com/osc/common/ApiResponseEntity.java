package com.osc.common;

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
     * 에러 메시지 내용
     */
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ApiResponseEntity(String status, Object response, String errorMessage) {
        this.status = status;
        this.response = response;
        this.errorMessage = errorMessage;
    }

}
