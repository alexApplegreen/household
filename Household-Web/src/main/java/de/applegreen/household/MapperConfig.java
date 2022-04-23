package de.applegreen.household;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
