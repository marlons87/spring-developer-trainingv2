package com.example.Labe.springjms.senders;

import com.example.Labe.springjms.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificatioSender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendSms(NotificationDto notificationDto){
        log.info("Enviando sms a la cola con número de teléfono: {}", notificationDto.getPhoneNumber());
        jmsTemplate.convertAndSend("smsReceiverJms", notificationDto);
    }
}
