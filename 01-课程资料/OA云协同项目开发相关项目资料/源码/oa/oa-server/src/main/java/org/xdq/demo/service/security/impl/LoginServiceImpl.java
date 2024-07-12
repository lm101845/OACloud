package org.xdq.demo.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.common.ex.BusinessException;
import org.xdq.demo.dao.LoginDao;
import org.xdq.demo.service.security.LoginService;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;

    @Override
    public CurrentUser checkLogin(Map<String, String> loginDto) {
        Map<String,String> user = loginDao.findUserByUserIdAndUserPwd(loginDto);
        if(user == null){
            throw new BusinessException("账号或密码错误！");
        }
        CurrentUser currentUser = new CurrentUser(user.get("u_id"),user.get("u_name"));
        return currentUser;
    }
}
