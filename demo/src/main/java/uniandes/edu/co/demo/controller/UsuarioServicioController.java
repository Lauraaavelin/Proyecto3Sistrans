package uniandes.edu.co.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.UsuarioServicio;
import uniandes.edu.co.demo.repository.UsuarioServicioRepository;
import uniandes.edu.co.demo.Service.SeervicioService;
import uniandes.edu.co.demo.modelo.Servicio;
import uniandes.edu.co.demo.modelo.SolicitarServicioDTO;
import uniandes.edu.co.demo.modelo.Tarjeta;

@RestController
@RequestMapping("/usuarios-servicio")
public class UsuarioServicioController {

    private final UsuarioServicioRepository usuarioServicioRepo;
    private final SeervicioService seervicioService;

    public UsuarioServicioController(UsuarioServicioRepository usuarioServicioRepo, SeervicioService seervicioService) {
        this.usuarioServicioRepo = usuarioServicioRepo;
        this.seervicioService=seervicioService;
    }

    
    @PostMapping("/crear")
    public String crearUsuarioServicio(
            @RequestParam int id,
            @RequestParam String nombre,
            @RequestParam String celular,
            @RequestParam String cedula,
            @RequestParam String email,
            @RequestBody Tarjeta tarjeta) {

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

    @PostMapping("/solicitar-servicio")
    public ResponseEntity<?> solicitarServicio(@RequestBody SolicitarServicioDTO dto) {
        try {
            Servicio nuevoServicio = seervicioService.SolicitarServicio(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoServicio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible solicitar el servicio: " + e.getMessage());
        }
    }
}
