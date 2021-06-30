package com.chauncy.cloud.client.config;

import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.common.enums.system.exception.Code;
import com.chauncy.cloud.common.exception.FeignException;
import com.chauncy.cloud.common.utils.JSONUtils;
import com.google.gson.*;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Author cheng
 * @create 2020-03-15 16:15
 *
 * feign调用过程配置 异常处理、localDateTime转换、实体类直接接收返回数据
 */
@Slf4j
@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration
@ConditionalOnClass(Feign.class)
public class MyFeignClientConfig {

    private int feignOkHttpReadTimeout = 60;
    private int feignConnectTimeout = 60;
    private int feignWriteTimeout = 120;

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(feignOkHttpReadTimeout, TimeUnit.SECONDS)
                .connectTimeout(feignConnectTimeout, TimeUnit.SECONDS)
                .writeTimeout(feignWriteTimeout, TimeUnit.SECONDS)
//				.connectionPool(new ConnectionPool(int maxIdleConnections, long keepAliveDuration, TimeUnit timeUnit))   //自定义链接池
//				.addInterceptor(XXXXXXXInterceptor) 	//自定义拦截器
                .build();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new MyErrorDecoder();
    }


    class MyErrorDecoder implements ErrorDecoder {


        @Override
        public Exception decode(String methodKey, Response response) {
            Result result = Result.error(Code.FEIGN_CALL_ERROR);
            try {
                String body = Util.toString(response.body().asReader());
                result = JSONUtils.parseObject(body, Result.class);
                log.error("\n ===== feign 调用异常, 调用方法[{}] \n ===== 返回信息[{}]", methodKey, body);
            } catch (IOException e) {
                log.error("feign io 异常", e);
            }
            return new FeignException(result);
        }
    }

    private static final Gson GSON;
    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();

        final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(fmt)));
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) -> {
            log.info("", jsonElement.getAsString());
            return LocalDateTime.parse(jsonElement.getAsString(), fmt);
        });

        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.format(dateFmt)));
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) -> LocalDate.parse(jsonElement.getAsString(), dateFmt));

        gsonBuilder.serializeNulls();
        GSON = gsonBuilder.create();
    }

    @Bean
    public Encoder feignEncoder(){
        HttpMessageConverter jacksonConverter = new GsonHttpMessageConverter(GSON);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

    @Bean
    public Decoder feignDecoder() {
        HttpMessageConverter jacksonConverter = new GsonHttpMessageConverter(GSON);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    /**
     * @Author chauncy
     * @Date 2020-04-06 20:48
     * @param
     * @return
     *   日志级别
     **/
    @Bean
    Logger.Level feignLoggerLevel(){

        return Logger.Level.FULL;
    }

}
