package uniandes.edu.co.demo.modelo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.ToString;
import java.util.List;



@Document(collection="Servicio")
@ToString
public class Servicio {
    @Id
    private int id;
    private String fecha;
    private UsuarioServicio usuarioServicio;
    private Conductor conductor;
    private String ciudad;
    private Ubicacion puntoPartida;
    private List<Ubicacion> puntosLlegada;
    private vehiculo vehiculo;
    private String horaInicio;
    private String horaFin;
    private float distanciaKm;
    private float comision;
    private Integer valorTotal;
    private String TipoServicio;
    private String nivel;
    private String orden;
    private String restaurante;
    private String elemento;

    Servicio(int id, String fecha, UsuarioServicio usuarioServicio, Conductor conductor, String ciudad, Ubicacion puntoPartida,
        List<Ubicacion> puntosLlegada, vehiculo vehiculo, String horaInicio, String horaFin, float distanciaKm, float comision, Integer valorTotal, String TipoServicio,
        String nivel, String orden, String restaurante, String elemento){
            this.id=id;
            this.fecha=fecha;
            this.usuarioServicio=usuarioServicio;
            this.conductor=conductor;
            this.ciudad=ciudad;
            this.puntoPartida=puntoPartida;
            this.puntosLlegada=puntosLlegada;
            this.vehiculo=vehiculo;
            this.horaInicio=horaInicio;
            this.horaFin=horaFin;
            this.distanciaKm=distanciaKm;
            this.comision=comision;
            this.valorTotal=valorTotal;
            this.TipoServicio=TipoServicio;
            this.nivel=nivel;
            this.orden=orden;
            this.restaurante=restaurante;
            this.elemento=elemento;

        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(vehiculo vehiculo) {
        this.vehiculo = vehiculo;
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

    public String getTipoServicio() {
        return TipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        TipoServicio = tipoServicio;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
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

        



}
