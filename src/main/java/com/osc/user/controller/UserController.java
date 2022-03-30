package com.osc.user.controller;

import com.osc.common.ApiResponseEntity;
import com.osc.user.service.UserService;
import com.osc.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 사용자 조회
     *
     * @param loginId
     * @return ResponseEntity<ApiResponseEntity>
     */
    @GetMapping("/{loginId}")
    public ResponseEntity<ApiResponseEntity> getUser(@PathVariable("loginId") String loginId) {
        Map<String, Object> result = userService.getUser(loginId);
        ApiResponseEntity message = new ApiResponseEntity((String) result.get("status"), result.get("response"), (String) result.get("errorMessage"));
        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * 사용자 생성
     *
     * @param userVo
     * @return ResponseEntity<ApiResponseEntity>
     */
    @PostMapping("")
    public ResponseEntity<ApiResponseEntity> createUser(@RequestBody UserVO userVo) {
        Map<String, Object> result = userService.createUser(userVo);
        ApiResponseEntity message = new ApiResponseEntity((String) result.get("status"), result.get("response"), (String) result.get("errorMessage"));
        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * 사용자 정보 수정
     *
     * @param userVo
     * @return ResponseEntity<ApiResponseEntity>
     */
    @PutMapping("")
    public ResponseEntity<ApiResponseEntity> updateUser(@RequestBody UserVO userVo) {
        Map<String, Object> result = userService.updateUser(userVo);
        ApiResponseEntity message = new ApiResponseEntity((String) result.get("status"), result.get("response"), (String) result.get("errorMessage"));
        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * 사용자 삭제
     *
     * @param loginId
     * @return ResponseEntity<ApiResponseEntity>
     */
    @DeleteMapping("/{loginId}")
    public ResponseEntity<ApiResponseEntity> deleteUser(@PathVariable("loginId") String loginId) {
        Map<String, Object> result = userService.deleteUser(loginId);
        ApiResponseEntity message = new ApiResponseEntity((String) result.get("status"), result.get("response"), (String) result.get("errorMessage"));
        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

}
