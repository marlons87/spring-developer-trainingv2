package com.example.Labe.springData.services;

import com.example.Labe.springData.criteria.CuentaSpecification;
import com.example.Labe.springData.dto.ClienteDto;
import  com.example.Labe.springData.dto.CuentaDto;
import  com.example.Labe.springData.model.Cuenta;
import com.example.Labe.springData.repository.ClienteRepository;
import com.example.Labe.springData.repository.CuentaRepository;
import com.example.Labe.springjms.dto.NotificationDto;
import com.example.Labe.springjms.pubsubscribers.publishers.NotificationPubSubSender;
import com.example.Labe.springjms.senders.NotificatioSender;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CuentaService {
    private CuentaRepository cuentaRepository;
    private CuentaSpecification cuentaSpecification;
    private NotificatioSender noticationSender;
    private ClienteService clienteService;
    private NotificationPubSubSender notificationPubSubSender;

    private CuentaDto fromCuentaToDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);
        return cuentaDto;
    }

    public List<CuentaDto> buscarCuentasDinamicamentePorCriterio(CuentaDto cuentaDtoFilter){
        return cuentaRepository.findAll(cuentaSpecification.buildFilter(cuentaDtoFilter))
                .stream().map(this::fromCuentaToDto).collect(Collectors.toList());
    }

    public List<CuentaDto> buscarCuentasPorCliente(int idCliente) {
        List<CuentaDto> cuentasPorCliente = new ArrayList<>();
        cuentaRepository.findByCliente_IdAndEstadoIsTrue(idCliente)
                .stream()
                .map(cuenta -> {
                    cuentasPorCliente.add(fromCuentaToDto(cuenta));
                    log.info("Cuenta de Cliente :{}", cuenta);
                    return cuenta;}
                ).collect(Collectors.toList());
        return cuentasPorCliente;
    }

    public void creacionDeCuenta(CuentaDto cuentaDto){
        Cuenta cuenta = new Cuenta();
        cuenta = fromDtoToCuenta(cuentaDto);
        cuentaRepository.save(cuenta);
        log.info("Cuenta: {} ", cuenta);
        //SMS en una cola para que luego se procese
        this.enviarNotificacion(cuentaDto);

    }

    private Cuenta fromDtoToCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = new Cuenta();
        BeanUtils.copyProperties(cuentaDto, cuenta);
        return cuenta;
    }

    public void desactivarCuentasPorCliente_id(Integer id)
    {
        List<Cuenta> cuentasPorCliente = new ArrayList<>();
        cuentasPorCliente = cuentaRepository.findByCliente_IdAndEstadoIsTrue(id);
        cuentasPorCliente.forEach(cuenta -> {
            cuenta.setEstado(false);
            cuentaRepository.save(cuenta);
        });
    }

    private void enviarNotificacion(CuentaDto cuentaDto){
        NotificationDto noticationDto = new NotificationDto();
        ClienteDto clienteDto = clienteService.obtenerCliente(cuentaDto.getClienteId());
        log.info("PREPARANDO ENVIO DE CLIENTE_ID: {}", clienteDto.getId());
        log.info("PREPARANDO ENVIO DE CLIENTE_TELEFONO: {}", clienteDto.getTelefono());
        noticationDto.setPhoneNumber(clienteDto.getTelefono());
        noticationDto.setMailBody("Estimado " + clienteDto.getNombre() + "tu cuenta fue creada");
        noticationSender.sendSms(noticationDto);
        Message<CuentaDto> message = MessageBuilder.withPayload(cuentaDto).build();
        notificationPubSubSender.sendNotification(message);
    }

}
