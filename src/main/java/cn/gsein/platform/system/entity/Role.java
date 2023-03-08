package cn.gsein.platform.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_role")
@Data
public class Role extends BaseEntity {

    /**
    * 主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 代表权限的字符串
    */
    @Column
    private String key;

    /**
    * 名称
    */
    @Column
    private String name;

    /**
    * 排序
    */
    @Column
    private Integer sort;


}
