package com.example.Labe.springjms.pubsubscribers;

import com.example.Labe.springData.dto.CuentaDto;
import com.example.Labe.springData.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ProcesadorNotificacionClientes {
    @ServiceActivator(inputChannel = "pubSubNotification")
    public Message<String> consumirMensajeParaEnvioFormatoCliente(Message<CuentaDto> message){
        CuentaDto cuentaDto = message.getPayload();
        log.info("consumirMensajeParaEnvioFormatoCliente");
        //Logica que necesitamos
        return MessageBuilder.withPayload("Mensaje recibido por ProcesadorNotificacionClientes")
                .build();

    }

}
