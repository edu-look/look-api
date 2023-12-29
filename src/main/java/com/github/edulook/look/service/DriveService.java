package com.github.edulook.look.service;

import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.google.api.services.drive.Drive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DriveService {

    private final Drive drive;

    public DriveService(Drive drive) {
        this.drive = drive;
    }

    public Optional<File> download(String fileId) {
        return download(fileId, "./");
    }

    public Optional<File> download(String fileId, String pathToSave) {
        var pathResolved = Paths.get(pathToSave).normalize().toAbsolutePath();

        if(fileId.isBlank())
            throw new TextExtractInvalidException("file id is invalid");

        try(var outputStream = new ByteArrayOutputStream()) {
            var originFile = drive.files()
                    .get(fileId)
                    .execute();

            var saveTo = new File(pathResolved + originFile.getName());

            drive.files()
                    .get(originFile.getId())
                    .executeAndDownloadTo(outputStream);

            try(var fileOutputStream = new FileOutputStream(saveTo)) {
                fileOutputStream.write(outputStream.toByteArray());
            }

            return Optional.of(saveTo);

        } catch (IOException e) {
            log.warn("error::", e);
            return Optional.empty();
        }
    }

    public Optional<File> downloadViaSharedLink(String sharedLink) {
        return downloadViaSharedLink(sharedLink, "./");
    }

    public Optional<File> downloadViaSharedLink(String sharedLink, String pathToSave) {
        var fileId = getIdFromDriveFile(sharedLink);

        if(fileId.isBlank()) {
            log.error("file id is invalid");
            return Optional.empty();
        }

        return download(fileId, pathToSave);
    }

    private String getIdFromDriveFile(String url) {
        var regex = "\\/d\\/(?<id>[\\w+_-]+)\\/";

        var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(url);

        if(matcher.find())
            return matcher.group("id");

        return "";
    }
}

