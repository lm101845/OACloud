package org.xdq.demo.service.security;

import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dto.UserDto;
import org.xdq.demo.dto.UserQueryDto;
import org.xdq.demo.model.Role;
import org.xdq.demo.model.User;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

public interface UserService {
    PageVo<User> getUserPage(UserQueryDto userQueryDto);

    void addUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void removeUser(String... ids);


    List<Role> getRoleList();

    List<Integer> getUserRoleIdList(String userId);

    void updateUserRole(Map<String, Object> map);
}
