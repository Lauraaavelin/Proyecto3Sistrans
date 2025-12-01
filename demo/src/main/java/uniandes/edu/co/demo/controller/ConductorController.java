package uniandes.edu.co.demo.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.demo.modelo.Conductor2;
import uniandes.edu.co.demo.modelo.Disponibilidad;
import uniandes.edu.co.demo.modelo.RegistrarDisponibilidadDTO;
import uniandes.edu.co.demo.modelo.RegistrarVehiculoDTO;
import uniandes.edu.co.demo.modelo.Vehiculo2;
import uniandes.edu.co.demo.repository.ConductorRepository;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/conductores")
public class ConductorController {

    private final ConductorRepository conductorRepository;

    public ConductorController(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @PostMapping
    public ResponseEntity<?> registrarConductor(@RequestBody Conductor2 conductor) {
        if (conductorRepository.existsById(conductor.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un conductor con el identificador proporcionado");
        }

        Conductor2 nuevoConductor = conductorRepository.save(conductor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoConductor);
    }

    @PostMapping("/{conductorId}/vehiculos")
    public ResponseEntity<?> registrarVehiculo(@PathVariable int conductorId,
            @RequestBody RegistrarVehiculoDTO vehiculoDTO) {
        var conductorOpt = conductorRepository.findById(conductorId);
        if (conductorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un conductor con el identificador proporcionado");
        }

        Vehiculo2 vehiculoExistente = conductorRepository.getVehiculoPorPlaca(vehiculoDTO.getPlaca());
        if (vehiculoExistente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un vehículo registrado con la placa proporcionada");
        }

        Vehiculo2 vehiculo = new Vehiculo2(
                vehiculoDTO.getPlaca(),
                vehiculoDTO.getTipo(),
                vehiculoDTO.getMarca(),
                vehiculoDTO.getModelo(),
                vehiculoDTO.getColor(),
                vehiculoDTO.getCiudadPlaca(),
                vehiculoDTO.getCapacidad());

        Conductor2 conductor = conductorOpt.get();
        conductor.getVehiculos().add(vehiculo);
        conductorRepository.save(conductor);

        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculo);
    }

    @PostMapping("/{conductorId}/disponibilidades")
    public ResponseEntity<?> registrarDisponibilidad(@PathVariable int conductorId,
            @RequestBody RegistrarDisponibilidadDTO disponibilidadDTO) {

        var conductorOpt = conductorRepository.findById(conductorId);
        if (conductorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un conductor con el identificador proporcionado");
        }

        Conductor2 conductor = conductorOpt.get();

        String tipoServicio = disponibilidadDTO.getTipoServicio();
        if (tipoServicio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El tipo de servicio debe ser T, D o M");
        }

        tipoServicio = tipoServicio.toUpperCase();
        if (!"TDM".contains(tipoServicio) || tipoServicio.length() != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El tipo de servicio debe ser T, D o M");
        }

        if (!tieneDiaSeleccionado(disponibilidadDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debe seleccionar al menos un día para la disponibilidad");
        }

        LocalTime horaInicio;
        LocalTime horaFin;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            horaInicio = LocalTime.parse(disponibilidadDTO.getHoraInicio(), formatter);
            horaFin = LocalTime.parse(disponibilidadDTO.getHoraFin(), formatter);
        } catch (DateTimeParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El formato de hora debe ser HH:mm");
        }

        if (!horaInicio.isBefore(horaFin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La hora de inicio debe ser anterior a la hora de fin");
        }

        Vehiculo2 vehiculo = conductor.getVehiculos().stream()
                .filter(v -> v.getPlaca() != null && disponibilidadDTO.getPlaca() != null
                        && v.getPlaca().equalsIgnoreCase(disponibilidadDTO.getPlaca()))
                .findFirst()
                .orElse(null);

        if (vehiculo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La placa indicada no pertenece al conductor");
        }

        String nivel = tipoServicio.equals("T")
                ? determinarNivel(vehiculo)
                : null;

        Disponibilidad nuevaDisponibilidad = new Disponibilidad(
                disponibilidadDTO.isLunes(),
                disponibilidadDTO.isMartes(),
                disponibilidadDTO.isMiercoles(),
                disponibilidadDTO.isJueves(),
                disponibilidadDTO.isViernes(),
                disponibilidadDTO.isSabado(),
                disponibilidadDTO.isDomingo(),
                disponibilidadDTO.getHoraInicio(),
                disponibilidadDTO.getHoraFin(),
                disponibilidadDTO.getPlaca(),
                nivel,
                tipoServicio);

        for (Disponibilidad existente : conductor.getDisponibilidades()) {
            if (seSuperpone(nuevaDisponibilidad, existente)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("La disponibilidad se superpone con otra ya registrada para el conductor");
            }
        }

        conductor.getDisponibilidades().add(nuevaDisponibilidad);
        conductorRepository.save(conductor);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaDisponibilidad);
    }

    private boolean tieneDiaSeleccionado(RegistrarDisponibilidadDTO dto) {
        return dto.isLunes() || dto.isMartes() || dto.isMiercoles() || dto.isJueves()
                || dto.isViernes() || dto.isSabado() || dto.isDomingo();
    }

    private boolean seSuperpone(Disponibilidad nueva, Disponibilidad existente) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime inicioNueva = LocalTime.parse(nueva.getHoraInicio(), formatter);
        LocalTime finNueva = LocalTime.parse(nueva.getHoraFin(), formatter);
        LocalTime inicioExistente = LocalTime.parse(existente.getHoraInicio(), formatter);
        LocalTime finExistente = LocalTime.parse(existente.getHoraFin(), formatter);

        boolean diaCoincide = (nueva.isLunes() && existente.isLunes())
                || (nueva.isMartes() && existente.isMartes())
                || (nueva.isMiercoles() && existente.isMiercoles())
                || (nueva.isJueves() && existente.isJueves())
                || (nueva.isViernes() && existente.isViernes())
                || (nueva.isSabado() && existente.isSabado())
                || (nueva.isDomingo() && existente.isDomingo());

        boolean horasSeCruzan = inicioNueva.isBefore(finExistente) && inicioExistente.isBefore(finNueva);

        return diaCoincide && horasSeCruzan;
    }

