package com.osc.user.vo;

public class UserVO {
    /**
     * 로그인 ID
     */
    private String loginId;

    /**
     * 사용자 이름
     */
    private String userName;

    /**
     * 사용자 주소
     */
    private String userAddress;

    /**
     * 사용자 전화번호
     */
    private String userPhone;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
