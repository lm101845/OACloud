package org.xdq.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @Author liming
 * @Date 2024/7/15 12:29
 * @Description
 **/
@Mapper
public interface LoginDao {

    @Select("select u_id,u_name from t_user where u_id = #{u_id} and u_pwd = #{u_pwd}")
    Map<String, String> findUserByUserIdAndUserPwd(Map<String, String> loginDto);
}
