package cn.gsein.platform.system.service.impl;

import cn.gsein.platform.system.dao.UserDao;
import cn.gsein.platform.system.entity.User;
import cn.gsein.platform.system.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {


}
