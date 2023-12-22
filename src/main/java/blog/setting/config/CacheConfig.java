package blog.setting.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CacheConfig {
    // EhCache FactoryBean 등록
    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean() {
        return new EhCacheManagerFactoryBean();
    }
    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        // layout 용 cache
        CacheConfiguration lyaoutCacheConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(3600)
                .timeToLiveSeconds(3600)
                .maxEntriesLocalHeap(5)
                .memoryStoreEvictionPolicy("LRU")
                .name("layoutCaching");

        Cache layoutCache = new net.sf.ehcache.Cache(lyaoutCacheConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(layoutCache);

        CacheConfiguration recentPostCacheConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(3600)
                .timeToLiveSeconds(3600)
                .maxEntriesLocalHeap(100)
                .memoryStoreEvictionPolicy("LRU")
                .name("layoutRecentPostCaching");
        //net.sf.ehcache 는 오픈 소스 캐싱 라이브러리의 패키지 경로이다.
        Cache recentPostCache = new net.sf.ehcache.Cache(recentPostCacheConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(recentPostCache);

        CacheConfiguration recentCommentCacheConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(3600)
                .timeToLiveSeconds(3600)
                .maxEntriesLocalHeap(5)
                .memoryStoreEvictionPolicy("LRU")
                .name("layoutRecentCommentCaching");
        Cache recentCommentCache = new net.sf.ehcache.Cache(recentCommentCacheConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(recentCommentCache);

        CacheConfiguration rssConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(21600)
                .timeToLiveSeconds(21600)
                .maxEntriesLocalHeap(5)
                .memoryStoreEvictionPolicy("LRU")
                .name("seoCaching");
        Cache rssCache = new net.sf.ehcache.Cache(rssConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(rssCache);

        return new EhCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean().getObject()));
    }
}
