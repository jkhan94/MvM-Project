package com.sparta.mvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication //(exclude = SecurityAutoConfiguration.class)
public class MvmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MvmApplication.class, args);
    }

}
