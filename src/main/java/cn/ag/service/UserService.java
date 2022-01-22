package cn.ag.service;

import cn.ag.domain.User;

public interface UserService {
    boolean regist(User user);

    boolean active(String code);
    User login(User user);
}
