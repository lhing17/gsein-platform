package cn.gsein.platform.system.dao;

import cn.gsein.platform.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户持久层
 */
@Repository
public interface UserDao extends BaseDao<User> {


}
