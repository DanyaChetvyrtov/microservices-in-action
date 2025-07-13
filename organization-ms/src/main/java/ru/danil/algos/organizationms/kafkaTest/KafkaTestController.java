package ru.danil.algos.organizationms.kafkaTest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka/test")
@RequiredArgsConstructor
public class KafkaTestController {
    private final MyProducer myProducer;

    @GetMapping("/send")
    public void test() {
        System.out.println("HELLLLLLLLO");
        myProducer.sendMessage("Simple test");
    }
}
