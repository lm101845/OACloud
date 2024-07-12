package org.xdq.demo.service.security;

import org.xdq.demo.common.currentuser.CurrentUser;

import java.util.Map;

public interface LoginService {
    CurrentUser checkLogin(Map<String, String> loginDto);

}
