package com.company.services;

import com.company.repositories.UserRepository;

public class UserAuthService {

    private final UserRepository repo;

    public UserAuthService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean username(String username, String password) {
        return repo.authenticate(username, password);
    }
}
