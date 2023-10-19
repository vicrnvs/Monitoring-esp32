package unip.com.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.com.outbound.adapter.mysql.entities.SensorDataEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public interface SensorDataRepository extends JpaRepository<SensorDataEntity, Integer> {


}
