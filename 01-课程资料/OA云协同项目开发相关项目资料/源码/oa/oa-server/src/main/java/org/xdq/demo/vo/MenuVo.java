package org.xdq.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {

    private Integer menuId;
    private String menuName;
    private String menuUrl;

    private List<MenuVo> children;
}
