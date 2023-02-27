package cn.gsein.platform.system.service;

import cn.gsein.platform.system.entity.User;

public interface UserService extends BaseService<User> {
    void login(User user);
}
