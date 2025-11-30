package uniandes.edu.co.demo.modelo;

public class Vehiculo {
    private String placa;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private Integer capacidad;

    public Vehiculo(String placa, String tipo, String marca, String modelo, String color, String ciudadPlaca, Integer capacidad){
        this.placa=placa;
        this.tipo=tipo;
        this.marca=marca;
        this.modelo=modelo;
        this.color=color;
        this.ciudadPlaca=ciudadPlaca;
        this.capacidad=capacidad;
     
    }

    public String getPlaca() {
        return placa;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public String getCiudadPlaca() {
        return ciudadPlaca;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

   

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCiudadPlaca(String ciudadPlaca) {
        this.ciudadPlaca = ciudadPlaca;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    

    


}
