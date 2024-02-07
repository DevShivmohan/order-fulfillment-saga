package dev.example.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class ApplicationProperties {
    private String workflowId;
    private String temporalServer;
}
