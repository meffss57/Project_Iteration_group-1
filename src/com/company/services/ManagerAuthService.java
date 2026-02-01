package com.company.services;


import com.company.repositories.ManagerRepository;

public class ManagerAuthService {

    private final ManagerRepository repo;

    public ManagerAuthService(ManagerRepository repo) {
        this.repo = repo;
    }

    public boolean login(String username, String password) {
        return repo.authenticate(username, password);
    }
}