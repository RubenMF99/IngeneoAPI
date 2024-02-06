package com.backend.api.controller;

import org.springframework.web.bind.annotation.*;

import com.backend.api.model.Logistica;
import com.backend.api.model.Tiposproducto;
import com.backend.api.repository.LogisticaRepositorio;
import com.backend.api.repository.TiposProductoRepositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(originPatterns = "*" ,origins = "https://ingeneo-web.onrender.com")
@RestController
@RequestMapping("/api")

public class LogisticaController {
    
    private final TiposProductoRepositorio tiposRepositorio;
    private final LogisticaRepositorio logisticaRepositorio;

    public LogisticaController(TiposProductoRepositorio tiposRepositorio, LogisticaRepositorio logisticaRepositorio) {
        this.tiposRepositorio = tiposRepositorio;
        this.logisticaRepositorio = logisticaRepositorio;
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
            if (guiaEnvio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron datos");
            }
            return ResponseEntity.ok(guiaEnvio);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }
}
