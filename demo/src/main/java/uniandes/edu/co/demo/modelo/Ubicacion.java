package uniandes.edu.co.demo.modelo;

public class Ubicacion {
    private String direccion;
    private Float lat;
    private Float lng;

    Ubicacion(String direccion, Float lat, Float lng){
        this.direccion=direccion;
        this.lat=lat;
        this.lng=lng;

    }

    public String getDireccion() {
        return direccion;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    

}
