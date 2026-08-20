package notes.app.demo.controller;

//package com.notesapp.notes.controller;

import notes.app.demo.entity.User;
import notes.app.demo.service.FileStorageService;
import notes.app.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;
    private final UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            HttpSession session
    ) {

        try {

            String username =
                    (String) session.getAttribute(
                            "USERNAME"
                    );

            if (username == null) {

                return ResponseEntity
                        .status(401)
                        .body("Not logged in");
            }

            User user =
                    userService.findByUsername(username);

            String path =
                    fileStorageService.storeFile(
                            file,
                            user
                    );

            return ResponseEntity.ok(
                    java.util.Map.of(
                            "path",
                            path
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{username}/{filename}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String username,
            @PathVariable String filename
    ) {

        try {

            Resource resource =
                    fileStorageService.loadFile(
                            username,
                            filename
                    );

            String contentType =
                    "application/octet-stream";

            String filenameLower =
                    filename.toLowerCase();

            if (filenameLower.endsWith(".png")) {
                contentType = "image/png";
            } else if (
                    filenameLower.endsWith(".jpg") ||
                            filenameLower.endsWith(".jpeg")
            ) {
                contentType = "image/jpeg";
            } else if (
                    filenameLower.endsWith(".gif")
            ) {
                contentType = "image/gif";
            } else if (
                    filenameLower.endsWith(".webp")
            ) {
                contentType = "image/webp";
            }

            return ResponseEntity.ok()
                    .contentType(
                            MediaType.parseMediaType(
                                    contentType
                            )
                    )
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" +
                                    filename +
                                    "\""
                    )
                    .body(resource);

        } catch (Exception e) {

            return ResponseEntity.notFound()
                    .build();
        }
    }
}