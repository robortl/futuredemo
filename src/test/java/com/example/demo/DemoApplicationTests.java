package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MultiThreadConfig.class })
public class DemoApplicationTests {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Test
    public void contextLoads() {
        String queryStr = "query";
        //构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
        FutureTask<String> future = new FutureTask<String>(new UseFuture(queryStr));
        FutureTask<String> future2 = new FutureTask<String>(new UseFuture(queryStr));

        //这里提交任务future,则开启线程执行RealData的call()方法执行
        //submit和execute的区别： 第一点是submit可以传入实现Callable接口的实例对象， 第二点是submit方法有返回值
        Future f1 = taskExecutor.submit(future);//单独启动一个线程A去执行
        Future f2 = taskExecutor.submit(future2);//单独启动一个线程B去执行
        System.out.println("请求完毕");

        try {
            //在线程A、B的执行过程中，主线程main可以做额外的数据操作，也就是主程序执行其他业务逻辑
            System.out.println("处理实际的业务逻辑...");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
            System.out.println("数据：" + future.get());//future.get()获取线程A执行任务的结果
            System.out.println("数据：" + future2.get());//future2.get()获取线程B执行任务的结果
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
