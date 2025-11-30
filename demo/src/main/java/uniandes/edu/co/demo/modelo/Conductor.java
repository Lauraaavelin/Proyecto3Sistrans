package uniandes.edu.co.demo.modelo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

import lombok.ToString;

@Document(collection="Conductor")
@ToString
public class Conductor {
    
    private int id;
    private String nombre;
    private String celular;
    private String cedula;
    private String email;
    private List<Vehiculo> vehiculos= new ArrayList<>();
    private List<Disponibilidad> disponibilidades= new ArrayList<>();
    
    public Conductor(int id, String nombre, String celular, String cedula, String email) {
        this.id = id;
        this.nombre = nombre;
        this.celular = celular;
        this.cedula = cedula;
        this.email = email;
    }
   
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}