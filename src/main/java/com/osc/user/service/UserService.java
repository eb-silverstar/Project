package com.osc.user.service;

import com.osc.user.mapper.UserMapper;
import com.osc.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 사용자 조회
     *
     * @param loginId
     * @return result
     */
    public Map<String, Object> getUser(String loginId) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "fail");

        try{
            UserVO userVo = userMapper.getUser(loginId);

            result.put("status", "success");
            if(userVo.getLoginId() != null) {
                result.put("response", userVo);
            } else {
                result.put("response", "존재하지 않는 사용자입니다.");
            }

        } catch (Exception e) {
            result.put("errorMessage", e.getMessage());
        }

        return result;
    }

    /**
     * 사용자 생성
     *
     * @param userVo
     * @return result
     */
    public Map<String, Object> createUser(UserVO userVo) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "fail");

        try{
            userMapper.insertUser(userVo);

            result.put("status", "success");
            result.put("response", userVo.getLoginId() + " 사용자를 생성하였습니다.");

        } catch (Exception e) {
            result.put("errorMessage", e.getMessage());
        }

        return result;
    }

    /**
     * 사용자 정보 수정
     *
     * @param userVo
     * @return result
     */
    public Map<String, Object> updateUser(UserVO userVo) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "fail");

        try{
            userMapper.updateUser(userVo);

            result.put("status", "success");
            result.put("response", userVo.getLoginId() + " 사용자 정보를 수정하였습니다.");

        } catch (Exception e) {
            result.put("errorMessage", e.getMessage());
        }

        return result;
    }

    /**
     * 사용자 정보 삭제
     *
     * @param loginId
     * @return result
     */
    public Map<String, Object> deleteUser(String loginId) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "fail");

        try{
            userMapper.deleteUser(loginId);

            result.put("status", "success");
            result.put("response", loginId + " 사용자 정보를 삭제하였습니다.");

        } catch (Exception e) {
            result.put("errorMessage", e.getMessage());
        }

        return result;
    }

}
