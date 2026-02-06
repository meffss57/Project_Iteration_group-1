package com.company.repositories.interfaces;

import com.company.models.AuthUser;

public interface IUserRepository {
    AuthUser login(String name, String password);
    boolean register(String name, String password);
}
