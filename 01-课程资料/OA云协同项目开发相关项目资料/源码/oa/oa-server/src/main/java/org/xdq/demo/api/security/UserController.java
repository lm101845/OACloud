package org.xdq.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.common.vo.R;
import org.xdq.demo.dto.UserDto;
import org.xdq.demo.dto.UserQueryDto;
import org.xdq.demo.model.Role;
import org.xdq.demo.model.User;
import org.xdq.demo.service.security.UserService;


import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/security/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public R<PageVo<User>> userList(UserQueryDto userQueryDto){
        PageVo<User> page = userService.getUserPage(userQueryDto);
        return R.OK(page);
    }

    @PostMapping("")
    public R<?> execAdd(@RequestBody UserDto userDto){
        userService.addUser(userDto);
        return R.OK();
    }
    @PutMapping("")
    public R<?> execUpd(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return R.OK();
    }

    @DeleteMapping("/{id}")
    public R<?> execDel(@PathVariable String id){
        userService.removeUser(id);
        return R.OK();
    }

    @DeleteMapping("")
    public R<?> execDel(@RequestBody String[] ids){
        userService.removeUser(ids);
        return R.OK();
    }

    @GetMapping("/role")
    public R<List<Role>> allRoleList(){
        List<Role> roleList = userService.getRoleList();
        return R.OK(roleList);
    }

    @GetMapping("/role/{userId}")
    public R<List<Integer>> getUserRoleIdList(@PathVariable String userId){
        List<Integer> roleIdList = userService.getUserRoleIdList(userId);
        return R.OK(roleIdList);
    }

    @PutMapping("/role")
    public R<?> execUpdateUserRoleIds(@RequestBody Map<String,Object> map){
        userService.updateUserRole(map);
        return R.OK();
    }

}
