package org.xdq.demo.service.security;

import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<MenuVo> getMenuList(CurrentUser currentUser);

    void updatePassword(Map<String, String> pwdDto);
}
