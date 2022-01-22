package cn.ag.service.impl;

import cn.ag.dao.FavoriteDao;
import cn.ag.dao.MyFavoriteDao;
import cn.ag.dao.RouteDao;
import cn.ag.dao.impl.FavoriteDaoImpl;
import cn.ag.dao.impl.MyFavoriteDaoImpl;
import cn.ag.dao.impl.RouteDaoImpl;
import cn.ag.domain.Favorite;
import cn.ag.domain.MyFavorite;
import cn.ag.domain.PageBean;
import cn.ag.domain.Route;
import cn.ag.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private MyFavoriteDao myFavoriteDao = new MyFavoriteDaoImpl();

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.isFavorite(rid, uid);
        return favorite != null;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.addFavorite(rid, uid);
    }

    @Override
    public PageBean<Route> myFavorite(int uid,int currentPage,int PageSize) {
        PageBean<Route> pb = new PageBean<>();
        //设置每页的大小
         pb.setPageSize(PageSize);
         //设置当前页
         pb.setCurrentPage(currentPage);
         //设置总记录数
        int totalCount = myFavoriteDao.findmyfavoritecount(uid);
        pb.setTotalcount(totalCount);
        //假如没有记录前台显示提示
        if(totalCount==0){
            return  pb;
        }
        int start = (currentPage - 1) * PageSize;
        int totalPage = (totalCount % PageSize == 0) ? (totalCount / PageSize) : (totalCount / PageSize + 1);
        pb.setTotalPage(totalPage);
        List<MyFavorite> myFavoriteList = myFavoriteDao.myFavorite(uid,start,PageSize);
        List<Route> routelist = new ArrayList<>();
        for (MyFavorite myFavorite : myFavoriteList) {
            int rid1 = myFavorite.getRid();
            //查出来的是每个用户的全部收藏路线
            Route route = routeDao.findOne(rid1);
            routelist.add(route);

        }
        pb.setList(routelist);

        return pb;
    }


    /**
     * 删除我的收藏
     * @param rid
     * @param uid
     */
    @Override
    public void removefavorite(int rid, int uid) {
        favoriteDao.removefavorite(rid,uid);
    }

    /**
     *
     * 热门排行榜
     * @param count
     * @return
     */
    @Override
    public List<Route> findhotfavorite(int count) {
        ArrayList<Route> hotroutes = new ArrayList<>();
        List<Route>route=favoriteDao.findhotfavorite(count);
        for (Route route1 : route) {
            Route hotroute = routeDao.findOne(route1.getRid());
            hotroutes.add(hotroute);
        }
        return hotroutes;
    }
}




