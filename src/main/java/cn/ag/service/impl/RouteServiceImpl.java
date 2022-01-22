package cn.ag.service.impl;

import cn.ag.dao.FavoriteDao;
import cn.ag.dao.RouteDao;
import cn.ag.dao.RouteImgDao;
import cn.ag.dao.SellerDao;
import cn.ag.dao.impl.FavoriteDaoImpl;
import cn.ag.dao.impl.RouteDaoImpl;
import cn.ag.dao.impl.RouteImgDaoImpl;
import cn.ag.dao.impl.SellerDaoImpl;
import cn.ag.domain.PageBean;
import cn.ag.domain.Route;
import cn.ag.domain.RouteImg;
import cn.ag.domain.Seller;
import cn.ag.service.RouteService;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {
       private  RouteDao routeDao= new RouteDaoImpl();
       private RouteImgDao routeImgDao= new RouteImgDaoImpl();
    private SellerDao sellerDao= new SellerDaoImpl();
    private FavoriteDao favoriteDao= new FavoriteDaoImpl();
    /**
     *
     * 对pagebean对象进行封装
     * @return
     */
    @Override
    public PageBean<Route> pagebean(int cid, int currentPage, int PageSize,String rname) {
        PageBean<Route> routePageBean = new PageBean<Route>();
        //设置当前页码
          routePageBean.setCurrentPage(currentPage);
          //设置总记录数
          routePageBean.setTotalcount(routeDao.findtotalcount(cid,rname));
          //设置页的大小
        routePageBean.setPageSize(PageSize);
        //设置总页数
        routePageBean.setTotalPage(routeDao.findtotalcount(cid,rname)%PageSize==0?routeDao.findtotalcount(cid,rname) / PageSize :(routeDao.findtotalcount(cid,rname) / PageSize) + 1 );
         //设置每页展示的路线条目
          int start= (currentPage-1)*PageSize;
          routePageBean.setList(routeDao.findroute(cid,start,PageSize,rname));


        return routePageBean;
    }
//findrouteindexguowai

    /**
     * 首先旅游路线展示
     * @return
     */
    @Override
    public List<Route> findrouteindex() {
        return routeDao.findrouteindex();
    }

    @Override
    public List<Route> findrouteindexguowai() {
        return routeDao.findrouteindexguowai();
    }

    @Override
    public Route findOne(int rid) {
        //根据id查询route对象
        Route route = routeDao.findOne(rid);
        //根据rid查询查询table_route_img，将其设置到route对象中去
        List<RouteImg> routeImg = routeImgDao.findRouteImg(rid);
        route.setRouteImgList(routeImg);
        //根据卖家的id查询table_seller，将其设置到route对象中去
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setSeller(seller);
       int count=favoriteDao.findCount(route.getRid());
       route.setCount(count);
        return route;
    }

    /**
     * 人气旅游页面展示功能
     * @return
     */
    @Override
    public List<Route> findPopularRoute(int count) {
        ArrayList<Route> route = new ArrayList<>();
        List<Route> route1=   routeDao.findPopularRoute(count);
        for (Route route2 : route1) {
            Route indexroute = routeDao.findOne(route2.getRid());
            route.add(indexroute);
        }
        return route;
    }

    @Override
    public List<Route> findindexNewRoute(int count) {
        return routeDao.findindexNewRoute(count);
    }

    @Override
    public List<List<Route>> findindexThemeRoute(int count) {
        return  routeDao.findindexThemeRoute(count);
    }
/**
 *
 * 收藏路线排行榜
 */
    @Override
    public PageBean<Route> favoriterank(int currentPage, String rname, int first, int last, int pageSize) {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
      int totalcount=routeDao.findfavoriterankcount(rname,first,last);
        pb.setTotalcount(totalcount);
       int totalpage=totalcount%pageSize==0?totalcount/pageSize:totalcount/pageSize+1;
       pb.setTotalPage(totalpage);
        int start= (currentPage-1)*pageSize;
        List<Route> routeList=routeDao.favoriterank(rname,first,last,start,pageSize);
        for (Route route : routeList) {
            int count = favoriteDao.findCount(route.getRid());
            route.setCount(count);
        }
        pb.setList(routeList);
        return pb;
    }

    /**
     *
     * 查询所有路线
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pagebean1(int currentPage, int pageSize, String rname) {
        PageBean<Route> routePageBean = new PageBean<Route>();
        //设置当前页码
        routePageBean.setCurrentPage(currentPage);
        //设置总记录数
        routePageBean.setTotalcount(routeDao.findtotalcount1(rname));
        //设置页的大小
        routePageBean.setPageSize(pageSize);
        //设置总页数
        routePageBean.setTotalPage(routeDao.findtotalcount1(rname)%pageSize==0?routeDao.findtotalcount1(rname) / pageSize :(routeDao.findtotalcount1(rname) / pageSize) + 1 );
        //设置每页展示的路线条目
        int start= (currentPage-1)*pageSize;
        routePageBean.setList(routeDao.findroute1(start,pageSize,rname));


        return routePageBean;

    }


}
