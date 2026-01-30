package com.company.factory;

import com.company.controllers.CarController;
import com.company.controllers.interfaces.ICarController;
import com.company.data.interfaces.IDB;
import com.company.repositories.*;
import com.company.repositories.interfaces.ICarRepository;
import com.company.services.*;

public class AppFactory {

    // ---------- Repositories ----------

    public static ICarRepository createCarRepository(IDB db) {
        return new CarRepository(db);
    }

    public static UserRepository createUserRepository(IDB db) {
        return new UserRepository(db);
    }

    public static AdminRepository createAdminRepository(IDB db) {
        return new AdminRepository(db);
    }

    // ---------- Services ----------

    public static UserAuthService createUserService(UserRepository repo) {
        return new UserAuthService(repo);
    }

    public static AdminAuthService createAdminService(AdminRepository repo) {
        return new AdminAuthService(repo);
    }

    // ---------- Controllers ----------

    public static ICarController createCarController(ICarRepository repo) {
        return new CarController(repo);
    }
}