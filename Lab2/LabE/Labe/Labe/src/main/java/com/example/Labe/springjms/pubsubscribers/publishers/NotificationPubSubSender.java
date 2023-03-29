package com.example.Labe.springjms.pubsubscribers.publishers;
import com.example.Labe.springData.dto.CuentaDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface NotificationPubSubSender {
    @Gateway(requestChannel = "pubSubNotification")
    Message<String> sendNotification(Message<CuentaDto> message);
}