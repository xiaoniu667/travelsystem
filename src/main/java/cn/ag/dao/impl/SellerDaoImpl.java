package cn.ag.dao.impl;

import cn.ag.dao.SellerDao;
import cn.ag.domain.Seller;
import cn.ag.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findSeller(int sid) {
        String sql="select * from tab_seller where sid = ?;";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
