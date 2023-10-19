package unip.com.outbound.port;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public interface ZonedDateTimeBrPort {

    ZonedDateTime now();

    ZoneId getZoneId();
}
