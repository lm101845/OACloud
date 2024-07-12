package org.xdq.demo.dao;

import org.apache.ibatis.annotations.*;
import org.xdq.demo.dto.RoleDto;
import org.xdq.demo.dto.RoleQueryDto;
import org.xdq.demo.model.Role;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleDao {

    List<Role> findRoleList(RoleQueryDto roleQueryDto);

    @Select("select ifnull(max(ro_id),0) from t_role")
    int findMaxId();

    @Insert("insert into t_role(ro_id,ro_name) values(#{ro_id},#{ro_name})")
    void insertRole(RoleDto roleDto);

    @Update("update t_role set ro_name = #{ro_name} where ro_id = #{ro_id}")
    void updateRole(RoleDto roleDto);

    void deleteRole(@Param("ids") Integer[] ids);


    List<MenuVo> findAllFunList();

    @Select("select f_id from t_rf where  ro_id = #{roleId}")
    List<Integer> findRoleFunIdList(Integer roleId);

    @Delete("delete from t_rf where ro_id = #{roleId}")
    void deleteRoleFun(Map<String, Object> map);

    void insertRoleFun(Map<String, Object> map);

    boolean findExistsUser(@Param("roleIds") Integer[] ids);

    boolean findExistsFun(@Param("roleIds") Integer[] ids);
}
