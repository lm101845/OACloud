package org.xdq.demo.service.security;

import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dto.RoleDto;
import org.xdq.demo.dto.RoleQueryDto;
import org.xdq.demo.model.Role;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

public interface RoleService {
    PageVo<Role> getRolePage(RoleQueryDto roleQueryDto);

    void addRole(RoleDto roleDto);

    void updateRole(RoleDto roleDto);

    void removeRole(Integer... ids);


    List<MenuVo> getFunList();

    List<Integer> getRoleFunIdList(Integer roleId);

    void updateRoleFun(Map<String, Object> map);
}
