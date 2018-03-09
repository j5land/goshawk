package com.j5land.goshawk.core.bean;

import brave.Tracer;
import brave.sampler.CountingSampler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import zipkin.Span;
import zipkin.reporter.GoshawkAsyncReporter;
import zipkin.reporter.kafka10.KafkaSender;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化BraveConnectionBean,需要在配置参数中，指定kafka地址
 */
public class Tracing implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(Tracing.class);
    //# defualt
    private static final String topic = "metrics.zipkin";
    private static final String ENV_DEV = "dev";
    private static final String ENV_TEST = "test";
    private static final String ENV_PROD = "prod";
    private Map<String, String> bootstrapServersDefaults = new HashMap<String, String>() {{
        put(ENV_DEV, "127.0.0.1:9092");
        put(ENV_TEST, "10.46.67.89:9092");
        put(ENV_PROD, "10.28.232.211:9092,10.28.232.193:9092,10.28.111.40:9092");
    }};


    //# dynamic
    private static String bootstrapServers;
    //# config
    private String springApplicationName;
    private String springApplicationEnv;
    private float springApplicationSamplerRate = 1.0F;

    //# object
    private static brave.Tracing tracing;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(springApplicationName)) {
            logger.error(" ### BraveClient Can't Initial Cause Of No springApplicationName Is Setting ### ");
            return;
        }
        if (StringUtils.isEmpty(springApplicationEnv)) {
            logger.error(" ### BraveClient Can't Initial Cause Of No env Is Setting ### ");
            return;
        }
        if (StringUtils.isEmpty(bootstrapServers)){
            bootstrapServers = bootstrapServersDefaults.get(springApplicationEnv);
            if (StringUtils.isEmpty(bootstrapServers)) {
                logger.error(" ### BraveClient Can't Initial Cause Of wring env Is Setting ### ");
                return;
            }
        }
        buildBraveClient();
    }

    /**
     * 客户端获得Tracing的方式
     *
     * @return
     */
    private brave.Tracing getTracing() {
        if (tracing == null) {
            buildBraveClient();
        }
        return tracing;
    }

    public Tracer getTracer() {
        brave.Tracing tracing = getTracing();
        if (tracing != null) {
            return tracing.tracer();
        }
        return null;
    }

    /**
     * 初始化Breace Client
     * 如果已经有了，那么不重新进行覆盖
     */
    private void buildBraveClient() {
        try {
            if (tracing != null) {
                return;
            }
            KafkaSender kafkaSender = KafkaSender.builder().bootstrapServers(bootstrapServers).topic(topic).build();
            GoshawkAsyncReporter<Span> reporter = GoshawkAsyncReporter.create(kafkaSender);
            tracing = brave.Tracing.newBuilder().localServiceName(springApplicationName).reporter(reporter).sampler(CountingSampler.create(springApplicationSamplerRate)).build();
        } catch (Exception e) {
            logger.error("build BraveClient error# " + e.getMessage());
        }
    }

    public String getSpringApplicationName() {
        return springApplicationName;
    }

    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    public String getTopic() {
        return topic;
    }

    public float getSpringApplicationSamplerRate() {
        return springApplicationSamplerRate;
    }

    public void setSpringApplicationSamplerRate(float springApplicationSamplerRate) {
        this.springApplicationSamplerRate = springApplicationSamplerRate;
    }

    public String getSpringApplicationEnv() {
        return springApplicationEnv;
    }

    public void setSpringApplicationEnv(String springApplicationEnv) {
        this.springApplicationEnv = springApplicationEnv;
    }

    public static String getBootstrapServers() {
        return bootstrapServers;
    }

    public static void setBootstrapServers(String bootstrapServers) {
        Tracing.bootstrapServers = bootstrapServers;
    }
}
