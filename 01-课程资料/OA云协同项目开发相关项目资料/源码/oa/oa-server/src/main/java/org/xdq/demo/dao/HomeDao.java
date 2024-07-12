package org.xdq.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface HomeDao {
    List<MenuVo> findMenuList(CurrentUser currentUser);

    @Select("select u_pwd from t_user where u_id = #{userId}")
    String findFactOldPwd(Map<String, String> pwdDto);

    @Update("update t_user set u_pwd = #{new_pwd} where u_id = #{userId}")
    void updatePassword(Map<String, String> pwdDto);
}
