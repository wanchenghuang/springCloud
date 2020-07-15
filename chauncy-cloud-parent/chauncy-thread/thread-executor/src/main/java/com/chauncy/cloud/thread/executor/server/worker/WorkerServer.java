package com.chauncy.cloud.thread.executor.server.worker;

import com.chauncy.cloud.thread.executor.constant.Constants;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * @Author cheng
 * @create 2020-07-13 22:00
 */
@ComponentScan(basePackages = {"com.chauncy.cloud.thread"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
public class WorkerServer {

    private CountDownLatch latch;

    private final static Boolean isCombinedServer = false;


    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","worker");
        Thread.currentThread().setName(Constants.THREAD_NAME_WORKER_SERVER);
        new SpringApplicationBuilder(WorkerServer.class).web(WebApplicationType.NONE).run(args);
//        SpringApplication.run(WorkerServer.class, args);
    }

    @PostConstruct
    public void run(){

        //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        //public void await() throws InterruptedException { };
        //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
        //public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
        //将count值减1
        //public void countDown() { };

        latch = new CountDownLatch(1);
        if (!isCombinedServer) {
            try {
                latch.await();
            } catch (InterruptedException ignore) {
            }
        }
    }

}
