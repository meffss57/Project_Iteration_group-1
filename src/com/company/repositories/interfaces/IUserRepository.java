package com.company.repositories.interfaces;

public interface IUserRepository {
    Integer login(String name, String password);

    boolean register(String name, String password);
}
