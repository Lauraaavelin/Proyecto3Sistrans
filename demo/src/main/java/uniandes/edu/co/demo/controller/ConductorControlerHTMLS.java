package uniandes.edu.co.demo.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Conductor2;
import uniandes.edu.co.demo.modelo.Disponibilidad;
import uniandes.edu.co.demo.modelo.RegistrarDisponibilidadDTO;
import uniandes.edu.co.demo.modelo.Vehiculo2;
import uniandes.edu.co.demo.repository.ConductorRepository;


@Controller
@RequestMapping("/Conductor")
public class ConductorControlerHTMLS {

    @Autowired
    private ConductorRepository conductorRepository;
    public ConductorControlerHTMLS() {
        
    }

    // ----- Mostrar formulario -----
    @GetMapping("/Registro")
    public String mostrarFormularioRegistro() {
        return "ConductorRegistro";
    }

    // ----- Recibir formulario -----
    @PostMapping("/Registrar")
    public String registrarConductor(
            @RequestParam int id,
            @RequestParam String nombre,
            @RequestParam String celular,
            @RequestParam String cedula,
            @RequestParam String email,
            Model model) {

        
        Conductor2 c = new Conductor2(id, nombre, celular, cedula, email);
        conductorRepository.save(c);
        model.addAttribute("nombre", nombre);
        model.addAttribute("id", id);
        model.addAttribute("conductor", c);
        model.addAttribute("email", email);

        return "redirect:/Conductor/Inicio/" + id;
    }

   @GetMapping("/Inicio/{id}")
    public String inicioConductor(@PathVariable int id, Model model) {

        var conductor = conductorRepository.findById(id);

        if(conductor.isPresent()) {
            model.addAttribute("nombre", conductor.get().getNombre());
            model.addAttribute("id", conductor.get().getId());
            model.addAttribute("vehiculos", conductor.get().getVehiculos());
            model.addAttribute("disponibilidades", conductor.get().getDisponibilidades());
        } else {
            model.addAttribute("nombre", "Desconocido");
            model.addAttribute("id", id);
        }

        return "InicioConductor";
    }


