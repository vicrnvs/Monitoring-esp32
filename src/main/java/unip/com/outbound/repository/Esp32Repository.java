package unip.com.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unip.com.outbound.adapter.mysql.entities.Esp32Entity;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped

public interface Esp32Repository extends JpaRepository<Esp32Entity, Integer> {

    Esp32Entity findByIdentificador(String identificador);

    @Query("SELECT e FROM esp32 e WHERE e.proximaManutencao <= :antesProximaManu")
    List<Esp32Entity> listarEsp32sComManutencao(LocalDate antesProximaManu);
}
