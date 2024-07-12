package org.xdq.demo.common.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.Getter;
import org.xdq.demo.common.ex.SysException;


import java.io.Serializable;
import java.util.List;

@Getter
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    public static <T> PageVo<T> getPageVo(PageParamDto pageParamDto,QueryAction<T> queryAction)
            throws SysException{
        PageHelper.startPage(pageParamDto);//该设置语句必须紧挨在执行查询之前
        List<T> list = null;
        try {
            list = queryAction.executeQuery();
        } catch (Exception e) {
            throw new SysException(e);
        }
        PageInfo<T> pageInfo = new PageInfo<>(list);//该语句必须紧挨在执行查询之后

        PageVo<T> page = new PageVo<>();
        page.current = pageInfo.getPageNum();
        page.first=1;
        page.prev=pageInfo.getPrePage();
        page.next=pageInfo.getNextPage();
        page.last=pageInfo.getPages();

        page.pageSize=pageInfo.getPageSize();
        page.total=pageInfo.getTotal();
        page.pages=pageInfo.getPages();
        page.currSize=pageInfo.getSize();
        page.list=pageInfo.getList();

        return page;
    }

    private Integer current;//当前页
    private Integer first;


    private Integer prev;
    private Integer next;
    private Integer last;

    private Integer pageSize;
    private Long total;//总记录数
    private Integer pages;//总页数

    private Integer currSize;//当前页实际记录数


    private List<T> list;//当前页数据记录
}
