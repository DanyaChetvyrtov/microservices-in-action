package ru.danil.algos.ostock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class LicenseMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseMsApplication.class, args);
    }

}
