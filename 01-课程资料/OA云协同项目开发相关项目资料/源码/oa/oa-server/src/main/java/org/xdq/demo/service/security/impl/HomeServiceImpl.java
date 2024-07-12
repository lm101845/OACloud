package org.xdq.demo.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.common.ex.BusinessException;
import org.xdq.demo.dao.HomeDao;
import org.xdq.demo.service.security.HomeService;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeDao homeDao;
    @Override
    public List<MenuVo> getMenuList(CurrentUser currentUser) {
        return homeDao.findMenuList(currentUser);
    }

    @Override
    public void updatePassword(Map<String, String> pwdDto) {
        String factOldPwd = homeDao.findFactOldPwd(pwdDto);
        if(!factOldPwd.equals(pwdDto.get("old_pwd"))){
            throw new BusinessException("原密码错误！");
        }
        homeDao.updatePassword(pwdDto);
    }
}
