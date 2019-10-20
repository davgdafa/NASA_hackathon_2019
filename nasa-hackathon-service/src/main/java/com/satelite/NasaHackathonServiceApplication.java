package com.satelite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication(scanBasePackages={"com.satelite"})
public class NasaHackathonServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(NasaHackathonServiceApplication.class, args);
    }
    
}
