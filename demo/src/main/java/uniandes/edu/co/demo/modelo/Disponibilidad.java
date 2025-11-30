package uniandes.edu.co.demo.modelo;

public class Disponibilidad {
    private boolean Lunes;
    private boolean Martes;
    private boolean Miercoles;
    private boolean Jueves;
    private boolean Viernes;    
    private boolean Sabado;
    private boolean Domingo;
    private String horaInicio;
    private String horaFin;
    private String placa;
    private String nivel;
    private String tipoServicio;

    public Disponibilidad( boolean lunes, boolean martes, boolean miercoles, boolean jueves,
            boolean viernes, boolean sabado, boolean domingo, String horaInicio, String horaFin, String placa,
            String nivel, String tipoServicio) {
        Lunes = lunes;
        Martes = martes;
        Miercoles = miercoles;
        Jueves = jueves;
        Viernes = viernes;
        Sabado = sabado;
        Domingo = domingo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.placa = placa;
        this.nivel = nivel;
        this.tipoServicio=tipoServicio;
    }

    public boolean isLunes() {
        return Lunes;
    }

    public void setLunes(boolean lunes) {
        Lunes = lunes;
    }

    public boolean isMartes() {
        return Martes;
    }

    public void setMartes(boolean martes) {
        Martes = martes;
    }

    public boolean isMiercoles() {
        return Miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        Miercoles = miercoles;
    }

    public boolean isJueves() {
        return Jueves;
    }

    public void setJueves(boolean jueves) {
        Jueves = jueves;
    }

    public boolean isViernes() {
        return Viernes;
    }

    public void setViernes(boolean viernes) {
        Viernes = viernes;
    }

    public boolean isSabado() {
        return Sabado;
    }

    public void setSabado(boolean sabado) {
        Sabado = sabado;
    }

    public boolean isDomingo() {
        return Domingo;
    }

    public void setDomingo(boolean domingo) {
        Domingo = domingo;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    

    

}
