package ru.danil.algos.organizationms.kafkaTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.danil.algos.organizationms.kafkaTest.model.OrganizationChangeModel;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyProducer {
    private final StreamBridge streamBridge;

    public void sendMessage(String message) {
        streamBridge.send("mySource-out-0", message);
    }

    public void publishingOrganizationChange(String action, String organizationId) {
        log.debug("Sending Kafka message {} for Organization id {}: ", action, organizationId);

        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                organizationId,
                UserContext.getCorrelationId()
        );

        streamBridge.send("mySource-out-0", MessageBuilder.withPayload(change).build());
    }
}