    private String determinarNivel(Vehiculo2 vehiculo) {
        Integer capacidad = vehiculo.getCapacidad();
        if (capacidad != null && capacidad >= 7) {
            return "Large";
        } else if (capacidad != null && capacidad >= 5) {
            return "Confort";
        }
        return "Estandar";
    }

    @PutMapping("/{conductorId}/disponibilidades/{indice}")
    public ResponseEntity<?> actualizarDisponibilidad(@PathVariable int conductorId, @PathVariable int indice,
            @RequestBody RegistrarDisponibilidadDTO disponibilidadDTO) {

        var conductorOpt = conductorRepository.findById(conductorId);
        if (conductorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un conductor con el identificador proporcionado");
        }

        Conductor2 conductor = conductorOpt.get();
        if (indice < 0 || indice >= conductor.getDisponibilidades().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la disponibilidad solicitada para el conductor");
        }

        String tipoServicio = disponibilidadDTO.getTipoServicio();
        if (tipoServicio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El tipo de servicio debe ser T, D o M");
        }

        tipoServicio = tipoServicio.toUpperCase();
        if (!"TDM".contains(tipoServicio) || tipoServicio.length() != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El tipo de servicio debe ser T, D o M");
        }

        if (!tieneDiaSeleccionado(disponibilidadDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debe seleccionar al menos un día para la disponibilidad");
        }

        LocalTime horaInicio;
        LocalTime horaFin;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            horaInicio = LocalTime.parse(disponibilidadDTO.getHoraInicio(), formatter);
            horaFin = LocalTime.parse(disponibilidadDTO.getHoraFin(), formatter);
        } catch (DateTimeParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El formato de hora debe ser HH:mm");
        }

        if (!horaInicio.isBefore(horaFin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La hora de inicio debe ser anterior a la hora de fin");
        }

        Vehiculo2 vehiculo = conductor.getVehiculos().stream()
                .filter(v -> v.getPlaca() != null && disponibilidadDTO.getPlaca() != null
                        && v.getPlaca().equalsIgnoreCase(disponibilidadDTO.getPlaca()))
                .findFirst()
                .orElse(null);

        if (vehiculo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La placa indicada no pertenece al conductor");
        }

        String nivel = tipoServicio.equals("T")
                ? determinarNivel(vehiculo)
                : null;

        Disponibilidad disponibilidadActualizada = new Disponibilidad(
                disponibilidadDTO.isLunes(),
                disponibilidadDTO.isMartes(),
                disponibilidadDTO.isMiercoles(),
                disponibilidadDTO.isJueves(),
                disponibilidadDTO.isViernes(),
                disponibilidadDTO.isSabado(),
                disponibilidadDTO.isDomingo(),
                disponibilidadDTO.getHoraInicio(),
                disponibilidadDTO.getHoraFin(),
                disponibilidadDTO.getPlaca(),
                nivel,
                tipoServicio);

        for (int i = 0; i < conductor.getDisponibilidades().size(); i++) {
            if (i == indice) {
                continue;
            }
            Disponibilidad existente = conductor.getDisponibilidades().get(i);
            if (seSuperpone(disponibilidadActualizada, existente)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("La disponibilidad se superpone con otra ya registrada para el conductor");
            }
        }

        conductor.getDisponibilidades().set(indice, disponibilidadActualizada);
        conductorRepository.save(conductor);

        return ResponseEntity.ok(disponibilidadActualizada);
    }
}




