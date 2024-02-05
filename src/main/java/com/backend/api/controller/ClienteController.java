package com.backend.api.controller;

import com.backend.api.model.Cliente;
import com.backend.api.model.Logistica;
import com.backend.api.model.Tiposproducto;
import com.backend.api.repository.ClienteRepositorio;
import com.backend.api.repository.LogisticaRepositorio;
import com.backend.api.repository.TiposProductoRepositorio;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ClienteController {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final ClienteRepositorio clienteRepositorio;
    @Autowired
    private TiposProductoRepositorio tiposRepositorio;
    @Autowired
    private LogisticaRepositorio logisticaRepositorio;

    // Constructor que inyecta el repositorio
    public ClienteController(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> agregarCliente(@RequestBody Cliente cliente) {
        if(cliente != null){
            return ResponseEntity.ok(clienteRepositorio.save(cliente));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Datos no validos" );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, Object> loginRequest) {
        String email = (String) loginRequest.get("email");
        String password = (String) loginRequest.get("password");
        Cliente cliente = clienteRepositorio.findByEmailAndPassword(email, password);

        if (cliente != null) {
            try {
                String token = Jwts.builder()
                        .setSubject(cliente.getEmail())
                        .claim("userId", cliente.getId())
                        .signWith(SECRET_KEY)
                        .compact();

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return ResponseEntity.ok(response);
            } catch (JwtException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/logistica/{idProduct}")
    public ResponseEntity<Object> postLogistica(@RequestBody Logistica logistica, @PathVariable Integer idProduct) {
        Optional<Tiposproducto> productoOptional = tiposRepositorio.findById(idProduct);
        if (productoOptional.isPresent()) {
            Tiposproducto tiposProducto = productoOptional.get();
            if (logistica.getCantidadproducto() > 10 && tiposProducto.getTransporte().equals("terrestre")) {
                logistica.setDescuento((logistica.getPrecioenvio() * 0.05));
                return ResponseEntity.ok(logisticaRepositorio.save(logistica));
            } else if(logistica.getCantidadproducto() > 10 && tiposProducto.getTransporte().equals("maritimo")){
                logistica.setDescuento((logistica.getPrecioenvio() * 0.03));
                return ResponseEntity.ok(logisticaRepositorio.save(logistica));
            }else{
                return ResponseEntity.ok(logistica);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró Tiposproducto con el identificador: " + idProduct);
        }
    }

    @GetMapping("/logistica")
    public ResponseEntity<Object> getListLogisticaByIdCliente(@RequestParam Integer idCliente) {
        try {
            List<Logistica> guiaEnvio = logisticaRepositorio.findByClienteId(idCliente);
            return ResponseEntity.ok(guiaEnvio);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }

    @GetMapping("/cliente")
    public ResponseEntity<Object> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(7);

            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            String userEmail = claims.getSubject();
            Long userId = claims.get("userId", Long.class);

            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("_id", userId);
            userProfile.put("userEmail", userEmail);

            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
        }
    }
}
