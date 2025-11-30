package uniandes.edu.co.demo.modelo;



public class Vehiculo {
    private String placa;
    private String marca;
    private String color;
    private Integer capacidad;
    // no nos interesa la ciuda de la placa porque es NoSQL

    
    public Vehiculo(String placa, String modelo, String color, Integer capacidad) {
        this.placa = placa;
        this.marca = modelo;
        this.color = color;
        this.capacidad = capacidad;
    }
    public Integer getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setModelo(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
