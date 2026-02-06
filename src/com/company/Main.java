package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.factory.AppFactory;
import com.company.repositories.interfaces.ICarRepository;
import com.company.repositories.interfaces.IUserRepository;
import com.company.services.UserAuthService;

public class Main {
    public static void main(String[] args) {

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        IDB db = PostgresDB.getInstance(url, user, password, dbName);

        ICarRepository carRepo = AppFactory.createCarRepository(db);
        ICarController carController = AppFactory.createCarController(carRepo);

        IUserRepository userRepo = AppFactory.createUserRepository(db);
        UserAuthService userAuthService = AppFactory.createUserService(userRepo);

        MyApplication app = new MyApplication(carController, userAuthService);
        app.start();

        db.close();
    }
}
