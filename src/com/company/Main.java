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

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB(
                "jdbc:postgresql://localhost:5432",
                "postgres",
                "0000",
                "postgres"
        );


        ICarRepository repo = new CarRepository(db);
        ICarController controller = new CarController(repo);

        AdminRepository adminRepo = new AdminRepository(db);
        AdminAuthService authService = new AdminAuthService(adminRepo);


        UserRepository userRepo = new UserRepository(db);
        UserAuthService userAuthService = new UserAuthService(userRepo);

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
