package com.example.travellapp.service;
import com.example.travellapp.models.User;
import com.example.travellapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserService {
    @Autowired
    UserRepository repo;
    public User register(User u) {

        return repo.save(u);
    }

    public User login(String username, String password) {
        return repo.findByUsernameAndPassword(username, password);
    }
    public User getUserById(int id) {
        return repo.findById(id).orElse(null);
    }

}