package com.example.travellapp.controller;

import com.example.travellapp.models.Memory;
import com.example.travellapp.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/memories")
public class MemoryController {

    @Autowired
    private MemoryService service;

    @PostMapping(value = "/add/{userId}/{placeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addMemory(@PathVariable int userId,
                                       @PathVariable int placeId,
                                       @RequestPart("memory") Memory memory,
                                       @RequestPart MultipartFile image) {
        try {
            Memory saved = service.createMemory(userId, placeId, memory, image);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Memory>> getMemories(@PathVariable int userId) {
        return ResponseEntity.ok(service.getMemoriesForUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemory(@PathVariable int id) {
        Memory memory = service.getMemoryById(id);
        if (memory == null) {
            return new ResponseEntity<>("Memory not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(memory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMemory(@PathVariable int id) {
        if (service.deleteMemory(id)) {
            return new ResponseEntity<>("Memory deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Memory not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getMemoryImage(@PathVariable int id) {
        Memory memory = service.getMemoryById(id);
        if (memory == null || memory.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }
        MediaType contentType = MediaType.IMAGE_JPEG;
        if (memory.getImageType() != null && !memory.getImageType().isBlank()) {
            try {
                contentType = MediaType.valueOf(memory.getImageType());
            } catch (IllegalArgumentException ignored) {
                contentType = MediaType.IMAGE_JPEG;
            }
        }
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"memory-image-" + id + "\"")
                .body(memory.getImageData());
    }
}
