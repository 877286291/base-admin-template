package top.houyuji.common.cache.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.houyuji.common.cache.core.BaseAdminCache;
import top.houyuji.common.cache.core.ICache;


@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "base.admin.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CacheAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public BaseAdminCache cache(
            ICache redisCache,
            CacheProperties cacheProperties) {
        BaseAdminCache baseAdminCache = new BaseAdminCache();
        baseAdminCache.setCache(redisCache);
        baseAdminCache.setPrefix(cacheProperties.getPrefix());
        return baseAdminCache;
    }
}