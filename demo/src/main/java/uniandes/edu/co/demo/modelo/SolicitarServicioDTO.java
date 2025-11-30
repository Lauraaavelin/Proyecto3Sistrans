package uniandes.edu.co.demo.modelo;

import java.util.List;

public class SolicitarServicioDTO {

    private int id;
    private int usuarioId;
    private String fecha;
    private Ubicacion puntoPartida;
    private List<Ubicacion> puntoLlegada;
    private String ciudad;
    private String TipoServicio;
    private String orden;
    private String restaurante;
    private String elemento;
    private String nivel;
    private String horaInicio;



    public SolicitarServicioDTO(int id,int usuarioId, String fecha, Ubicacion puntoPartida, List<Ubicacion> puntoLlegada,
            String ciudad, String tipoServicio, String orden, String restaurante, String elemento, String nivel,
            String horaInicio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.puntoPartida = puntoPartida;
        this.puntoLlegada = puntoLlegada;
        this.ciudad = ciudad;
        TipoServicio = tipoServicio;
        this.orden = orden;
        this.restaurante = restaurante;
        this.elemento = elemento;
        this.nivel = nivel;
        this.horaInicio = horaInicio;
    }



    public int getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Ubicacion getPuntoPartida() {
        return puntoPartida;
    }
    public void setPuntoPartida(Ubicacion puntoPartida) {
        this.puntoPartida = puntoPartida;
    }
    public List<Ubicacion> getPuntoLlegada() {
        return puntoLlegada;
    }
    public void setPuntoLlegada(List<Ubicacion> puntoLlegada) {
        this.puntoLlegada = puntoLlegada;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getNivel() {
        return nivel;
    }
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getTipoServicio() {
        return TipoServicio;
    }
    public void setTipoServicio(String tipoServicio) {
        TipoServicio = tipoServicio;
    }
    public String getOrden() {
        return orden;
    }
    public void setOrden(String orden) {
        this.orden = orden;
    }
    public String getRestaurante() {
        return restaurante;
    }
    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }
    public String getElemento() {
        return elemento;
    }
    public void setElemento(String elemento) {
        this.elemento = elemento;
    }



    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }
 

    

}
