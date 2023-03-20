package cn.gsein.platform.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_user")
@Data
@ApiModel("用户实体类")
public class User extends BaseEntity implements UserDetails {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 用户名
     */
    @Column
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 性别 0-男 1-女
     */
    @Column
    @ApiModelProperty("性别 0-男 1-女")
    private Integer gender;
    /**
     * 密码
     */
    @Column
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用于密码加密的盐值
     */
    private String salt;

    /**
     * 身份证号
     */
    @Column
    @ApiModelProperty("身份证号")
    private String idCard;


    /**
     * 邮箱
     */
    @Column
    @ApiModelProperty("邮箱")
    private String email;


    /**
     * 电话
     */
    @Column
    @ApiModelProperty("电话")
    private String phone;


    /**
     * 生日
     */
    @Column
    @ApiModelProperty("生日")
    private LocalDateTime birthday;

    @Column
    @ApiModelProperty("年龄")
    private Integer age;

    /**
     * 状态 1-正常 2-禁用
     */
    @Column
    @ApiModelProperty("状态 1-正常 2-禁用")
    private Integer status;

    /**
     * 上次登录时间
     */
    @Column
    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLogin;

    @ManyToMany
    @JoinTable(name="system_user_role",
            joinColumns=
            @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="role_id", referencedColumnName="id")
    )

    @ApiModelProperty("用户角色")
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", idCard='" + idCard + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", status=" + status +
                ", lastLogin=" + lastLogin +
                "} " + super.toString();
    }

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
