package com.example.travellapp.repository;
import com.example.travellapp.models.placesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BucketRepository extends JpaRepository<placesModel, Integer> {
    List<placesModel> findByLocationContainingIgnoreCase(String keyword);
}
