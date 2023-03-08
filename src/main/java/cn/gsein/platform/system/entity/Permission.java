package cn.gsein.platform.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

import cn.gsein.platform.system.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_permission")
@Data
public class Permission extends BaseEntity {

    /**
    * 主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 权限字符串
    */
    @Column
    private String name;


}
