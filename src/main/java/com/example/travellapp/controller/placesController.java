package com.example.travellapp.controller;

import com.example.travellapp.models.placesModel;
import com.example.travellapp.service.placesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/places")
public class placesController {

    @Autowired
    private placesService service;

    @PostMapping(value="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPlaces(
            @RequestPart("places") String placesJson,
            @RequestPart("image") MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            placesModel places = mapper.readValue(placesJson, placesModel.class);

            placesModel p = service.addPlaces(places, image);
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<placesModel>> getAllPlaces() {
        List<placesModel> places = service.getAllPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaceById(@PathVariable int id) {
        placesModel place = service.getPlaceById(id);
        if (place != null) {
            return new ResponseEntity<>(place, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Place not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable int id, @RequestPart placesModel updatedPlace, @RequestPart(required = false) MultipartFile image) throws IOException {
        placesModel updated = service.updatePlace(id, updatedPlace, image);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Place not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable int id) {
        boolean deleted = service.deletePlace(id);
        if (deleted) {
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Place not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getPlaceImage(@PathVariable int id) {
        placesModel place = service.getPlaceById(id);
        if (place == null || place.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(place.getImageType()))
                .body(place.getImageData());
    }
}
