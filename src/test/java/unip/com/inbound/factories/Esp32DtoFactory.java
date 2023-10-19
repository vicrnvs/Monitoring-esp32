package unip.com.inbound.factories;

import unip.com.domain.model.Esp32;
import unip.com.inbound.adapter.dto.Esp32Dto;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@ApplicationScoped
public class Esp32DtoFactory {

    public Esp32Dto createEsp32(Integer id){
        return Esp32Dto.builder().id(id).identificador("1234567890")
                .nomeRua("Rua").numero("25").cidade("cidade")
                .bairro("bairro").cep("06950000").estado("são paulo")
                .pais("Brasil").latitude("26.1648").longitude("165.4561")
                .altura(10).criadoEm(ZonedDateTime.now())
                .proximaManutencao(LocalDate.now().plusDays(10)).build();

    }
}
