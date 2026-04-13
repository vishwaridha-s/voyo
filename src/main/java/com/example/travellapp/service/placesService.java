package com.example.travellapp.service;
import com.example.travellapp.models.placesModel;
import com.example.travellapp.repository.placesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class placesService {
    @Autowired
    private placesRepository repo;
    public placesModel addPlaces(placesModel places, MultipartFile image) throws IOException {
        places.setImageName(image.getOriginalFilename());
        places.setImageType(image.getContentType());
        places.setImageData(image.getBytes());
        System.out.println("Location: " + places.getLocation());
        System.out.println("Description: " + places.getDescription());
        System.out.println("Price: " + places.getPrice());
        return repo.save(places);
    }

    public List<placesModel> getAllPlaces() {
        return repo.findAll();
    }
    public placesModel getPlaceById(int id) {
        Optional<placesModel> place = repo.findById(id);
        return place.orElse(null);
    }

    public placesModel updatePlace(int id, placesModel updatedPlace, MultipartFile image) throws IOException {
        Optional<placesModel> optional = repo.findById(id);
        if (optional.isPresent()) {
            placesModel existing = optional.get();
            existing.setLocation(updatedPlace.getLocation());
            existing.setDescription(updatedPlace.getDescription());
            existing.setPrice(updatedPlace.getPrice());

            if (image != null && !image.isEmpty()) {
                existing.setImageName(image.getOriginalFilename());
                existing.setImageType(image.getContentType());
                existing.setImageData(image.getBytes());
            }

            return repo.save(existing);
        }
        return null;
    }
    public boolean deletePlace(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
