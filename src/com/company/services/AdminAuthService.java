package com.company.services;

import com.company.repositories.AdminRepository;

public class AdminAuthService {

    private final AdminRepository repo;

    public AdminAuthService(AdminRepository repo) {
        this.repo = repo;
    }
    public boolean login(String username, String password) {
        return repo.authenticate(username, password);
    }
}
