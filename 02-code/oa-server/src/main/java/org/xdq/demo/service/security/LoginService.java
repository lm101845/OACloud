package org.xdq.demo.service.security;

import org.xdq.demo.common.currentuser.CurrentUser;

import java.util.Map;

/**
 * @Author liming
 * @Date 2024/7/15 12:20
 * @Description
 **/
public interface LoginService {
    CurrentUser checkLogin(Map<String, String> loginDto);

}
