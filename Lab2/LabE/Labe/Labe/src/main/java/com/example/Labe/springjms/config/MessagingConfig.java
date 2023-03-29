package com.example.Labe.springjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class MessagingConfig {

    @Bean // Serializar el mensaje como mensaje de texto
    public MessageConverter jacksonHmsMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean(name ="pubSubNotification")
    public PublishSubscribeChannel channel(){
        return new PublishSubscribeChannel();
    }

}
