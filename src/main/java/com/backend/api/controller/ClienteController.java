package com.backend.api.controller;

import com.backend.api.config.JwtConfig;
import com.backend.api.model.Cliente;
import com.backend.api.model.Logistica;
import com.backend.api.model.Tiposproducto;
import com.backend.api.repository.ClienteRepositorio;
import com.backend.api.repository.LogisticaRepositorio;
import com.backend.api.repository.TiposProductoRepositorio;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private Key SECRET_KEY;

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    private TiposProductoRepositorio tiposRepositorio;
    private LogisticaRepositorio logisticaRepositorio;

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

    @GetMapping("/admin/cliente")
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
