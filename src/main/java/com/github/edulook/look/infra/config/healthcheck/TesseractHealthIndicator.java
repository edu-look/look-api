package com.github.edulook.look.infra.config.healthcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TesseractHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        String path = System.getenv("PATH");
        String[] paths = path.split(":");
        boolean found = false;

        for (String p : paths) {
            var tesseractFile = new File(p, "tesseract");
            if (tesseractFile.exists() && tesseractFile.canExecute()) {
                found = true;
                break;
            }
        }

        var status = found ? Health.up() : Health.down();
        status.withDetail("available", found ? "on" : "off");

        return status.build();
    }
}
