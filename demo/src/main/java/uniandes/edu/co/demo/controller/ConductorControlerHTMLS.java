package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Conductor2;
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


}
