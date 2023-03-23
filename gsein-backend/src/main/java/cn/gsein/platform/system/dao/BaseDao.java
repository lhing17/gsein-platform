package cn.gsein.platform.system.dao;

import cn.gsein.platform.system.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseDao<T extends BaseEntity> extends JpaRepository<T, Long> {


    @Override
    @Query("select t from #{#entityName} t where t.id = ?1 and t.isDeleted = 0")
    @Transactional(readOnly = true)
    Optional<T> findById(Long id);

    @Override
    @Query("select t from #{#entityName} t where t.isDeleted = 0")
    @Transactional(readOnly = true)
    List<T> findAll();

    @Query("update #{#entityName} t set t.isDeleted = 1 where t.id = ?1")
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void logicDeleteById(Long id);
}
