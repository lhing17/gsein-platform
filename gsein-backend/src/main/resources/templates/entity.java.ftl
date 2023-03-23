package ${basePackage}.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

import cn.gsein.platform.system.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "${tableName}")
@Data
public class ${className} extends BaseEntity {

    /**
    * 主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<#list fields as field>
    /**
    * ${field.comment}
    */
    @Column
    private ${field.type.javaType} ${field.name};

</#list>

}
