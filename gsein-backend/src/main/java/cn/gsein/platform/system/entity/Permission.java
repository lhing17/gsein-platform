package cn.gsein.platform.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * 权限实体类
 */
@ApiModel(description = "权限实体类")
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_permission")
@Data
public class Permission extends BaseEntity implements GrantedAuthority {

    /**
    * 主键
    */
    @ApiModelProperty("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 权限字符串
    */
    @ApiModelProperty("权限字符串")
    @Column
    private String name;

    @ApiModelProperty(hidden = true)
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    @Override
    public String getAuthority() {
        return name;
    }
}
