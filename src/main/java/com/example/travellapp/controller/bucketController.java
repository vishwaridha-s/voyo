package com.example.travellapp.controller;

import com.example.travellapp.models.placesModel;
import com.example.travellapp.service.BucketService;
import com.example.travellapp.service.placesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-places")
public class bucketController {

    @Autowired
    private BucketService service;

    @GetMapping("/places")
    public ResponseEntity<List<placesModel>> getAllPlaces() {
        List<placesModel> allPlaces = service.getAllPlaces();
        return ResponseEntity.ok(allPlaces);
    }

    @GetMapping("/places/search")
    public ResponseEntity<List<placesModel>> searchPlaces(@RequestParam("keyword") String keyword) {
        List<placesModel> result = service.searchPlaces(keyword);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addtolist/{userId}/{placeId}")
    public ResponseEntity<String> addToBucket(@PathVariable int userId, @PathVariable int placeId) {
        service.addToBucketList(userId, placeId);
        return ResponseEntity.ok("Place added to bucket list");
    }

    @GetMapping("/mylist/{userId}")
    public ResponseEntity<List<placesModel>> getUserBucketList(@PathVariable int userId) {
        List<placesModel> bucket = service.getBucketList(userId);
        return ResponseEntity.ok(bucket);
    }

    @DeleteMapping("/removefromlist/{userId}/{placeId}")
    public ResponseEntity<String> removeFromBucket(@PathVariable int userId, @PathVariable int placeId) {
        service.removeFromBucketList(userId, placeId);
        return ResponseEntity.ok("Place removed from bucket list");
    }
}
