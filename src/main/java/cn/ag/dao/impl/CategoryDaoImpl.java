package cn.ag.dao.impl;

import cn.ag.dao.CategoryDao;
import cn.ag.domain.Category;
import cn.ag.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private  JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<Category> findAll() {
      String sql="select * from tab_category order by cid";
        List<Category> categories = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return  categories;
    }
}
