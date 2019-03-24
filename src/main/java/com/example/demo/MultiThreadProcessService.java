package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MultiThreadProcessService {
//    public static final Logger logger = Logger.getLogger(MultiThreadProcessService.class);
    /*** 默认处理流程耗时1000ms
  */
    public void processSomething() {
        System.out.println("MultiThreadProcessService-processSomething" + Thread.currentThread() + "......start");
                 try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                     }
        System.out.println("MultiThreadProcessService-processSomething" + Thread.currentThread() + "......end");
    }

}
