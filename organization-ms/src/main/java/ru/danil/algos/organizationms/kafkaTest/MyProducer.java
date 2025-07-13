package ru.danil.algos.organizationms.kafkaTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyProducer {
    private final StreamBridge streamBridge;

    public MyProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendMessage(String message) {
        streamBridge.send("mySource-out-0", message);
    }
}