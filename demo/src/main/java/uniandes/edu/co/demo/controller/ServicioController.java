package uniandes.edu.co.demo.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.repository.Consultas;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final Consultas servicioRepoCustom;

    public ServicioController(Consultas servicioRepoCustom) {
        this.servicioRepoCustom = servicioRepoCustom;
    }

    // --------------------------------------------------------------
    // RFC1 - Historial de servicios pedidos por un usuario
    @GetMapping("/historico/{idUsuario}")
    public List<Document> obtenerHistoricoServicios(@PathVariable int idUsuario) {
        return servicioRepoCustom.obtenerHistoricoServiciosUsuario(idUsuario);
    }

    // --------------------------------------------------------------
    // RFC2 - Top 20 conductores con más servicios prestados
    // --------------------------------------------------------------
    @GetMapping("/top20-conductores")
    public List<Document> obtenerTop20Conductores() {
        return servicioRepoCustom.obtenerTop20Conductores();
    }

    // --------------------------------------------------------------
    // RFC3 - Uso de servicios por ciudad y rango de fechas
    // --------------------------------------------------------------
    @GetMapping("/uso")
    public List<Document> obtenerUsoServiciosCiudad(
            @RequestParam String ciudad,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        return servicioRepoCustom.obtenerUsoServiciosCiudad(ciudad, fechaInicio, fechaFin);
    }
}
