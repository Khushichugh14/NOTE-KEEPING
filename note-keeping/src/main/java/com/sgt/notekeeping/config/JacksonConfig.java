package com.sgt.notekeeping.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /**
     * Increase Jackson's max JSON string length to support base64 encoded images.
     * Default limit is 20,000,000 chars (~20MB). We raise it to 100MB.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.postConfigurer((ObjectMapper objectMapper) -> {
            objectMapper.getFactory().setStreamReadConstraints(
                StreamReadConstraints.builder()
                    .maxStringLength(100_000_000) // 100 million chars = ~100MB
                    .build()
            );
        });
    }
}
