package cn.gsein.platform.system.service.impl;

import cn.gsein.platform.system.dao.UserDao;
import cn.gsein.platform.system.entity.User;
import cn.gsein.platform.system.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {


    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public void save(User entity) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        entity.setPassword(encoder.encode(entity.getPassword()));
        super.save(entity);
    }

    @Override
    public void login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 使用spring security的认证方法
        authenticationManager.authenticate(token);



    }

}
