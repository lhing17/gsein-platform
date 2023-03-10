package cn.gsein.platform.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_permission")
@Data
public class Permission extends BaseEntity implements GrantedAuthority {

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

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    @Override
    public String getAuthority() {
        return name;
    }
}
