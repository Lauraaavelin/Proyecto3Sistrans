package uniandes.edu.co.demo.modelo;

public class RegistrarVehiculoDTO {
    private String placa;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String ciudadPlaca;
    private Integer capacidad;

    public RegistrarVehiculoDTO() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCiudadPlaca() {
        return ciudadPlaca;
    }

    public void setCiudadPlaca(String ciudadPlaca) {
        this.ciudadPlaca = ciudadPlaca;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}