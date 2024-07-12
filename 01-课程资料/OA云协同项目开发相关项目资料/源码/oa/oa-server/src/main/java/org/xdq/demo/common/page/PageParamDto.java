package org.xdq.demo.common.page;




public class PageParamDto {


    private Integer pageNum = 1;//请求页码


    private Integer pageSize = 10;//每页允许最大记录数

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
