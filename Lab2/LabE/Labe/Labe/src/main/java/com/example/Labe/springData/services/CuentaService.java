package com.example.Labe.springData.services;

import com.example.Labe.springData.criteria.CuentaSpecification;
import  com.example.Labe.springData.dto.CuentaDto;
import  com.example.Labe.springData.model.Cuenta;
import com.example.Labe.springData.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
}
