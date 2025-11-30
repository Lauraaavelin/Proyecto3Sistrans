package uniandes.edu.co.demo.repository;

import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Consultas {

    private final MongoTemplate mongoTemplate;

    public Consultas(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /** ---------------------------------------------------------
     * RFC1: HISTÓRICO DE SERVICIOS PEDIDOS POR UN USUARIO
     * ---------------------------------------------------------
     * Filtra por usuarioServicio.id
     */
    public List<Document> obtenerHistoricoServiciosUsuario(int idUsuario) {

        List<Document> pipeline = List.of(
            new Document("$match",
                new Document("usuarioServicio.id", idUsuario)
            )
        );

        return mongoTemplate.getCollection("Servicio")
                .aggregate(pipeline)
                .into(new java.util.ArrayList<>());
    }

    /** ---------------------------------------------------------
     * RFC2: TOP 20 CONDUCTORES QUE MÁS SERVICIOS HAN PRESTADO
     * ---------------------------------------------------------
     * Agrupa por conductor.id
     */
    public List<Document> obtenerTop20Conductores() {

        List<Document> pipeline = List.of(
            new Document("$group", new Document()
                .append("_id", "$conductor.id")
                .append("totalServicios", new Document("$sum", 1))
            ),
            new Document("$sort", new Document("totalServicios", -1)),
            new Document("$limit", 20),
            // Opcional: incluir detalles del conductor desde otra colección
            new Document("$lookup", new Document()
                .append("from", "Conductor")
                .append("localField", "_id")
                .append("foreignField", "id")
                .append("as", "infoConductor")
            )
        );

        return mongoTemplate.getCollection("Servicio")
                .aggregate(pipeline)
                .into(new java.util.ArrayList<>());
    }

    /** ---------------------------------------------------------
     * RFC3: USO DE SERVICIOS POR CIUDAD EN UN RANGO DE FECHAS
     * ---------------------------------------------------------
     * Filtra por ciudad y fecha, agrupa por TipoServicio.
     */
    public List<Document> obtenerUsoServiciosCiudad(
        String ciudad, String fechaInicio, String fechaFin) {

    List<Document> pipeline = List.of(
        new Document("$match", new Document()
            .append("ciudad", ciudad)
            .append("fecha", new Document()
                .append("$gte", fechaInicio)
                .append("$lte", fechaFin)
            )
        ),
        // Usamos facet para obtener:
        // 1. Conteo por tipo
        // 2. Total de servicios
        new Document("$facet", new Document()
            .append("conteoPorTipo", List.of(
                new Document("$group", new Document()
                    .append("_id", "$TipoServicio")
                    .append("cantidad", new Document("$sum", 1))
                ),
                new Document("$sort", new Document("cantidad", -1))
            ))
            .append("total", List.of(
                new Document("$count", "total")
            ))
        ),
        new Document("$unwind", "$total"),
        new Document("$unwind", "$conteoPorTipo"),
        new Document("$project", new Document()
            .append("tipo", "$conteoPorTipo._id")
            .append("cantidad", "$conteoPorTipo.cantidad")
            .append("porcentaje",
                new Document("$multiply", List.of(
                    new Document("$divide", List.of(
                        "$conteoPorTipo.cantidad",
                        "$total.total"
                    )),
                    100
                ))
            )
        ),
        new Document("$sort", new Document("cantidad", -1))
    );

    return mongoTemplate.getCollection("Servicio")
            .aggregate(pipeline)
            .into(new java.util.ArrayList<>());
}

}
