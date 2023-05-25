package com.b2.bookingingorkutek.service;

import com.b2.bookingingorkutek.model.User;
import com.b2.bookingingorkutek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUser(){
        return userRepository.findAllByRole("USER");
    }

}
