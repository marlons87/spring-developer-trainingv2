package com.example.Labe.springData.services;

import  com.example.Labe.springData.criteria.ClienteSpecification;
import com.example.Labe.springData.dto.*;
import  com.example.Labe.springData.model.Cliente;
import com.example.Labe.springData.model.Cuenta;
import com.example.Labe.springData.model.Inversion;
import com.example.Labe.springData.model.Tarjeta;
import com.example.Labe.springData.repository.ClienteRepository;
import com.example.Labe.springData.repository.CuentaRepository;
import com.example.Labe.springData.repository.DireccionRepository;
import com.example.Labe.springData.repository.TarjetaRepository;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ClienteService {
    private ClienteRepository clienteRepository;
    private DireccionRepository direccionRepository;
    private CuentaRepository cuentaRepository;
    private TarjetaRepository tarjetaRepository;
    private ClienteSpecification clienteSpecification;

    public void insertarCliente (ClienteDto clienteDto){
        Cliente cliente = new Cliente();
        cliente.setApellido(clienteDto.getApellido());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setPaisNacimiento(clienteDto.getPaisNacimiento());
        clienteRepository.save(cliente);
    }

    public ClienteDto obtenerCliente (int idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> {throw new RuntimeException("Cliente no Existe");});
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setApellido(cliente.getApellido());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setCedula(cliente.getCedula());
        return clienteDto;
    }


    public List<ClienteDto> obtenerTodosClientes (){
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteRepository.findAll().
                         stream().
                         map(cliente -> {
                             clienteDtoList.add(fromClienteToDto(cliente));
                             return cliente;
                         }).collect(Collectors.toList());
        return clienteDtoList;
    }

    public ClienteDto actualizarCliente(ClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findById(clienteDto.getId())
                .orElseThrow(() -> {throw new RuntimeException("Clinete no Existe");});
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setPaisNacimiento(clienteDto.getPaisNacimiento());
        clienteRepository.save(cliente);
        return null;
    }

    public void actualizarCliente2(ClienteDto clienteDto){
        Cliente cliente = new Cliente();
        cliente.setId(clienteDto.getId());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());
        clienteRepository.save(cliente);
    }

    public void eliminarCliente(int clienteId){
        direccionRepository.deleteAllByCliente_Id(clienteId);
        cuentaRepository.deleteAllByCliente_Id(clienteId);
        clienteRepository.deleteById(clienteId);
    }

    public List<ClienteDto> obtenerClientesPorCodigoISOPaisYCuentasActivas(String codigoISOPais){
        List<ClienteDto> resultadoClientesDto = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findClientesByPaisNacimientoAndCuentas_EstadoIsTrue(codigoISOPais);
        clientes.forEach(cliente -> {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setId(cliente.getId());
            clienteDto.setApellido(cliente.getApellido());
            clienteDto.setNombre(cliente.getNombre());
            clienteDto.setCedula(cliente.getCedula());
            clienteDto.setPaisNacimiento(cliente.getPaisNacimiento());
            resultadoClientesDto.add(clienteDto);
            System.out.println(clienteDto);
        });
        return resultadoClientesDto;
    }

    public List<Cliente> buscarClientesPorApellido(String apellidos){
        return clienteRepository.buscarPorApellido(apellidos);
    }

    public List<ClienteDto> buscarClientesPorApellidoNativo(String apellidos){
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Tuple> tuples = clienteRepository.buscarPorApellidoNativo(apellidos);
        tuples.forEach(tuple -> {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setApellido((String) tuple.get("apellidos"));
            clienteDto.setNombre((String) tuple.get("nombre"));
            clienteDto.setCedula((String) tuple.get("cedula"));
            clienteDtos.add(clienteDto);
            System.out.println(tuple.get("apellidos"));
        });
        return clienteDtos;
    }

    public List<ClienteDto> buscarClienteExtranjerosPorTarjeta_Inactiva(){
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Tuple> tuples = clienteRepository.buscarClienteExtranjerosPorTarjeta_Inactiva();
        tuples.forEach(tuple -> {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setApellido((String) tuple.get("apellidos"));
            clienteDto.setNombre((String) tuple.get("nombre"));
            clienteDto.setCedula((String) tuple.get("cedula"));
            clienteDtos.add(clienteDto);
            System.out.println(tuple.get("apellidos"));
        });
        return clienteDtos;
    }

    private ClienteDto fromClienteToDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        return clienteDto;
    }

    public List<ClienteDto> buscarClientesDinamicamentePorCriterio(ClienteDto clienteDtoFilter){
        return clienteRepository.findAll(clienteSpecification.buildFilter(clienteDtoFilter))
                .stream().map(this::fromClienteToDto).collect(Collectors.toList());

    }

    /*public ProductosDto obtenerTodosLosProductosDeUnCliente(){
        //cuentaRepository.findAllById();
        //tarjetaRepository
        return null;
    }*/

    private CuentaDto fromCuentaToDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);
        return cuentaDto;
    }
    private TarjetaDto fromTarjetaToDto(Tarjeta tarjeta) {
        TarjetaDto tarjetaDto = new TarjetaDto();
        BeanUtils.copyProperties(tarjeta, tarjetaDto);
        return tarjetaDto;
    }

    private InversionDto fromInversionToDto(Inversion inversion) {
        InversionDto inversionDto = new InversionDto();
        BeanUtils.copyProperties(inversion, inversionDto);
        return inversionDto;
    }
    public ProductosDto obtenerTodosLosProductosDeUnClientePorId(int id){
        ProductosDto productosDto = new ProductosDto();
        List<CuentaDto> cuentaDtos = new ArrayList<>();
        cuentaRepository.findByCliente_Id(id).forEach(
                cuenta -> {
                    CuentaDto cuentaDto;
                    cuentaDto = fromCuentaToDto(cuenta);
                    cuentaDtos.add(cuentaDto);
                });
        productosDto.setCuentaDtos(cuentaDtos);

        List<TarjetaDto> tarjetaDtos = new ArrayList<>();
        tarjetaRepository.findByCliente_Id(id).forEach(
                tarjeta -> {
                    TarjetaDto tarjetaDto;
                    tarjetaDto = fromTarjetaToDto(tarjeta);
                    tarjetaDtos.add(tarjetaDto);
                });
        productosDto.setCuentaDtos(cuentaDtos);


        return productosDto;
    }

    public io.spring.guides.gs_producing_web_service.Cliente obtenerClienteParaSoap (int idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> {
                    throw new RuntimeException("Clinete no existe.");
                });

        io.spring.guides.gs_producing_web_service.Cliente clienteWS = new io.spring.guides.gs_producing_web_service.Cliente();
        clienteWS.setId(cliente.getId());
        clienteWS.setApellidos(cliente.getApellido());
        clienteWS.setNombre(cliente.getNombre());
        clienteWS.setCedula(cliente.getCedula());
        clienteWS.setTelefono(cliente.getTelefono());
        clienteWS.setPaisNacimiento(cliente.getPaisNacimiento());
        return clienteWS;

    }
}
