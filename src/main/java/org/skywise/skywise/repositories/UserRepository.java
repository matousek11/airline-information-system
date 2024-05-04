package org.skywise.skywise.repositories;

import org.skywise.skywise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    ArrayList<User> findAllByRole(String role);
}
