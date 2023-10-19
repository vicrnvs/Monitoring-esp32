package unip.com.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unip.com.domain.model.Esp32ConfigParams;
import unip.com.outbound.adapter.mysql.entities.Esp32ConfigParamsEntity;
import unip.com.outbound.adapter.mysql.entities.Esp32Entity;

import java.time.LocalDate;
import java.util.List;

public interface Esp32ConfigRepository extends JpaRepository<Esp32ConfigParamsEntity, Integer> {

    @Query("SELECT e FROM esp32ConfigParams e WHERE e.esp32.identificador = :identificador AND e.active = :active")
    List<Esp32ConfigParamsEntity> findByIdentificadorConfigParamsActive(String identificador, boolean active);

}
