package cn.gsein.platform.system.shiro;

import cn.gsein.platform.system.dao.UserDao;
import cn.gsein.platform.system.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JpaRealm extends AuthenticatingRealm {

    @Resource
    private UserDao userDao;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user = userDao.findByUsername(usernamePasswordToken.getUsername());
        String password = user.getPassword();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        return info;
    }

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher md5 = new HashedCredentialsMatcher("md5");
        md5.setHashIterations(10);
        return md5;
    }
}
