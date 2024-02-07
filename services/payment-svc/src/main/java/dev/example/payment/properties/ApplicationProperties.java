package dev.example.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class ApplicationProperties {
    private String workflowId;
    private String temporalServer;
}
