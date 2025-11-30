package uniandes.edu.co.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.Servicio;

public interface ServicioRepository extends MongoRepository<Servicio, Integer> {

  @Query("{$insert:{_id:?0, fecha:?1, usuarioServicio:?2, conductor:?3, ciudad:?4, puntoPartida:?5, puntosLlegada:?6, vehiculo:?7, horaInicio:?8, horaFin:?9, distanciaKm:?10, comision:?11, valorTotal:?12, TipoServicio:?13, nivel:?14, orden:?15, restaurante:?16, elemento:?17}}")
  void insertarServicio(int id, String fecha, Object usuarioServicio, Object conductor, String ciudad, Object puntoPartida,
      Object puntosLlegada, Object vehiculo, String horaInicio, String horaFin, float distanciaKm, float comision, Integer valorTotal, String TipoServicio,
      String nivel, String orden, String restaurante, String elemento);
}
