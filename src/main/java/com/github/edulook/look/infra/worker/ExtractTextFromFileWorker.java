package com.github.edulook.look.infra.worker;

import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@Configuration
@EnableAsync
public class ExtractTextFromFileWorker {
    @Async
    @EventListener
    public void processorWorkMaterial(CourseMaterialExtractPDFEvent event) {
        log.info("run OCR service");
    }
}
