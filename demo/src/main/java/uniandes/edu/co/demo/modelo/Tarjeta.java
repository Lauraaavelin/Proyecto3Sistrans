package uniandes.edu.co.demo.modelo;

public class Tarjeta {

    private String numeroTarjeta;
    private String nombre;
    private String fechaExpiracion;
    private String codigoSeguridad;

    public Tarjeta(String numeroTarjeta, String nombre, String fechaExpiracion, String codigoSeguridad) {
        this.numeroTarjeta = numeroTarjeta;
        this.nombre = nombre;
        this.fechaExpiracion = fechaExpiracion;
        this.codigoSeguridad = codigoSeguridad;
    }
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }
    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }
    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
    

}