    // =========================
// FORMULARIO DE VEHÍCULO
// =========================

// Mostrar formulario
@GetMapping("/RegistrarVehiculo/{id}")
public String mostrarFormularioVehiculo(@PathVariable int id, Model model) {
    model.addAttribute("id", id);
    return "VehiculoRegistro";
}

// Recibir formulario
@PostMapping("/RegistrarVehiculo")
public String registrarVehiculo(
        @RequestParam int idConductor,
        @RequestParam String placa,
        @RequestParam String tipo,
        @RequestParam String marca,
        @RequestParam String modelo,
        @RequestParam String color,
        @RequestParam String ciudadPlaca,
        @RequestParam int capacidad,
        Model model) {

    var conductorOpt = conductorRepository.findById(idConductor);

    if (conductorOpt.isEmpty()) {
        model.addAttribute("error", "Conductor no encontrado");
        return "InicioConductor";
    }

    // Validar placa existente
    var vehiculoExistente = conductorRepository.getVehiculoPorPlaca(placa);
    if (vehiculoExistente != null) {
        model.addAttribute("error", "Ya existe un vehículo con esa placa");
        model.addAttribute("id", idConductor);
        return "VehiculoRegistro";
    }

    // Crear vehículo 
    var vehiculo = new Vehiculo2(
            placa, tipo, marca, modelo, color, ciudadPlaca, capacidad);

    var conductor = conductorOpt.get();
    conductor.getVehiculos().add(vehiculo);

    conductorRepository.agregarVehiculoAConductor(conductor.getId(), vehiculo);
    

    // Redirigir al inicio del conductor
    return "redirect:/Conductor/Inicio/" + idConductor;
}

@GetMapping("/nuevaDisponibilidad/{id}")
public String mostrarFormularioDisponibilidad(@PathVariable int id, Model model) {
    var conductorOpt = conductorRepository.findById(id);
    if (conductorOpt.isEmpty()) {
        return "redirect:/conductores?error=notfound";
    }

    model.addAttribute("conductor", conductorOpt.get());
    model.addAttribute("disponibilidad", new RegistrarDisponibilidadDTO());
    return "nuevaDisponibilidad";
}

@PostMapping("/guardarDisponibilidad/{id}")
public String guardarDisponibilidad(
        @PathVariable int id,
        @ModelAttribute RegistrarDisponibilidadDTO disponibilidadDTO,
        Model model) {

    var conductorOpt = conductorRepository.findById(id);
    if (conductorOpt.isEmpty()) {
        model.addAttribute("error", "No se encontró el conductor.");
        return "nuevaDisponibilidad";
    }

    Conductor2 conductor = conductorOpt.get();

    // Validar tipo servicio
    String tipoServicio = disponibilidadDTO.getTipoServicio();
    if (tipoServicio == null || !"TDM".contains(tipoServicio.toUpperCase()) || tipoServicio.length() != 1) {
        model.addAttribute("error", "El tipo de servicio debe ser T, D o M");
        return "nuevaDisponibilidad";
    }

    // Validar días
    if (!tieneDiaSeleccionado(disponibilidadDTO)) {
        model.addAttribute("error", "Debe seleccionar al menos un día");
        return "nuevaDisponibilidad";
    }

    // Validar horas
    LocalTime horaInicio, horaFin;
    try {
        var formatter = DateTimeFormatter.ofPattern("HH:mm");
        horaInicio = LocalTime.parse(disponibilidadDTO.getHoraInicio(), formatter);
        horaFin = LocalTime.parse(disponibilidadDTO.getHoraFin(), formatter);
    } catch (Exception ex) {
        model.addAttribute("error", "Formato de hora inválido (use HH:mm)");
        return "nuevaDisponibilidad";
    }

    if (!horaInicio.isBefore(horaFin)) {
        model.addAttribute("error", "La hora de inicio debe ser antes de la hora fin.");
        return "nuevaDisponibilidad";
    }

    // Validar placa pertenece al conductor
    Vehiculo2 vehiculo = conductor.getVehiculos().stream()
        .filter(v -> v.getPlaca().equalsIgnoreCase(disponibilidadDTO.getPlaca()))
        .findFirst()
        .orElse(null);

    if (vehiculo == null) {
        model.addAttribute("error", "La placa no pertenece al conductor.");
        return "nuevaDisponibilidad";
    }

    // Nivel cuando T
    String nivel = tipoServicio.equalsIgnoreCase("T")
            ? determinarNivel(vehiculo)
            : null;

    // Crear disponibilidad
    Disponibilidad nueva = new Disponibilidad(
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
        tipoServicio.toUpperCase()
    );

    // Verificar superposición
    for (Disponibilidad d : conductor.getDisponibilidades()) {
        if (seSuperpone(nueva, d)) {
            model.addAttribute("error", "La disponibilidad se superpone con otra ya registrada.");
            return "nuevaDisponibilidad";
        }
    }

    conductor.getDisponibilidades().add(nueva);
    
    conductorRepository.agregarDisponibilidadAConductor(conductor.getId(), nueva);
    
    return "redirect:/Conductor/Inicio/" + id;
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

    private boolean tieneDiaSeleccionado(RegistrarDisponibilidadDTO dto) {
        return dto.isLunes() || dto.isMartes() || dto.isMiercoles() || dto.isJueves()
                || dto.isViernes() || dto.isSabado() || dto.isDomingo();
    }







    @GetMapping("/{conductorId}/Disponibilidades/Editar/{indice}")
public String mostrarFormularioEditarDisponibilidad(
        @PathVariable int conductorId,
        @PathVariable int indice,
        Model model) {

    var conductorOpt = conductorRepository.findById(conductorId);
    if (conductorOpt.isEmpty()) {
        model.addAttribute("error", "Conductor no encontrado");
        return "Error";
    }

    Conductor2 conductor = conductorOpt.get();

    if (indice < 0 || indice >= conductor.getDisponibilidades().size()) {
        model.addAttribute("error", "Índice de disponibilidad no válido");
        return "Error";
    }

    Disponibilidad disponibilidad = conductor.getDisponibilidades().get(indice);

    // Llenamos el DTO con los valores almacenados
    RegistrarDisponibilidadDTO dto = new RegistrarDisponibilidadDTO();
    dto.setLunes(disponibilidad.isLunes());
    dto.setMartes(disponibilidad.isMartes());
    dto.setMiercoles(disponibilidad.isMiercoles());
    dto.setJueves(disponibilidad.isJueves());
    dto.setViernes(disponibilidad.isViernes());
    dto.setSabado(disponibilidad.isSabado());
    dto.setDomingo(disponibilidad.isDomingo());
    dto.setHoraInicio(disponibilidad.getHoraInicio());
    dto.setHoraFin(disponibilidad.getHoraFin());
    dto.setPlaca(disponibilidad.getPlaca());
    dto.setTipoServicio(disponibilidad.getTipoServicio());

    model.addAttribute("disponibilidad", dto);
    model.addAttribute("conductorId", conductorId);
    model.addAttribute("indice", indice);
    model.addAttribute("vehiculos", conductor.getVehiculos());

    return "EditarDisponibilidad";
}



@PostMapping("/{conductorId}/Disponibilidades/Editar/{indice}")
public String actualizarDisponibilidad(
        @PathVariable int conductorId,
        @PathVariable int indice,
        @ModelAttribute RegistrarDisponibilidadDTO dto,
        Model model) {

    var conductorOpt = conductorRepository.findById(conductorId);
    if (conductorOpt.isEmpty()) {
        model.addAttribute("error", "Conductor no encontrado");
        return "Error";
    }

    Conductor2 conductor = conductorOpt.get();

    if (indice < 0 || indice >= conductor.getDisponibilidades().size()) {
        model.addAttribute("error", "Disponibilidad no encontrada");
        return "Error";
    }

    // Validar tipo de servicio
    String tipoServicio = dto.getTipoServicio();
    if (tipoServicio == null || !"TDM".contains(tipoServicio.toUpperCase()) || tipoServicio.length() != 1) {
        model.addAttribute("error", "El tipo de servicio debe ser T, D o M");
        return "Error";
    }

    if (!tieneDiaSeleccionado(dto)) {
        model.addAttribute("error", "Debe seleccionar al menos un día");
        return "Error";
    }

    LocalTime horaInicio;
    LocalTime horaFin;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        horaInicio = LocalTime.parse(dto.getHoraInicio(), formatter);
        horaFin = LocalTime.parse(dto.getHoraFin(), formatter);
    } catch (Exception e) {
        model.addAttribute("error", "Formato de hora inválido");
        return "Error";
    }

    if (!horaInicio.isBefore(horaFin)) {
        model.addAttribute("error", "La hora de inicio debe ser anterior a la hora de fin");
        return "Error";
    }

    // Validar placa
    Vehiculo2 vehiculo = conductor.getVehiculos().stream()
            .filter(v -> v.getPlaca().equalsIgnoreCase(dto.getPlaca()))
            .findFirst()
            .orElse(null);

    if (vehiculo == null) {
        model.addAttribute("error", "La placa indicada no pertenece al conductor");
        return "Error";
    }

    // Determinar nivel
    String nivel = tipoServicio.equals("T")
            ? determinarNivel(vehiculo)
            : null;

    // Crear nueva disponibilidad
    Disponibilidad actualizada = new Disponibilidad(
            dto.isLunes(),
            dto.isMartes(),
            dto.isMiercoles(),
            dto.isJueves(),
            dto.isViernes(),
            dto.isSabado(),
            dto.isDomingo(),
            dto.getHoraInicio(),
            dto.getHoraFin(),
            dto.getPlaca(),
            nivel,
            tipoServicio);

    // Validar superposición excepto con la que se está editando
    for (int i = 0; i < conductor.getDisponibilidades().size(); i++) {
        if (i == indice) continue;

        if (seSuperpone(actualizada, conductor.getDisponibilidades().get(i))) {
            model.addAttribute("error", "La disponibilidad se superpone con otra existente");
            return "Error";
        }
    }

    // Remplazar la disponibilidad
    conductor.getDisponibilidades().set(indice, actualizada);
    conductorRepository.save(conductor);

    return "redirect:/Conductor/Inicio/" + conductorId;
}



}
