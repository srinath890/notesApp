package notes.app.demo.service;

//package com.notesapp.notes.service;

//import com.notesapp.notes.entity.User;
import notes.app.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final Path uploadRoot =
            Paths.get("uploads").toAbsolutePath().normalize();

    public String storeFile(
            MultipartFile file,
            User user
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "File is empty"
            );
        }

        String originalFilename =
                file.getOriginalFilename();

        if (originalFilename == null) {
            throw new IllegalArgumentException(
                    "Invalid filename"
            );
        }

        String extension = "";

        int dotIndex =
                originalFilename.lastIndexOf(".");

        if (dotIndex >= 0) {
            extension =
                    originalFilename.substring(dotIndex)
                            .toLowerCase();
        }

        if (!extension.matches(
                "\\.(jpg|jpeg|png|gif|webp)"
        )) {

            throw new IllegalArgumentException(
                    "Only JPG, JPEG, PNG, GIF and WEBP images are allowed"
            );
        }

        if (file.getSize() > 10 * 1024 * 1024) {

            throw new IllegalArgumentException(
                    "Image size cannot exceed 10 MB"
            );
        }

        String username =
                user.getUsername()
                        .replaceAll("[^a-zA-Z0-9_-]", "_");

        Path userDirectory =
                uploadRoot.resolve(username);

        Files.createDirectories(userDirectory);

        String filename =
                UUID.randomUUID() + extension;

        Path target =
                userDirectory.resolve(filename)
                        .normalize();

        if (!target.startsWith(userDirectory)) {
            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        try (InputStream inputStream =
                     file.getInputStream()) {

            Files.copy(
                    inputStream,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        return username + "/" + filename;
    }

    public Resource loadFile(
            String username,
            String filename
    ) throws IOException {

        Path userDirectory =
                uploadRoot.resolve(username)
                        .normalize();

        Path file =
                userDirectory.resolve(filename)
                        .normalize();

        if (!file.startsWith(userDirectory)) {
            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        Resource resource =
                new UrlResource(file.toUri());

        if (!resource.exists() ||
                !resource.isReadable()) {

            throw new NoSuchFileException(
                    file.toString()
            );
        }

        return resource;
    }
}