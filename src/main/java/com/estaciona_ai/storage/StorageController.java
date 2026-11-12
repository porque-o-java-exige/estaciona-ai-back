package com.estaciona_ai.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StorageController {

    private final StorageService storageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "general") String folder
    ) {
        String url = storageService.upload(file, folder);
        return ResponseEntity.ok(Map.of("url", url));
    }
}