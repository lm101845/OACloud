package org.xdq.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.common.vo.R;
import org.xdq.demo.dto.RoleDto;
import org.xdq.demo.dto.RoleQueryDto;
import org.xdq.demo.model.Role;
import org.xdq.demo.service.security.RoleService;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/security/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("")
    public R<PageVo<Role>> roleList(RoleQueryDto roleQueryDto){
        PageVo<Role> page = roleService.getRolePage(roleQueryDto);
        return R.OK(page);
    }

    @PostMapping("")
    public R<?> execAdd(@RequestBody RoleDto roleDto){
        roleService.addRole(roleDto);
        return R.OK();
    }
    @PutMapping("")
    public R<?> execUpd(@RequestBody RoleDto roleDto){
        roleService.updateRole(roleDto);
        return R.OK();
    }

    @DeleteMapping("/{id}")
    public R<?> execDel(@PathVariable Integer id){
        roleService.removeRole(id);
        return R.OK();
    }

    @DeleteMapping("")
    public R<?> execDel(@RequestBody Integer[] ids){
        roleService.removeRole(ids);
        return R.OK();
    }

    @GetMapping("/fun")
    public R<List<MenuVo>> allFunList(){
        List<MenuVo> funList = roleService.getFunList();
        return R.OK(funList);
    }

    @GetMapping("/fun/{roleId}")
    public R<List<Integer>> getRoleFunIdList(@PathVariable Integer roleId){
        List<Integer> funIdList = roleService.getRoleFunIdList(roleId);
        return R.OK(funIdList);
    }

    @PutMapping("/fun")
    public R<?> execUpdateRoleFunIds(@RequestBody Map<String,Object> map){
        roleService.updateRoleFun(map);
        return R.OK();
    }

}
