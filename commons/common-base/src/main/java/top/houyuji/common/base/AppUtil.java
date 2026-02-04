package top.houyuji.common.base;

import cn.hutool.extra.spring.EnableSpringUtil;
import cn.hutool.extra.spring.SpringUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@EnableSpringUtil
public class AppUtil extends SpringUtil {
    /**
     * 根据注解获取bean
     *
     * @param annotationType 注解类型
     * @param type           bean类型
     * @param <T>            bean类型
     * @return bean
     */
    public static <T> Map<String, T> getBeansWithAnnotation(Class<? extends Annotation> annotationType,
                                                            Class<T> type) {

        Map<String, Object> beans = SpringUtil.getApplicationContext().getBeansWithAnnotation(annotationType);
        Map<String, T> result = new HashMap<>();
        beans.forEach((key, value) -> result.put(key, type.cast(value)));
        return result;
    }

    /**
     * 获取bean
     *
     * @param type         bean类型
     * @param defaultValue 默认值
     * @param <T>          bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> type, T defaultValue) {
        T value = SpringUtil.getBean(type);
        return value == null ? defaultValue : value;
    }
}