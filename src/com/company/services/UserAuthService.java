package com.company.services;

import com.company.models.AuthUser;
import com.company.repositories.interfaces.IUserRepository;

public class UserAuthService {

    private final IUserRepository repo;

    public UserAuthService(IUserRepository repo) {
        this.repo = repo;
    }

    public AuthUser login(String username, String password) {
        return repo.login(username, password);
    }

    public boolean register(String username, String password) {
        return repo.register(username, password);
    }
}
