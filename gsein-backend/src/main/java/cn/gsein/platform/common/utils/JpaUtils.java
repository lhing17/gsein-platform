package cn.gsein.platform.common.utils;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

public final class JpaUtils {
    private JpaUtils() {
    }

    /**
     * 将字符串解析为Sort对象
     *
     * @param sort 排序字符串，格式为："id desc,name asc"，多个排序字段用逗号分隔，如果是升序，asc可以省略
     * @return Sort对象
     */
    public static Sort parseSort(String sort) {
        if (!StringUtils.hasText(sort)) {
            return Sort.unsorted();
        }
        String[] sortArray = sort.split(",");
        Sort.Order[] orders = new Sort.Order[sortArray.length];
        for (int i = 0; i < sortArray.length; i++) {
            String[] sortItem = sortArray[i].split(" ");
            if (sortItem.length == 1) {
                orders[i] = new Sort.Order(Sort.Direction.ASC, sortItem[0]);
            } else {
                orders[i] = new Sort.Order(Sort.Direction.fromString(sortItem[1]), sortItem[0]);
            }
        }
        return Sort.by(orders);
    }

    /**
     * 将实体类中的空字符串设置为null
     */
    public static void setEmptyStringToNull(Object entity) {
        // 使用反射获取实体类中的所有属性
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 设置私有属性可访问
            field.setAccessible(true);
            try {
                // 获取属性值
                Object value = field.get(entity);
                if (value instanceof String && !StringUtils.hasText((String) value)) {
                    // 如果属性值是空字符串，设置为null
                    field.set(entity, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
