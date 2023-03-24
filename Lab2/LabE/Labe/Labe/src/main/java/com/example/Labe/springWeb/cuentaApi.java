package com.example.Labe.springWeb;


import com.example.Labe.springData.dto.ClienteDto;
import com.example.Labe.springData.dto.CuentaDto;
import com.example.Labe.springData.services.ClienteService;
import com.example.Labe.springData.services.CuentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/api/cuenta")
@Slf4j
public class cuentaApi {

    @Autowired

    private CuentaService cuentaService;

    @GetMapping(value = "/{id}", produces =MediaType.APPLICATION_JSON_VALUE)
    public List<CuentaDto> buscarCuenta(@PathVariable int id){
        log.info("Busqueda de Cuentas; {}", id);
        return cuentaService.buscarCuentasPorCliente(id);
    }

    @PostMapping
    public void guardarCuenta(@RequestBody CuentaDto cuentaDto){
        log.info("Busqueda de Cuenta; {}", cuentaDto);
        cuentaService.creacionDeCuenta(cuentaDto);
    }

    @PutMapping("/{id}")
    public void desactivarCuentasPorCliente_id(@PathVariable Integer id){
        cuentaService.desactivarCuentasPorCliente_id(id);
    }
/*
    @GetMapping(value = "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ClienteDto buscarClienteXML(@PathVariable int id){
        log.info("Busqueda de Cliente; {}", id);
        return clienteService.obtenerCliente(id);
    }


    @GetMapping(value = "/xml/json/{id}", produces ={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ClienteDto buscarClienteXMLJson(@PathVariable int id){
        log.info("Busqueda de Cliente; {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping(value = "/parameter", produces =MediaType.APPLICATION_JSON_VALUE)
    public ClienteDto buscarClienteParametro(@RequestParam int id){
        log.info("Busqueda de Cliente; {}", id);
        return clienteService.obtenerCliente(id);
    }



    @PostMapping
    public void guardarCliente(@Valid @RequestBody ClienteDto clienteDto){
        log.info("Busqueda de Cliente; {}", clienteDto);
        clienteService.insertarCliente(clienteDto);
    }

    @GetMapping(value = "/all")
    public List<ClienteDto> buscarTodosClientes(){

       return clienteService.obtenerTodosClientes();
    }

    @PutMapping
    public void ActualizarCliente(@RequestBody ClienteDto clienteDto){
        log.info("Busqueda de Cliente; {}", clienteDto);
        clienteService.actualizarCliente(clienteDto);
    }

    @DeleteMapping(value="/{id}")
    public void eliminarCliente(@PathVariable int clienteId){
        log.info("Busqueda de Cliente; {}", clienteId);
        clienteService.eliminarCliente(clienteId);
    }*/

}
