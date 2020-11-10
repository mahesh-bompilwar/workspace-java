package com.techdenovo.app.jwtspringbootapp.repository;

import com.techdenovo.app.jwtspringbootapp.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DAOUser, Long> {
    DAOUser findByUsername(String username);
}
