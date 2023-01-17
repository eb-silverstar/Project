package com.amore.project.profile.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileVO {

    /**
     * 사용자 ID
     */
    private int userId;

    /**
     * 사용자 이름
     */
    private String userName;

    public ProfileVO(int userId) {
        this.userId = userId;

        switch (userId) {
            case 1:
                this.userName = "유재석";
                break;
            case 2:
                this.userName = "김종국";
                break;
            case 3:
                this.userName = "전소민";
                break;
            case 4:
                this.userName = "송지효";
                break;
            case 5:
                this.userName = "하하";
                break;
            default:
                this.userName = "이름없음";
                break;
        }
    }

}
