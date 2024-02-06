package com.backend.api.controller;

import com.backend.api.model.Almacen;
import com.backend.api.repository.AlmacenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(originPatterns = "*" ,origins = "https://ingeneo-web.onrender.com")
@RestController
@RequestMapping("/api/admin")
public class AlmacenController {
    @Autowired
    private AlmacenRepositorio almacenRepositorio;

    @PostMapping("/almacenaje")
    public ResponseEntity<Object> postAlmacen(@RequestBody Almacen almacen) {
        if (almacen != null) {
            return ResponseEntity.ok(almacenRepositorio.save(almacen));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Datos no validos");
    }

    @GetMapping("/almacenaje")
    public ResponseEntity<Object> getAlmacen() {
        return ResponseEntity.ok(almacenRepositorio.findAll());
    }

    @GetMapping("/almacenaje/{id}")
    public ResponseEntity<Object> getAlmacenById(@PathVariable Integer id) {
        return ResponseEntity.ok(almacenRepositorio.findById(id));
    }

    @DeleteMapping("/almacenaje/{id}")
    public ResponseEntity<Object> deleteAlmacen(@PathVariable Integer id) {
        almacenRepositorio.deleteById(id);
        return ResponseEntity.ok("Almacen eliminado");
    }

}
