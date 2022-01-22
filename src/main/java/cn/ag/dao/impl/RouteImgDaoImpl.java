package cn.ag.dao.impl;

import cn.ag.dao.RouteImgDao;
import cn.ag.domain.RouteImg;
import cn.ag.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private  JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *
     * 详情页面图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findRouteImg(int rid) {
        String sql="select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid) ;
    }
}
