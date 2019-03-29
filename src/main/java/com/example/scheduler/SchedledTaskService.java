package com.example.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一个定时任务
 */
@Service
public class SchedledTaskService {
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("每隔五秒执行一次" + dataFormat.format(new Date()));
    }
    @Scheduled(cron = "0 58 18 ? * *")
    public void fixTimeExecution () {
        System.out.println("在指定时间:" + dataFormat.format(new Date()) + "执行");
    }
}
