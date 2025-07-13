package ru.danil.algos.ostock.kafkaTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Component
@Slf4j
public class MyConsumer {
    @Bean
    public Consumer<String> mySource() {
        return message -> {
            System.out.println("Received message: " + message);
        };
    }
}