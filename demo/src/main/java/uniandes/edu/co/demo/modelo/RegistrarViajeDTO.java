package uniandes.edu.co.demo.modelo;

import java.util.List;

public class RegistrarViajeDTO {
    private int id;
    private int usuarioId;
    private int conductorId;
    private String fecha;
    private Ubicacion puntoPartida;
    private List<Ubicacion> puntosLlegada;
    private String ciudad;
    private String tipoServicio;
    private String orden;
    private String restaurante;
    private String elemento;
    private String nivel;
    private String horaInicio;
    private String horaFin;
    private float distanciaKm;
    private float comision;
    private Integer valorTotal;
    private String placaVehiculo;

    public RegistrarViajeDTO(int id, int usuarioId, int conductorId, String fecha, Ubicacion puntoPartida,
            List<Ubicacion> puntosLlegada, String ciudad, String tipoServicio, String orden, String restaurante,
            String elemento, String nivel, String horaInicio, String horaFin, float distanciaKm, float comision,
            Integer valorTotal, String placaVehiculo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.conductorId = conductorId;
        this.fecha = fecha;
        this.puntoPartida = puntoPartida;
        this.puntosLlegada = puntosLlegada;
        this.ciudad = ciudad;
        this.tipoServicio = tipoServicio;
        this.orden = orden;
        this.restaurante = restaurante;
        this.elemento = elemento;
        this.nivel = nivel;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.distanciaKm = distanciaKm;
        this.comision = comision;
        this.valorTotal = valorTotal;
        this.placaVehiculo = placaVehiculo;
    }

    public RegistrarViajeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getConductorId() {
        return conductorId;
    }

    public void setConductorId(int conductorId) {
        this.conductorId = conductorId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Ubicacion getPuntoPartida() {
        return puntoPartida;
    }

    public void setPuntoPartida(Ubicacion puntoPartida) {
        this.puntoPartida = puntoPartida;
    }

    public List<Ubicacion> getPuntosLlegada() {
        return puntosLlegada;
    }

    public void setPuntosLlegada(List<Ubicacion> puntosLlegada) {
        this.puntosLlegada = puntosLlegada;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
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

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public float getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(float distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public float getComision() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision = comision;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }
}