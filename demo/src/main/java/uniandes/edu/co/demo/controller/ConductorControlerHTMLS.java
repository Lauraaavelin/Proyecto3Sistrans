package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        
        conductorRepository.registrarConductor(id, nombre, celular, cedula, email);

        model.addAttribute("nombre", nombre);
        model.addAttribute("id", id);

        return "redirect:/Conductor/Inicio/" + id;
    }

    @GetMapping("/Inicio/{id}")
    public String inicioConductor(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        return "InicioConductor";
    }

}
