package com.backend.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.backend.api.controller.ClienteController;
import com.backend.api.model.Cliente;
import com.backend.api.repository.ClienteRepositorio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ClientTest {
    @Autowired
    private ClienteController clienteController;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Test
    public void testAgregarClienteConClienteValido() {
        Cliente cliente = new Cliente("Pedro", "Pedropass", "12345", "pedro@email.com");
        ResponseEntity<Object> response = clienteController.agregarCliente(cliente);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Cliente clienteGuardado = clienteRepositorio.findById(cliente.getId()).orElse(null);
        assertNotNull(clienteGuardado);
        assertEquals("Pedro", clienteGuardado.getNombre());

    }

    @Test
    public void testAgregarClienteConClienteNulo() {
        ResponseEntity<Object> response = clienteController.agregarCliente(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Datos no validos", response.getBody());
    }
    @Test
    public void testLoginConCredencialesValidas() {
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("email", "rumarfo6@gmail.com");
        loginRequest.put("password", "12345");
        ResponseEntity<Object> response = clienteController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verifica si el token está presente en la respuesta
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody.get("token"));
    }

    @Test
    public void testLoginConCredencialesInvalidas() {
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("email", "rumarfo19");
        loginRequest.put("password", "1der2yy1");

        ResponseEntity<Object> response = clienteController.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales inválidas", response.getBody());
    }
}


