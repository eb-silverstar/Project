package com.amore.project.profile.controller;

import com.amore.project.common.ApiResponseEntity;
import com.amore.project.common.Message;
import com.amore.project.profile.service.ProfileService;
import com.amore.project.profile.vo.ProfileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponseEntity> getProfileList() {
        List<ProfileVO> profileList = profileService.getProfileList();

        ApiResponseEntity message = new ApiResponseEntity(Message.I0001, profileList, null, null);

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

}
