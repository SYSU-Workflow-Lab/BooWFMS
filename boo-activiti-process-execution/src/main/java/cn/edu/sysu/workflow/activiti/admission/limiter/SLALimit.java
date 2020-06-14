package cn.edu.sysu.workflow.activiti.admission.limiter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * SLA限制要求
 * @author: Gordan Lin
 * @create: 2019/12/21
 **/
public class SLALimit {

    // 一个级别对应的每秒请求到达数
    public static final int REQUEST_RATE_PER_LEVEL = 5;

    // 一个级别对应的响应时间要求
    public static final int RESPONSE_TIME_PER_LEVEL = 1000;

    public static LoadingCache<String, RateLimiter> requestRateLimiterCaches = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) throws Exception { // key要求：tenantId-rarLevel
                    int rarLevel = Integer.valueOf(key.substring(key.lastIndexOf('-')+1));
                    return RateLimiter.create(rarLevel*REQUEST_RATE_PER_LEVEL);
                }
            });

}
