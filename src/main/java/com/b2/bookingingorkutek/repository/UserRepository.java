package com.b2.bookingingorkutek.repository;

import com.b2.bookingingorkutek.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @NonNull
    List<User> findAllByRole(String role);
}