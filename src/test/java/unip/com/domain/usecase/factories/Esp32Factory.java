package unip.com.domain.usecase.factories;

import unip.com.domain.model.Esp32;
import unip.com.domain.model.Esp32ConfigParams;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Esp32Factory {

    public Esp32 createEsp32(Integer id){
        return Esp32.builder().id(id).identificador("0123456789")
                .nomeRua("Rua").numero("25").cidade("cidade")
                .bairro("bairro").cep("06950000").estado("são paulo")
                .pais("Brasil").latitude("26.1648").longitude("165.4561")
                .altura(10).criadoEm(ZonedDateTime.now())
                .proximaManutencao(LocalDate.now().plusDays(10)).build();

    }
}
