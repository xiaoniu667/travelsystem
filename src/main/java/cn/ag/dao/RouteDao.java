package cn.ag.dao;

import cn.ag.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     *
     * 查询总记录数
     */
    public int findtotalcount(int cid,String rname);


    /**
     *
     *查询route路线集合
     */
    public List<Route> findroute(int cid,int start,int PageSize,String rname);


     public  List<Route> findrouteindex();
     public  List<Route> findrouteindexguowai();

     public  Route findOne(int rid);


    List<Route> findPopularRoute(int count);

    List<Route> findindexNewRoute(int count);


 List<List<Route>> findindexThemeRoute(int count);

    int findfavoriterankcount(String rname, int first, int last);

    List<Route> favoriterank( String rname, int first, int last, int start, int pageSize);


    int findtotalcount1(String rname);

    List<Route> findroute1(int start, int pageSize, String rname);
}
