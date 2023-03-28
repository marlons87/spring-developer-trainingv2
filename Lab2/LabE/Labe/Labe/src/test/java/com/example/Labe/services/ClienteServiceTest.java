package com.example.Labe.services;

import com.example.Labe.springData.dto.ClienteDto;
import com.example.Labe.springData.dto.ProductosDto;
import com.example.Labe.springData.model.Cliente;
import com.example.Labe.springData.services.ClienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void insertarCliente() {
        List<Cliente> clienteList = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("listar antes de insertar: " + clienteList);
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellido("Salazar");
        clienteDto.setNombre("Alberto");
        clienteDto.setCedula("1890000000");
        clienteDto.setTelefono("0999714563");

        clienteService.insertarCliente(clienteDto);

        clienteList = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        assertFalse(clienteList.isEmpty());

        //System.out.println("listar cuanto Clientes hay: " + clienteList.size());
        //assertEquals("1890000000", clienteList.get(4).getCedula());

    }

    @Test
    void testInsertarCliente() {
    }

    @Test
    void obtenerCliente() {
        ClienteDto cliente = clienteService.obtenerCliente(1);
        System.out.println("El cliente es: " + cliente.getNombre());
        assertEquals(1, cliente.getId());
    }

    @Test
    void actualizarCliente() {
        //INSERT INTO CLIENTE (APELLIDOS, CEDULA, NOMBRE, TELEFONO) VALUES ('PEREZ', '1', 'ROBERTO', '093939393');

        ClienteDto clienteDtoBase = clienteService.obtenerCliente(1);
        System.out.println(clienteDtoBase);

        clienteDtoBase.setNombre(clienteDtoBase.getNombre() + "TEST");
        clienteDtoBase.setCedula(clienteDtoBase.getCedula() + "TEST");
        clienteDtoBase.setTelefono(clienteDtoBase.getTelefono() + "TEST");
        clienteDtoBase.setApellido(clienteDtoBase.getApellido() + "TEST");
        clienteService.actualizarCliente(clienteDtoBase);

        ClienteDto clienteDtoBaseUpdated = clienteService.obtenerCliente(1);

        System.out.println(clienteDtoBaseUpdated);
        assertEquals("ROBERTOTEST", clienteDtoBaseUpdated.getNombre());
        assertEquals("PEREZTEST", clienteDtoBaseUpdated.getApellido());
    }

    @Test
    void eliminarCliente(){
        ClienteDto clienteDto = clienteService.obtenerCliente(1);
        assertEquals(1, clienteDto.getId());
        clienteService.eliminarCliente(1);

        try {
            clienteService.obtenerCliente(1);
                    fail("No debe llegar acá");
        }catch (RuntimeException e){
            System.out.println("CLIENTE NO EXISTE: " + e.getMessage());
        }
    }

    @Test
    void obtenerClientesPorCodigoISOPaisYCuentasActivas() {
        List<ClienteDto> clientesDto = clienteService.obtenerClientesPorCodigoISOPaisYCuentasActivas("CR");
        clientesDto.forEach(clienteDto -> {System.out.println("Cuentas Activas" + clienteDto);});
        assertEquals(1, clientesDto.size());
    }

    @Test
    void buscarClientesPorApellido() {
        List<Cliente> cliente =  clienteService.buscarClientesPorApellido("PEREZ");
        assertFalse(cliente.isEmpty());
        assertEquals("PEREZ", cliente.get(0).getApellido());
    }

    /*@Test
    /*void buscarClienteExtranjerosPorTarjeta_Inactiva() {
        List<Tuple> cliente =  clienteService.buscarClienteExtranjerosPorTarjeta_Inactiva();
        assertFalse(cliente.isEmpty());
        assertEquals("PEREZ", cliente.get(0).getApellidos());
    }*/

    @Test
    void buscarClientesDinamicamentePorCriterio() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellido("SANCHEZ");
        clienteDto.setNombre("RAUL");
        List<ClienteDto> resultadoCriteriosConDatosDTO = clienteService.buscarClientesDinamicamentePorCriterio(clienteDto);
        resultadoCriteriosConDatosDTO.forEach(clienteDtoResultado -> {System.out.println("ClienteDto es:"+ clienteDtoResultado);});
        assertEquals(1,resultadoCriteriosConDatosDTO.size());
    }

    @Test
    void obtenerTodosLosProductosDeUnClientePorId() {
        ProductosDto productosDto = clienteService.obtenerTodosLosProductosDeUnClientePorId(1);
        System.out.println("Cantidad de Cuentas: " + productosDto.getCuentaDtos().size());
        assertEquals(2, productosDto.getCuentaDtos().size());
    }

    @Test
    void insertarClienteConValidaciones(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellido("Salazar");
        clienteDto.setNombre("Al");
        clienteDto.setCedula("hola");
        assertEquals(1,1);

    }

}