package uniandes.edu.co.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.Tarjeta;
import uniandes.edu.co.demo.modelo.UsuarioServicio;



public interface UsuarioServicioRepository extends MongoRepository <UsuarioServicio, Integer> {

    default UsuarioServicio insertarUsuarioServicio(int id,
                                                    String nombre,
                                                    String celular,
                                                    String cedula,
                                                    String email,
                                                    Tarjeta tarjeta) {
        UsuarioServicio usuario = new UsuarioServicio(
                id,
                nombre,
                celular,
                cedula,
                email,
                tarjeta
        );
        return save(usuario);
    }

    @Query("{ _id: ?0 }")
    UsuarioServicio getById(int id);

}
