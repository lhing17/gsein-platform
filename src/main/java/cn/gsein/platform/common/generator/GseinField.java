package cn.gsein.platform.common.generator;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 代表生成的实体类中的一个字段
 */
@Data
@AllArgsConstructor
public class GseinField {
    private String name;
    private GseinFieldType type;
    private String comment;


}
