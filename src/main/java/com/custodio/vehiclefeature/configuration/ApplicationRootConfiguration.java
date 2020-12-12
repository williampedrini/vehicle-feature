package com.custodio.vehiclefeature.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EntityScan(basePackages = "com.custodio.vehiclefeature.domain.entity")
class ApplicationRootConfiguration {

    @Bean
    @Primary
    ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    CsvMapper csvMapper() {
        return new CsvMapper();
    }
}
