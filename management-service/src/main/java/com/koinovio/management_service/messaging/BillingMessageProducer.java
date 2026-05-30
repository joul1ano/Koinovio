package com.koinovio.management_service.messaging;

import com.koinovio.management_service.config.RabbitMQConfig;
import com.koinovio.management_service.dto.messaging.BuildingExpensesMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.stereotype.Service;

import java.util.RandomAccess;

@Service
@RequiredArgsConstructor
public class BillingMessageProducer {
    private final AmqpTemplate amqpTemplate;

    public void sendBillingMessage(BuildingExpensesMessage message) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message,
                m -> {
                    m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return m;
                }
        );
    }
}
