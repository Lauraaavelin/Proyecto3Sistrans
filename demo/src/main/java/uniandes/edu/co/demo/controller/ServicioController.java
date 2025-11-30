package uniandes.edu.co.demo.controller;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Conductor2;
import uniandes.edu.co.demo.modelo.RegistrarViajeDTO;
import uniandes.edu.co.demo.modelo.Servicio;
import uniandes.edu.co.demo.modelo.UsuarioEnServicio;
import uniandes.edu.co.demo.modelo.UsuarioServicio;
import uniandes.edu.co.demo.modelo.Vehiculo2;
import uniandes.edu.co.demo.repository.Consultas;
import uniandes.edu.co.demo.repository.ConductorRepository;
import uniandes.edu.co.demo.repository.UsuarioServicioRepository;
import uniandes.edu.co.demo.repository.ServicioRepository;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final Consultas servicioRepoCustom;
    private ConductorRepository conductorRepository;
    private UsuarioServicioRepository usuarioServicioRepository;
    private ServicioRepository servicioRepository;

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

    @PostMapping("/registrar-viaje")
    public ResponseEntity<?> registrarViaje(@RequestBody RegistrarViajeDTO viajeDTO) {

        Optional<Conductor2> conductorOpt = conductorRepository.findById(viajeDTO.getConductorId());
        if (conductorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un conductor con el identificador proporcionado");
        }

        Optional<UsuarioServicio> usuarioOpt = usuarioServicioRepository.findById(viajeDTO.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un usuario de servicios con el identificador proporcionado");
        }

        Vehiculo2 vehiculo = conductorRepository.getVehiculoPorPlaca(viajeDTO.getPlacaVehiculo());
        boolean vehiculoPerteneceAlConductor = conductorOpt.get().getVehiculos().stream()
                .anyMatch(v -> v.getPlaca() != null && v.getPlaca().equalsIgnoreCase(viajeDTO.getPlacaVehiculo()));
        if (vehiculo == null || !vehiculoPerteneceAlConductor) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se encontró un vehículo con la placa indicada para el conductor");
        }

        UsuarioServicio usuarioServicio = usuarioOpt.get();
        Conductor2 conductor = conductorOpt.get();

        UsuarioEnServicio pasajeroEnServicio = new UsuarioEnServicio(
                usuarioServicio.getId(),
                usuarioServicio.getNombre(),
                usuarioServicio.getCelular(),
                usuarioServicio.getCedula());

        UsuarioEnServicio conductorEnServicio = new UsuarioEnServicio(
                conductor.getId(),
                conductor.getNombre(),
                conductor.getCelular(),
                conductor.getCedula());

        Servicio servicio = new Servicio(
                viajeDTO.getId(),
                viajeDTO.getFecha(),
                pasajeroEnServicio,
                conductorEnServicio,
                viajeDTO.getCiudad(),
                viajeDTO.getPuntoPartida(),
                viajeDTO.getPuntosLlegada(),
                vehiculo,
                viajeDTO.getHoraInicio(),
                viajeDTO.getHoraFin(),
                viajeDTO.getDistanciaKm(),
                viajeDTO.getComision(),
                viajeDTO.getValorTotal(),
                viajeDTO.getTipoServicio(),
                viajeDTO.getNivel(),
                viajeDTO.getOrden(),
                viajeDTO.getRestaurante(),
                viajeDTO.getElemento());

        Servicio servicioRegistrado = servicioRepository.save(servicio);
        conductorRepository.YaNoEstaEnServicio(conductor.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(servicioRegistrado);
    }
}
