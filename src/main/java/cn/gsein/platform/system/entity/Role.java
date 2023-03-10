package cn.gsein.platform.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

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
    private String roleKey;

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

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(name="system_role_permission",
            joinColumns=
            @JoinColumn(name="role_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="permission_id", referencedColumnName="id")
    )
    private List<Permission> permissions;


}
