package org.xdq.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.dao.HomeDao;
import org.xdq.demo.vo.MenuVo;

import java.util.List;

@Slf4j
@SpringBootTest
public class HomeDaoTest {

    @Autowired
    private HomeDao homeDao;

    /**
     * 测试查询菜单列表的功能。
     * 该方法模拟一个管理员（"admin", "用户1"）的菜单查询，查询到的菜单将被逐级打印出来。
     *
     */
    @Test
    public void testFindMenuList(){
        // 通过homeDao查询管理员的菜单列表
        List<MenuVo> menuList = homeDao.findMenuList(new CurrentUser("admin", "用户1"));

        // 遍历菜单列表，打印主菜单的ID和名称
        menuList.forEach(main->{
            log.debug("{},{}",main.getMenuId(),main.getMenuName());
            // 对每个主菜单，遍历其子菜单，打印子菜单的ID、名称和URL
            main.getChildren().forEach(sub->{
                log.debug("   {},{},{}",sub.getMenuId(),sub.getMenuName(),sub.getMenuUrl());
            });
        });

    }

}
