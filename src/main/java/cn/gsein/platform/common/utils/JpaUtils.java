package cn.gsein.platform.common.utils;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

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
}
