package uniandes.edu.co.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Conductor2;
import uniandes.edu.co.demo.modelo.Vehiculo2;

public interface ConductorRepository extends MongoRepository <Conductor2, Integer> {
    @Query("{ _id: ?0 }")
    @Update("{ $set: { enServicio: true } }")
    void EstaEnServicio(int id);


    @Query("{ _id: ?0 }")
    @Update("{ $set: { enServicio: false } }")
    void YaNoEstaEnServicio(int id);


    @Query("{ 'vehiculos.placa': ?0 }")
    Vehiculo2 getVehiculoPorPlaca(String placa);

    
}
