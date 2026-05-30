package com.koinovio.billing_service.messaging;

import com.koinovio.billing_service.config.RabbitMQConfig;
import com.koinovio.billing_service.dto.BuildingExpensesMessage;
import com.koinovio.billing_service.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingMessageConsumer {
    private final BillingService billingService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(BuildingExpensesMessage message){
        log.info("Message for building with id: " + message.getBuildingId() + " received");
        billingService.processBill(message);
    }
}
