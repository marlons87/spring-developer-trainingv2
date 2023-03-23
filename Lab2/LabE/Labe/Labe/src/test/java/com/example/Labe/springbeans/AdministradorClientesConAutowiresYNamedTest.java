package com.example.Labe.springbeans;

import com.example.Labe.springData.dto.ClienteDto;
import com.example.Labe.springData.repository.ClienteRepository;
import com.example.Labe.springData.services.ClienteService;
import com.example.Labe.springbeans.dto.ClienteQueryDto;
import com.example.Labe.springbeans.dto.ClienteQueryType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@Slf4j
class AdministradorClientesConAutowiresYNamedTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AdministradorClientes defaultCedula;

    @Autowired
    @Qualifier("defaultNombres")
    private  AdministradorClientes administradorClientes;



    @BeforeEach
    void setupClientes()
    {
        var clientes = List.of(
                new ClienteDto(1, "Alberto", "Salazar", "1890000000", "0999714563", true, "CR"),
                new ClienteDto(2, "Rosa", "Salazar", "1890000001", "0983475616", true, "CR"),
                new ClienteDto(3, "Alexis", "Vivanco", "1890000002", "0983475616", true, "CR"),
                new ClienteDto(4, "Natalie", "Vivanco", "1890000003", "0983665616", true, "CR"),
                new ClienteDto(5, "Ximena", "Silva", "1890000004", "0983475616", true, "CR"),
                new ClienteDto(6, "Thalia", "Rodriguez", "1890000005", "0983475616", true, "CR"),
                new ClienteDto(7, "Jonh", "Rodriguez", "1890000006", "0983475616", true, "CR"),
                new ClienteDto(8, "Eduardo", "Guerra", "1890000007", "0983475616", true, "CR"),
                new ClienteDto(9, "Juan", "Vaca", "1890000008", "0983475616", true, "CR"),
                new ClienteDto(10, "Cristina", "Ortiz", "1890000009", "0983475616", true, "CR")
        );
        clientes.forEach(cliente -> clienteService.insertarCliente(cliente));
    }

    @Test
    void obtieneClientesSinDatos()
    {
        var administradorCliente = new AdministradorClientes(this.clienteRepository, ClienteQueryType.NOMBRES);
        var consulta = new ClienteQueryDto();
        consulta.setTextoBusqueda("mi texto");
        consulta.setTipoBusqueda(ClienteQueryType.NOMBRES);
        var resultado = administradorCliente.obtenerListaClientesPorCriterio(consulta);
        Assert.isTrue(resultado.size() == 0, "Sin resultados");
    }
    @Test
    void obtieneClientesConNombreExitoso()
    {
        var administradorCliente = new AdministradorClientes(this.clienteRepository, ClienteQueryType.NOMBRES);
        var consulta = new ClienteQueryDto();
        consulta.setTextoBusqueda("RAUL");
        consulta.setTipoBusqueda(ClienteQueryType.NOMBRES);
        var resultado = administradorCliente.obtenerListaClientesPorCriterio(consulta);
        Assert.isTrue(resultado.size() == 1, "Con resultados");
    }

    @Test
    void obtieneClientesConCedulaExitoso()
    {
        var administradorCliente = new AdministradorClientes(this.clienteRepository, ClienteQueryType.CEDULA);
        var consulta = new ClienteQueryDto();
        consulta.setTextoBusqueda("1100");
        consulta.setTipoBusqueda(ClienteQueryType.CEDULA);
        var resultado = administradorCliente.obtenerListaClientesPorCriterio(consulta);
        Assert.isTrue(resultado.size() == 1, "Con resultados");
    }

    @Test
    void obtieneClientesPreCargadosConCedulaExitoso()
    {
        var administradorCliente = new AdministradorClientes(this.clienteRepository, ClienteQueryType.CEDULA);
        var consulta = new ClienteQueryDto();
        consulta.setTextoBusqueda("1890000007");
        consulta.setTipoBusqueda(ClienteQueryType.CEDULA);
        var resultado = administradorCliente.obtenerListaClientesPorCriterio(consulta);
        Assert.isTrue(resultado.size() >= 1, "Con resultados");
    }

    @Test
    void obtenerListaClientesPorCriterioCedula() {
        AdministradorClientes administradorClientes = new AdministradorClientes(ClienteQueryType.CEDULA);
        administradorClientes.setClienteRepository(clienteRepository);
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTipoBusqueda(ClienteQueryType.CEDULA);
        clienteQueryDto.setTextoBusqueda("1890000002");
        //Se invoca al método respectivo
        List<ClienteDto> clienteDtos = administradorClientes.obtenerListaClientesPorCriterio(clienteQueryDto);
        Assertions.assertEquals(0, clienteDtos.size());
    }

    @Test
    void obtenerListaClientPorCriterioNombres(){
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTipoBusqueda(ClienteQueryType.CEDULA);
        clienteQueryDto.setTextoBusqueda("Salazar");
        List<ClienteDto> clienteDtos = administradorClientes.obtenerListaClientesPorCriterio(clienteQueryDto);
        System.out.println("Tamaño: " + clienteDtos.size());
        Assertions.assertEquals(0, clienteDtos.size());
    }

    @Test
    void administradorClientesBeanSession() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTipoBusqueda(ClienteQueryType.CEDULA);
        clienteQueryDto.setTextoBusqueda("Salazar");
        List<ClienteDto> clienteDtos = administradorClientes.obtenerListaClientesPorCriterio(clienteQueryDto);
        System.out.println("Tamaño: " + clienteDtos.size());
        Assertions.assertEquals(0, clienteDtos.size());
    }

    @Test
    void administradorClientesBeanRequest() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTipoBusqueda(ClienteQueryType.CEDULA);
        clienteQueryDto.setTextoBusqueda("Salazar");
        List<ClienteDto> clienteDtos = administradorClientes.obtenerListaClientesPorCriterio(clienteQueryDto);
        System.out.println("Tamaño: " + clienteDtos.size());
        Assertions.assertEquals(0, clienteDtos.size());
    }

    @Test
    void administradorClientesBeanApplication() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTipoBusqueda(ClienteQueryType.CEDULA);
        clienteQueryDto.setTextoBusqueda("Salazar");
        List<ClienteDto> clienteDtos = administradorClientes.obtenerListaClientesPorCriterio(clienteQueryDto);
        System.out.println("Tamaño: " + clienteDtos.size());
        Assertions.assertEquals(0, clienteDtos.size());
    }


}