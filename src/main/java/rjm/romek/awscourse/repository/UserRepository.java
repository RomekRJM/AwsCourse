package rjm.romek.awscourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rjm.romek.awscourse.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}