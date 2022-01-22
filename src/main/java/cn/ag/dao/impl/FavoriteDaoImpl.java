package cn.ag.dao.impl;

import cn.ag.dao.FavoriteDao;
import cn.ag.domain.Favorite;
import cn.ag.domain.Route;
import cn.ag.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 用户是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite isFavorite(int rid, int uid) {
        Favorite  favorite = null;
        try {
            String sql="select * from tab_favorite where  rid =? and uid=?;";
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
        }
        return favorite;
    }

    /**
     * 收藏总数量
     * @param rid
     * @return
     */
    @Override
    public int findCount(int rid) {
        String sql="select count(*) from tab_favorite where  rid =?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        return count;
    }

    /**
     *添加收藏记录
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
         String sql="insert into travel2.tab_favorite values (?,?,?)";
       jdbcTemplate.update(sql,rid,new Date(),uid);

    }

    /**
     *
     * 删除用户收藏的功能
     * @param rid
     * @param uid
     */
    @Override
    public void removefavorite(int rid, int uid) {
        String sql="delete from tab_favorite where rid =? and uid= ?";
        jdbcTemplate.update(sql,rid,uid);
    }

    @Override
    public List<Route> findhotfavorite(int count) {
        String sql= null;
        try {
            sql = "SELECT rid,COUNT(rid) AS COUNT FROM tab_favorite GROUP BY rid ORDER BY COUNT(rid) DESC  LIMIT 0, ?";
        } catch (Exception e) {
        }
        return  jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),count);
    }
}
