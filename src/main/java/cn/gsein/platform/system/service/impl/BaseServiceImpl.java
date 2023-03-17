package cn.gsein.platform.system.service.impl;

import cn.gsein.platform.common.utils.SecurityUtils;
import cn.gsein.platform.system.dao.BaseDao;
import cn.gsein.platform.system.entity.BaseEntity;
import cn.gsein.platform.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public abstract class BaseServiceImpl<D extends BaseDao<T>, T extends BaseEntity> implements BaseService<T> {

    @Autowired
    protected D dao;


    @Override
    public T findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public void save(T entity) {
        if (entity.getIsDeleted() == null) {
            entity.setIsDeleted(0);
        }
        SecurityUtils.getLoginUser()
                .ifPresent(user -> {
                    entity.setCreatedBy(user.getId());
                    entity.setUpdatedBy(user.getId());
                });
        if (entity.getCreatedTime() == null) {
            entity.setCreatedTime(LocalDateTime.now());
        }
        entity.setUpdatedTime(LocalDateTime.now());

        dao.save(entity);
    }

    @Override
    public void updateById(T entity) {
        SecurityUtils.getLoginUser()
                .ifPresent(user -> entity.setUpdatedBy(user.getId()));
        entity.setUpdatedTime(LocalDateTime.now());
        dao.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        dao.logicDeleteById(id);
    }
}
