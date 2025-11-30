package uniandes.edu.co.demo.modelo;

public class Disponibilidad {
    private String placa;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;
    private boolean domingo;
    private String inicio;
    private String fin;

    public Disponibilidad(String placa, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado, boolean domingo, String inicio, String fin){
        this.placa=placa;
        this.lunes=lunes;
        this.martes=martes;
        this.miercoles=miercoles;
        this.jueves=jueves;
        this.viernes=viernes;
        this.sabado=sabado;
        this.domingo=domingo;
        this.inicio=inicio;
        this.fin=fin;
    }

    public String getPlaca() {
        return placa;
    }

    public boolean isLunes() {
        return lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public boolean isSabado() {
        return sabado;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    

}
