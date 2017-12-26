package presentation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import infrastructure.MqttService;
import play.libs.Json;

public class PresentationModule extends AbstractModule {
    @Override
    protected void configure() {
        ObjectMapper mapper = Json.newDefaultMapper()
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Json.setObjectMapper(mapper);

        bind(MqttService.class).asEagerSingleton();
    }
}
