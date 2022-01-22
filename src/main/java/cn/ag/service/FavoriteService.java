package cn.ag.service;

import cn.ag.domain.PageBean;
import cn.ag.domain.Route;

import java.util.List;

public interface FavoriteService {

    public boolean isFavorite(int rid, int uid);

    void addFavorite(int rid, int uid);
    public void removefavorite(int rid, int uid);


    PageBean<Route> myFavorite(int uid,int currentPage,int PageSize);

    List<Route> findhotfavorite(int count);
}
