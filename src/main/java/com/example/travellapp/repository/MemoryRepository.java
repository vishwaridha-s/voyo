package com.example.travellapp.repository;

import com.example.travellapp.models.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoryRepository extends JpaRepository<Memory, Integer> {
    List<Memory> findByUserId(int userId);
}
