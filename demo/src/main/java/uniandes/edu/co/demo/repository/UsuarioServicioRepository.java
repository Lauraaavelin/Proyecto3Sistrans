package uniandes.edu.co.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.UsuarioServicio;



public interface UsuarioServicioRepository extends MongoRepository <UsuarioServicio, Integer> {
 
    @Query("{$insert:{_id:?0, nombre:?1, celular:?2, cedula:?3, email:?4, tarjeta:?5}}")
    void insertarUsuarioServicio(int id, String nombre,  String celular, String cedula, String email, Object tarjeta);

    @Query("{ _id: ?0 }")
    UsuarioServicio getById(int id);

}
