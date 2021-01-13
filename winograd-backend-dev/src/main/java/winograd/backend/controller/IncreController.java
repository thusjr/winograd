package winograd.backend.controller;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


import winograd.lucene.service.IndexManager;

@Configuration 
@EnableScheduling  
public class IncreController {
        private IndexManager iManager = new IndexManager();
        @Scheduled(cron = "0 0 0/3 * * ?") //每3小时进行一次检查更新
        public void incrementTasks() {
            System.err.println("开始定时增量索引: " + LocalDateTime.now());
            try {
                iManager.createIncreIndex("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
