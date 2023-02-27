package cn.gsein.platform.system.service.impl;

import cn.gsein.platform.system.dao.UserDao;
import cn.gsein.platform.system.entity.User;
import cn.gsein.platform.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

    @Override
    public void save(User entity) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        SimpleHash hash = new SimpleHash("MD5", entity.getPassword(), salt, 10);
        entity.setPassword(hash.toHex());
        entity.setSalt(salt);
        super.save(entity);
    }

    @Override
    public void login(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }


    }
}
