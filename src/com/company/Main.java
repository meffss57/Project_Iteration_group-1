package com.company;

import com.company.controllers.CarController;
import com.company.controllers.interfaces.ICarController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.AdminRepository;
import com.company.repositories.CarRepository;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.ICarRepository;
import com.company.services.AdminAuthService;
import com.company.services.UserAuthService;
import com.company.factory.AppFactory;

public class Main {
    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        IDB db = PostgresDB.getInstance(url, user, password, dbName);

// Repositories
        ICarRepository repo =
                AppFactory.createCarRepository(db);

        AdminRepository adminRepo =
                AppFactory.createAdminRepository(db);

        UserRepository userRepo =
                AppFactory.createUserRepository(db);

// Services
        AdminAuthService authService =
                AppFactory.createAdminService(adminRepo);

        UserAuthService userAuthService =
                AppFactory.createUserService(userRepo);

// Controller
        ICarController controller =
                AppFactory.createCarController(repo);

        MyApplication app =
                new MyApplication(controller, authService, userAuthService);


        app.start();

        db.close();

        // S = every part of the code is responsible to its tasks
        // O = Open/Closed means that you add to the code a new code . You are not rewriting all code
        // L = part of the code can be substituted by another part
        // I = class does his personal tasks not others
        // D = we can change methods not changing main logic!
        // Added Join with id
        // Added lambda methods
        // Changed boolean type to integer in login method
        // added purchases table to PgAdmin
        // understood SOLID
    }
}
