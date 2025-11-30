package uniandes.edu.co.demo.controller;

import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.UsuarioServicio;
import uniandes.edu.co.demo.repository.UsuarioServicioRepository;

@RestController
@RequestMapping("/usuarios-servicio")
public class UsuarioServicioController {

    private final UsuarioServicioRepository usuarioServicioRepo;

    public UsuarioServicioController(UsuarioServicioRepository usuarioServicioRepo) {
        this.usuarioServicioRepo = usuarioServicioRepo;
    }

    
    @PostMapping("/crear")
    public String crearUsuarioServicio(
            @RequestParam int id,
            @RequestParam String nombre,
            @RequestParam String celular,
            @RequestParam String cedula,
            @RequestParam String email,
            @RequestBody Object tarjeta) {

        usuarioServicioRepo.insertarUsuarioServicio(id, nombre, celular, cedula, email, tarjeta);
        return "Usuario de servicio creado correctamente.";
    }

    @GetMapping("/{id}")
    public UsuarioServicio obtenerUsuarioPorId(@PathVariable int id) {
        return usuarioServicioRepo.getById(id);
    }

    
    @GetMapping
    public Iterable<UsuarioServicio> obtenerTodos() {
        return usuarioServicioRepo.findAll();
    }
}
