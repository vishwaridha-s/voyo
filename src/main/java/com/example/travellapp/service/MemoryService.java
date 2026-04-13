package com.example.travellapp.service;

import com.example.travellapp.models.Memory;
import com.example.travellapp.models.User;
import com.example.travellapp.models.placesModel;
import com.example.travellapp.repository.MemoryRepository;
import com.example.travellapp.repository.UserRepository;
import com.example.travellapp.repository.placesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemoryService {

    @Autowired
    private MemoryRepository memoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private placesRepository placeRepository;

    public Memory createMemory(int userId, int placeId, Memory memory, MultipartFile image) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        placesModel place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        memory.setUser(user);
        memory.setPlace(place);
        memory.setCreatedAt(LocalDateTime.now());

        if (image != null && !image.isEmpty()) {
            memory.setImageName(image.getOriginalFilename());
            memory.setImageType(image.getContentType());
            memory.setImageData(image.getBytes());
        }

        return memoryRepository.save(memory);
    }

    public List<Memory> getMemoriesForUser(int userId) {
        return memoryRepository.findByUserId(userId);
    }

    public Memory getMemoryById(int memoryId) {
        Optional<Memory> memory = memoryRepository.findById(memoryId);
        return memory.orElse(null);
    }

    public boolean deleteMemory(int memoryId) {
        if (memoryRepository.existsById(memoryId)) {
            memoryRepository.deleteById(memoryId);
            return true;
        }
        return false;
    }
}
