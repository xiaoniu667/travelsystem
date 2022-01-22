package cn.ag.dao.impl;

import cn.ag.dao.UserDao;
import cn.ag.domain.User;
import cn.ag.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findbyusername(String username) {
        //定义sql语句
        User user=null;
        try {
            String sql="select * from tab_user where username=?";
            //执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public void save(User user) {
                   //定义sql语句
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)values(?,?,?,?,?,?,?,?,?)";
        //执行sql
       jdbcTemplate.update(sql,user.getUsername(),
               user.getPassword(),user.getName(),
               user.getBirthday(),user.getSex(),user.getTelephone(),
               user.getEmail(),user.getStatus(),user.getCode());

    }

    @Override
    public User findbycode(String code) {
        User user = null;
        try {
            String sql="select * from tab_user where code=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status='Y'where uid=?";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findbyusernameandpassword(String username, String password) {
        User u=null;
        try {
            String sql="select * from tab_user where username=? and password = ?";
            u  = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
        }
        return u;

    }


}
