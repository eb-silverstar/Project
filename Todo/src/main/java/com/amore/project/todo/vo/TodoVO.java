package com.amore.project.todo.vo;

import com.amore.project.profile.vo.ProfileVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class TodoVO {

    /**
     * ID
     */
    private int id;

    /**
     * 날짜
     */
    private String date;

    /**
     * 담당자
     */
    private ProfileVO manager;

    /**
     * 상태 (진행 중, 완료, 취소, 위임)
     */
    private String status;

    /**
     * 양수인
     */
    private ProfileVO assignee;

    /**
     * 양도인
     */
    private ProfileVO assigner;

    /**
     * 원 Todo ID
     */
    private int originTodoId;

    /**
     * 업무 제목
     */
    private String task;

    /**
     * 업무 설명
     */
    private String description;

    /**
     * 중요도 등급 (S > A > B > C > D)
     */
    private String rank;

    /**
     * 둥일 중요도 등급 내 순서
     */
    private int order;

}
