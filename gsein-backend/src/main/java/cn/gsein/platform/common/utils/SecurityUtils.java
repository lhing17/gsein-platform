package cn.gsein.platform.common.utils;

import cn.gsein.platform.system.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * 获取当前登录用户
     */
    public static Optional<User> getLoginUser() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof User)
                .map(principal -> (User) principal);

    }

    /**
     * 获取当前登录用户的用户名
     */
    public static String getLoginUsername() {
        return getLoginUser().map(User::getUsername).orElse(null);
    }
}
