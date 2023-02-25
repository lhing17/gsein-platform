package cn.gsein.platform.system.service;

import cn.gsein.platform.system.entity.BaseEntity;

public interface BaseService<T extends BaseEntity> {


    T findById(Long id);

    void save(T entity);

    void updateById(T entity);

    void deleteById(Long id);
}
