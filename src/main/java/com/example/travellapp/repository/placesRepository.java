package com.example.travellapp.repository;

import com.example.travellapp.models.placesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface placesRepository extends JpaRepository<placesModel,Integer> {
}
