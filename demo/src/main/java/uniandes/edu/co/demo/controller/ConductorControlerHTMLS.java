package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Conductor2;
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
        } else {
            model.addAttribute("nombre", "Desconocido");
            model.addAttribute("id", id);
        }

        return "InicioConductor";
    }


}
