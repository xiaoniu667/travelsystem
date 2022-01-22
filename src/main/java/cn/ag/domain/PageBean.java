package cn.ag.domain;

import java.util.List;

public class PageBean<T> {

    private  int totalcount;//总记录数
    private  int totalPage;//总页数
    private  int currentPage;//当前页
    private  int PageSize;//当前页展示的路线数量
    private List<T> list;

    @Override
    public String toString() {
        return "PageBean{" +
                "totalcount=" + totalcount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", PageSize=" + PageSize +
                ", list=" + list +
                '}';
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
}
