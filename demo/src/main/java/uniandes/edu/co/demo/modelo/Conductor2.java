package uniandes.edu.co.demo.modelo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

import lombok.ToString;

@Document(collection="Conductor")
@ToString
public class Conductor2 {
    
    private int id;
    private String nombre;
    private String celular;
    private String cedula;
    private String email;
    private List<Vehiculo2> vehiculos= new ArrayList<>();
    private List<Disponibilidad> disponibilidades= new ArrayList<>();
    private boolean enServicio = false;
    
    public Conductor2(int id, String nombre, String celular, String cedula, String email) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehiculo2> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo2> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }
    
    public boolean isEnServicio() {
        return enServicio;
    }

    public void setEnServicio(boolean enServicio) {
        this.enServicio = enServicio;
    }
}