package cn.ag.dao;

import cn.ag.domain.User;

public interface  UserDao {
    /**
     *
     * 根据User对象查找username
     * @param username
     * @return
     */
    public User findbyusername(String username);

    /**
     *
     * 保存User对象
     * @param user
     */
    public  void save (User user);

    User findbycode(String code);

    void updateStatus(User user);



    User findbyusernameandpassword(String username, String password);
}
