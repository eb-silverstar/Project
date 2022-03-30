package com.osc.user.mapper;

import com.osc.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public UserVO getUser(String loginId);
    public void insertUser(UserVO userVo);
    public void updateUser(UserVO userVo);
    public void deleteUser(String loginId);

}
