package cn.gsein.platform.system.service.impl;

import cn.gsein.platform.common.utils.JpaUtils;
import cn.gsein.platform.system.dao.RoleDao;
import cn.gsein.platform.system.entity.Role;
import cn.gsein.platform.system.service.RoleService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {

    @Override
    public Page<Role> findAll(Integer page, Integer size, String sort, Role condition) {
        // 将sort解析为Sort对象
        Sort sortObj = JpaUtils.parseSort(sort);

        // 构造分页对象
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);

        // 将查询条件中的空字符串设置为null
        JpaUtils.setEmptyStringToNull(condition);

        // 构造查询条件，将isDeleted设置为0，表示未删除的数据
        condition.setIsDeleted(0);

        // 对name和roleKey字段进行模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("roleKey", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Role> example = Example.of(condition, matcher);
        return dao.findAll(example, pageable);
    }
}
