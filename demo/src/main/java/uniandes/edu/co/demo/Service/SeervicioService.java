package uniandes.edu.co.demo.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;

import uniandes.edu.co.demo.modelo.Conductor2;
import uniandes.edu.co.demo.modelo.Servicio;
import uniandes.edu.co.demo.modelo.SolicitarServicioDTO;
import uniandes.edu.co.demo.modelo.UsuarioEnServicio;
import uniandes.edu.co.demo.modelo.UsuarioServicio;
import uniandes.edu.co.demo.modelo.Vehiculo2;
import uniandes.edu.co.demo.repository.ConductorRepository;
import uniandes.edu.co.demo.repository.ServicioRepository;
import uniandes.edu.co.demo.repository.UsuarioServicioRepository;

public class SeervicioService {
 
    @Autowired
    private ServicioRepository repositorio;
    @Autowired
    private ConductorService conductorService;
    @Autowired
    private ConductorRepository conductorRepo;
    @Autowired
    private UsuarioServicioRepository usuarioRepo;
    
    public Servicio SolicitarServicio(SolicitarServicioDTO servicio){
        List<Object> condPlaca = conductorService.encontrarConductorDisponible();
        Conductor2 conductor = (Conductor2) condPlaca.get(0);
        String placa = (String) condPlaca.get(1);
        // la tarifa es generica
        Integer tarifa = 5000;
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatte = DateTimeFormatter.ofPattern("HH:mm");
        String horaInicio = horaActual.format(formatte);
        float distancia = ThreadLocalRandom.current().nextInt(5, 51); // un numero aleorio para la distancia 
        DateTimeFormatter f = DateTimeFormatter.ofPattern("AAAA/MM/DD");
        String fecha= horaActual.format(f);
        double comision= tarifa*0.6;

        UsuarioServicio usuario = usuarioRepo.getById(servicio.getUsuarioId());

        UsuarioEnServicio usu = new UsuarioEnServicio(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCelular(),
            usuario.getCedula()
        );
        UsuarioEnServicio cond = new UsuarioEnServicio(
            conductor.getId(),
            conductor.getNombre(),
            conductor.getCelular(),
            conductor.getCedula()
        );

        Vehiculo2 vehiculo = conductorRepo.getVehiculoPorPlaca(placa);

        Servicio nuevoServicio = new Servicio( 
         servicio.getId(),
         fecha,
         usu,
         cond,
         servicio.getCiudad(),
         servicio.getPuntoPartida(), 
         servicio.getPuntoLlegada(), 
         vehiculo, 
         horaInicio, 
         null, 
         distancia, 
         (float)comision, 
         tarifa,
         servicio.getTipoServicio(), 
         servicio.getNivel(), 
         servicio.getOrden(), 
         servicio.getRestaurante(), 
         servicio.getElemento());

        return repositorio.save(nuevoServicio);
    }

  

}
