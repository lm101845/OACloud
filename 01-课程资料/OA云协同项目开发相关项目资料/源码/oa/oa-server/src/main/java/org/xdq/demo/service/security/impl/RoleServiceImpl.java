package org.xdq.demo.service.security.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xdq.demo.common.InfoStatusEnum;
import org.xdq.demo.common.ex.BusinessException;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dao.RoleDao;
import org.xdq.demo.dto.RoleDto;
import org.xdq.demo.dto.RoleQueryDto;
import org.xdq.demo.model.Role;
import org.xdq.demo.service.security.RoleService;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Override
    public PageVo<Role> getRolePage(RoleQueryDto roleQueryDto) {



        return PageVo.getPageVo(roleQueryDto,()-> roleDao.findRoleList(roleQueryDto));
    }


    private int id;//最大编号

    @PostConstruct //（初始化方法）表示该方法在创建对象后执行，而仅执行一次
    public void init(){
        id = roleDao.findMaxId();
    }

    private synchronized int newId(){
        return ++id;
    }

    @Override
    public void addRole(RoleDto roleDto) {
        roleDto.setRo_id(newId());
        roleDao.insertRole(roleDto);
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        roleDao.updateRole(roleDto);
    }

    @Override
    public void removeRole(Integer... ids) {
        if(ids == null || ids.length == 0){
            throw new BusinessException("请选择至少一条数据！");
        }

        if(roleDao.findExistsUser(ids)){
            throw new BusinessException("将要删除的角色中，部分角色已被用户使用，不允许删除！");
        }

        if(roleDao.findExistsFun(ids)){
            throw new BusinessException("将要删除的角色中，部分角色已有权限，不允许删除！");
        }

        roleDao.deleteRole(ids);
    }

    @Override
    public List<MenuVo> getFunList() {
        return roleDao.findAllFunList();
    }

    @Override
    public List<Integer> getRoleFunIdList(Integer roleId) {
        return roleDao.findRoleFunIdList(roleId);
    }

    @Override
    public void updateRoleFun(Map<String, Object> map) {
        //先删除指定角色下的权限
        roleDao.deleteRoleFun(map);
        //再将提交的权限新增
        roleDao.insertRoleFun(map);
    }

}
