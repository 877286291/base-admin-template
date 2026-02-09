package top.houyuji.common.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 声明 base 前缀配置结构，用于 IDE/Spring 识别 application.yml 中的 base 顶层 key，消除 YAML_UNKNOWN_PROPERTY 告警。
 * 实际绑定仍使用 {@link CacheProperties}（base.admin.cache）。
 */
@Data
@ConfigurationProperties(prefix = "base")
public class BaseProperties {

    private Admin admin = new Admin();

    @Data
    public static class Admin {
        private Cache cache = new Cache();
    }

    @Data
    public static class Cache {
        private boolean enabled = true;
        private String prefix = "base_admin";
    }
}
