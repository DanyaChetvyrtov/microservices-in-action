package ru.danil.algos.ostock.kafkaTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.danil.algos.ostock.kafkaTest.model.OrganizationChangeModel;

import java.util.function.Consumer;

@Component
@Slf4j
public class MyConsumer {
    @Bean
    public Consumer<OrganizationChangeModel> mySource() {
        System.out.println("IT WORKS FINE");
        return message -> {
            System.out.println("Received message: " + message);
        };
    }
}