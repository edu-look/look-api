package com.github.edulook.look.infra.worker.cron;

import com.google.api.client.auth.oauth2.Credential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Slf4j
@Configuration
@EnableScheduling
public class RefreshTokenSchedule {
    private final Credential credential;

    public RefreshTokenSchedule(Credential credential) {
        this.credential = credential;
    }

    @Scheduled(fixedRateString = "${look.application.refresh_token.time}")
    public void refreshToken() throws IOException {
        var isOk = credential.refreshToken();
        log.info("refresh token {}", isOk);
    }
}
