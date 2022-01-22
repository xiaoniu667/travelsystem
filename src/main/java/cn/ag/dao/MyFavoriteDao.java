package cn.ag.dao;

import cn.ag.domain.MyFavorite;

import java.util.List;

public interface MyFavoriteDao {

    public List<MyFavorite>   myFavorite (int uid,int start,int PageSize);

    public  int  findmyfavoritecount(int  uid);
}
