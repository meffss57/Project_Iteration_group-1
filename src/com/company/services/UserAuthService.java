package com.company.services;

import com.company.repositories.UserRepository;

public class UserAuthService {

    private final UserRepository repo;

    public UserAuthService(UserRepository repo) {
        this.repo = repo;
    }

    public Integer login(String username, String password) {
        return repo.login(username, password);
    }


    public boolean register(String username, String password) {
        return repo.register(username, password);
    }
}
