package org.takehomeassessment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
public class TakeHomeAssessmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeHomeAssessmentApplication.class, args);
    }
}
