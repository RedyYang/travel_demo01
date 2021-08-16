package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    //注册
    boolean regist(User user);

    //激活用户
    boolean active(String code);
    //登录
    User login(User user);
}
