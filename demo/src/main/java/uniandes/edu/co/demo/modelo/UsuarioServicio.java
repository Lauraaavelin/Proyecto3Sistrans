package uniandes.edu.co.demo.modelo;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection="UsuarioServicio")
@ToString
public class UsuarioServicio {
    @Id
    private int id;
    private String nombre;
    private String celular;
    private String cedula;
    private String email;
    private Tarjeta tarjeta;
    public UsuarioServicio(int id, String nombre, String celular, String cedula, String email, Tarjeta tarjeta) {
        this.id = id;
        this.nombre = nombre;
        this.celular = celular;
        this.cedula = cedula;
        this.email = email;
        this.tarjeta = tarjeta;
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
    public Tarjeta getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    
}
