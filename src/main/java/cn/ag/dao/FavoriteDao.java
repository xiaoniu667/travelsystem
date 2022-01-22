package cn.ag.dao;

import cn.ag.domain.Favorite;
import cn.ag.domain.Route;

import java.util.List;

public interface FavoriteDao
{
     Favorite isFavorite(int rid, int uid);
    int findCount(int rid);

    void addFavorite(int rid, int uid);

    void removefavorite(int rid, int uid);

    List<Route> findhotfavorite(int count);
}
