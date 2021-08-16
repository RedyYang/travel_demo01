package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //查询用户是否存在
    User findByUsername(String username);
    //保存用户数据
    void save(User user);

    //通过激活码寻找用户
    User findByCode(String code);

    //更新激活状态
    void updateStatus(User user);
    //通过用户名密码寻找用户
    User findByUsernameAndPassword(String username, String password);
}
