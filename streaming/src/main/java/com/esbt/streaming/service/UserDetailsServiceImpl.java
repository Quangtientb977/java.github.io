package com.esbt.streaming.service;

import com.esbt.streaming.data.model.User;
import com.esbt.streaming.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username does not exist"));
        return user;
    }

    public UserDetails loadUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("username does not exist"));
        return user;
    }
}
