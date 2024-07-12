package org.xdq.demo.common.token;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenDao {

    @Select("select u_pwd from t_user where u_id = #{userId}")
    public String findUserPassword(String userId);
}
