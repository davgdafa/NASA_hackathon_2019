package com.satelite.jobs;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DataReaderScheduler {

    @Scheduled(fixedRate = 60000)
    public void refreshData() {
        System.out.println("Data being refreshed");
    }
}
