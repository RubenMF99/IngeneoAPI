package com.backend.api;
import com.backend.api.controller.LogisticaController;
import com.backend.api.model.Logistica;
import com.backend.api.repository.LogisticaRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class LogisticsTest {

    @Autowired
    private LogisticaController logisticaController;

    @Autowired
    private LogisticaRepositorio logisticaRepositorio;

    @Test
    public void testPostLogisticaConDescuentoTerrestre() {
        // Arrange
        Logistica logistica = new Logistica("23/04/2024","25/04/2024",15,1000f,0d,1,1,1);
        int idProducto = 1; 
        ResponseEntity<Object> response = logisticaController.postLogistica(logistica, idProducto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Logistica logisticaGuardada = logisticaRepositorio.findById(logistica.getId()).orElse(null);
        assertNotNull(logisticaGuardada);
        assertEquals(logistica.getPrecioenvio() * 0.05, logisticaGuardada.getDescuento());
    }
    @Test
    public void testPostLogisticaConDescuentoMaritimo() {
        // Arrange
        Logistica logistica = new Logistica("23/04/2024","25/04/2024",15,1000f,0d,1,1,1);
        int idProducto = 2; 

        ResponseEntity<Object> response = logisticaController.postLogistica(logistica, idProducto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Logistica logisticaGuardada = logisticaRepositorio.findById(logistica.getId()).orElse(null);
        assertNotNull(logisticaGuardada);
        assertEquals(logistica.getPrecioenvio() * 0.03, logisticaGuardada.getDescuento());
    }

    @Test
    public void testPostLogisticaSinDescuento() {
        Logistica logistica = new Logistica("23/04/2024","25/04/2024",9,1000f,0d,1,1,1);
        int idProducto = 1; 

        ResponseEntity<Object> response = logisticaController.postLogistica(logistica, idProducto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verifica que no se aplicó descuento
        if (response.getBody() instanceof Logistica) {
            Logistica logisticaResponse = (Logistica) response.getBody();
            assertEquals(0d, logisticaResponse.getDescuento());
        } else {
            fail("Se esperaba una respuesta de tipo Logistica, pero el cuerpo es de tipo: " + response.getBody().getClass().getName());
        }
    }

    @Test
    public void testPostLogisticaProductoNoEncontrado() {
        Logistica logistica = new Logistica("23/04/2024","25/04/2024",9,1000f,0d,1,1,1);
        int idProductoNoExistente = 999;  

        ResponseEntity<Object> response = logisticaController.postLogistica(logistica, idProductoNoExistente);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró Tiposproducto con el identificador: " + idProductoNoExistente, response.getBody());
    }
    @Test
    public void testGetListLogisticaByIdClienteError() {
        int idCliente = 1000;
        ResponseEntity<Object> response = logisticaController.getListLogisticaByIdCliente(idCliente);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("No se encontraron datos", response.getBody());
    }
}

