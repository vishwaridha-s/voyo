package com.example.travellapp.service;

import com.example.travellapp.models.User;
import com.example.travellapp.models.placesModel;
import com.example.travellapp.repository.BucketRepository;
import com.example.travellapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService{

    @Autowired
    private BucketRepository Repo;

    @Autowired
    private UserRepository userRepo;

    public List<placesModel> getAllPlaces() {
        return Repo.findAll();
    }

    public List<placesModel> searchPlaces(String keyword) {
        return Repo.findByLocationContainingIgnoreCase(keyword);
    }

    public void addToBucketList(int userId, int placeId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        placesModel place = Repo.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        if (!user.getBucketList().contains(place)) {
            user.getBucketList().add(place);
            userRepo.save(user);
        }
    }
    public List<placesModel> getBucketList(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBucketList();
    }

    public void removeFromBucketList(int userId, int placeId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        placesModel place = Repo.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        user.getBucketList().remove(place);
        userRepo.save(user);
    }
}

