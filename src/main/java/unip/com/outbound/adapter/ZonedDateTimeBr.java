package unip.com.outbound.adapter;

import io.quarkus.runtime.StartupEvent;
import unip.com.outbound.port.ZonedDateTimeBrPort;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@ApplicationScoped
public class ZonedDateTimeBr implements ZonedDateTimeBrPort {

    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now(getZoneId());
    }

    @Override
    public ZoneId getZoneId(){
        return TimeZone.getTimeZone("UTC").toZoneId();
    }

}
