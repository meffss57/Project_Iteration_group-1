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
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        IDB db = new PostgresDB(url, user, password, dbName);


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
    }
}
