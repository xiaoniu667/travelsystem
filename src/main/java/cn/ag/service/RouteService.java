package cn.ag.service;

import cn.ag.domain.PageBean;
import cn.ag.domain.Route;

import java.util.List;

public interface RouteService {
    public  PageBean<Route> pagebean(int cid, int currentPage, int PageSize,String rname);
     public List<Route> findrouteindex();
     public List<Route> findrouteindexguowai();
     Route findOne(int rid);

    List<Route> findPopularRoute(int count);

    List<Route> findindexNewRoute(int count);

    List<List<Route>> findindexThemeRoute(int count);

    PageBean<Route> favoriterank(int currentPage, String rname, int first, int last, int pageSize);


    PageBean<Route> pagebean1(int currentPage, int pageSize, String rname);
}
