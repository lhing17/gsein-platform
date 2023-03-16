package cn.gsein.platform.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_operation_log")
@Data
public class OperationLog extends BaseEntity {

    /**
    * 主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 操作内容描述
    */
    @Column
    private String description;

    /**
    * IP地址
    */
    @Column
    private String ip;

    /**
    * 方法名
    */
    @Column
    private String method;

    /**
    * 操作类型
    */
    @Column
    private Integer operationType;

    /**
    * 参数
    */
    @Column
    private String params;

    /**
    * 返回结果
    */
    @Column
    private String result;

    /**
    * 操作时间
    */
    @Column
    private LocalDateTime time;

    /**
     * 耗时
     */
    @Column
    private Long duration;

    /**
    * 操作用户名
    */
    @Column
    private String username;


}
