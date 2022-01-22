package cn.ag.dao.impl;

import cn.ag.dao.MyFavoriteDao;
import cn.ag.domain.MyFavorite;
import cn.ag.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MyFavoriteDaoImpl implements MyFavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 分页查询我的收藏
     * @param uid
     * @param start
     * @param PageSize
     * @return
     */
    @Override
    public List<MyFavorite> myFavorite(int uid, int start, int PageSize) {
        String sql = "SELECT * FROM tab_favorite WHERE uid = ? LIMIT ? , ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MyFavorite>(MyFavorite.class), uid, start, PageSize);
    }

    @Override
    public int findmyfavoritecount(int uid) {
        String sql="select count(*) from tab_favorite where uid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,uid);
    }
}
