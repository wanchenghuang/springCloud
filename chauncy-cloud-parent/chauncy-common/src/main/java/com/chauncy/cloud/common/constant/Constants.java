package com.chauncy.cloud.common.constant;

/**
 * @Author cheng
 * @create 2020-03-04 20:19
 *
 * 常量
 */
public final class Constants {

    /**
     * common properties path
     */
    public static final String COMMON_PROPERTIES_PATH = "/common.properties";

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_USERNAME = "system";

    /**
     *  key of redis
     */
    public static final String GATEWAY_ROUTES = "gateway_routes::";

    /**
     *  route of mq
     */
    public static final String QUEUE_NAME = "event-gateway";
    public static final String EXCHANGE_NAME = "spring-boot-exchange";
    public static final String ROUTING_KEY = "gateway-route";

}
