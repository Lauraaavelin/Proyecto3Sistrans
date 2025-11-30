package uniandes.edu.co.demo.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import uniandes.edu.co.demo.modelo.Conductor2;
import uniandes.edu.co.demo.modelo.Disponibilidad;
import uniandes.edu.co.demo.repository.ConductorRepository;

public class ConductorService {

    @Autowired
    private ConductorRepository repositorio;

    public List<Object> encontrarConductorDisponible() {
        
        // 1. Obtener el día de la semana
        java.time.DayOfWeek dia = LocalDate.now().getDayOfWeek();


        // 2. Buscar un conductor disponible para ese día, hora y nivel
        Conductor2 conductorElegido = null;
        String placa = null;
        List<Conductor2> conductores = repositorio.findAll();
        for (Conductor2 conductor : conductores) {
            boolean disponible = false; // Suponiendo que la hora actual siempre está dentro del rango para simplificar
            List<Disponibilidad> disponibilidades = conductor.getDisponibilidades();
            for(Disponibilidad disp : disponibilidades){
                if(dia==java.time.DayOfWeek.SATURDAY){
                
                    disponible = disponible | disp.isSabado();
                    
                } else if(dia==java.time.DayOfWeek.SUNDAY){
                    disponible = disponible | disp.isDomingo();
                } else if(dia==java.time.DayOfWeek.MONDAY) {
                    disponible = disponible | disp.isLunes();
                } else if(dia==java.time.DayOfWeek.TUESDAY) {
                    disponible = disponible | disp.isMartes();
                } else if(dia==java.time.DayOfWeek.WEDNESDAY) {
                    disponible = disponible | disp.isMiercoles();
                } else if(dia==java.time.DayOfWeek.THURSDAY) {
                    disponible = disponible | disp.isJueves();
                } else if(dia==java.time.DayOfWeek.FRIDAY) {
                    disponible = disponible | disp.isViernes();
                }
                if(disponible & !conductor.isEnServicio()){    
                    conductorElegido = conductor;
                    placa = disp.getPlaca();
                    repositorio.EstaEnServicio(conductor.getId());
                }
                break;//solo se necesita una disponibiillidad una placa
            }     
            
            break; // solo se necesita un conductor
           
        }
        List<Object> lista = new ArrayList<>();
        lista.add(conductorElegido);
        lista.add(placa);
        
        return lista;
    }

   

}
