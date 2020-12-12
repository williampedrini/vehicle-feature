package com.custodio.vehiclefeature.adapter.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.integration")
class FileProcessingFlowProperties {
    private Directory directory;
    private long pollerDelay;
    @Data
    static class Directory {
        private String input;
        private String output;
        private String error;
    }
}
