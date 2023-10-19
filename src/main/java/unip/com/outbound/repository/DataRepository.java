package unip.com.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unip.com.outbound.adapter.mysql.entities.DataEntity;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

@ApplicationScoped
public interface DataRepository extends JpaRepository<DataEntity, Integer> {

    @Query("SELECT c FROM Data c WHERE c.esp32.estado = :estado AND c.esp32.cidade = :cidade AND c.esp32.bairro = :bairro AND c.coleta BETWEEN :dataInicial AND :dataFinal")
    Stream<DataEntity> findByEsp32CidadeBairro(String estado, String cidade, String bairro, ZonedDateTime dataInicial, ZonedDateTime dataFinal);
    @Query("SELECT c FROM Data c WHERE c.esp32.estado = :estado AND c.esp32.cidade = :cidade AND c.coleta BETWEEN :dataInicial AND :dataFinal")
    Stream<DataEntity> findByEsp32Cidade(String estado, String cidade, ZonedDateTime dataInicial, ZonedDateTime dataFinal);
    @Query("SELECT c FROM Data c WHERE c.esp32.estado = :estado AND c.coleta BETWEEN :dataInicial AND :dataFinal")
    Stream<DataEntity> findByEsp32Estado(String estado, ZonedDateTime dataInicial, ZonedDateTime dataFinal);


}
