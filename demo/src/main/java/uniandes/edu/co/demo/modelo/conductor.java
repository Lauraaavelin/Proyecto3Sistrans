package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


import lombok.ToString;



@Document(collection="Conductor")
@ToString
public class conductor {

    @Id
    private int id;
    private String nombre;
    private String celular;
    private String cedula;
    private String email;
    private List<vehiculo> vehiculos;
    private List<Disponibilidad> disponibilidad;

    public conductor(int id, String nombre, String celular, String cedula, String email){
        this.id=id;
        this.nombre=nombre;
        this.celular=celular;
        this.cedula=cedula;
        this.email=email;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Disponibilidad> getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(List<Disponibilidad> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    



}
