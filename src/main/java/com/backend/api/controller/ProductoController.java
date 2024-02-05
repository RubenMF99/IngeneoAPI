package com.backend.api.controller;

import com.backend.api.model.Tiposproducto;
import com.backend.api.repository.TiposProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ProductoController {
    @Autowired
    TiposProductoRepositorio tiposRepositorio;
    @PostMapping("/producto")
    public Tiposproducto agregarProducto(@RequestBody Tiposproducto producto) {
        return  tiposRepositorio.save(producto);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<Tiposproducto> actualizarProducto(@PathVariable Integer id) {
        Tiposproducto producto = tiposRepositorio.findById(id).orElse(null);
        if (producto != null) {
            return ResponseEntity.ok(tiposRepositorio.save(producto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/producto")
    public ResponseEntity<Object> getListProducts(@RequestParam Integer idCliente) {
        try{
            List<Tiposproducto> tiposProductos = tiposRepositorio.findByClienteId(idCliente);
            return ResponseEntity.ok(tiposProductos);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Object> eliminarProducto(@PathVariable Integer id) {
        try {
            tiposRepositorio.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<Object> getProducto() {
        try {
            List<Tiposproducto> productos = tiposRepositorio.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }
}