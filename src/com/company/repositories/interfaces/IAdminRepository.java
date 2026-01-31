package com.company.repositories.interfaces;

public interface IAdminRepository {
    boolean authenticate(String name, String password);
}
