package com.company.factory;

import com.company.controllers.CarController;
import com.company.controllers.interfaces.ICarController;
import com.company.data.interfaces.IDB;
import com.company.repositories.CarRepository;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.ICarRepository;
import com.company.repositories.interfaces.IUserRepository;
import com.company.services.UserAuthService;

public class AppFactory {

    public static ICarRepository createCarRepository(IDB db) {
        return new CarRepository(db);
    }

    public static IUserRepository createUserRepository(IDB db) {
        return new UserRepository(db);
    }

    public static UserAuthService createUserService(IUserRepository repo) {
        return new UserAuthService(repo);
    }

    public static ICarController createCarController(ICarRepository repo) {
        return new CarController(repo);
    }
}
