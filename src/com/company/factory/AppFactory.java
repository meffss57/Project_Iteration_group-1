package com.company.factory;

import com.company.MyApplication;
import com.company.controllers.CarController;
import com.company.controllers.interfaces.ICarController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.*;
import com.company.repositories.interfaces.*;
import com.company.services.*;
import com.company.services.ChatBotService;


public class AppFactory {

    private final IDB db;

    //создаём DB один раз
    private AppFactory(IDB db) {
        this.db = db;
    }

    //точка входа
    public static AppFactory create() {

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        IDB db = PostgresDB.getInstance(url, user, password, dbName);

        return new AppFactory(db);
    }

    //Repositories

    private ICarRepository createCarRepository() {
        return new CarRepository(db);
    }

    private IUserRepository createUserRepository() {
        return new UserRepository(db);
    }

    private AdminRepository createAdminRepository() {
        return new AdminRepository(db);
    }

    private ManagerRepository createManagerRepository() {
        return new ManagerRepository(db);
    }


    //  Services

    private UserAuthService createUserService() {
        return new UserAuthService(createUserRepository());
    }

    private AdminAuthService createAdminService() {
        return new AdminAuthService(createAdminRepository());
    }

    private ManagerAuthService createManagerService() {
        return new ManagerAuthService(createManagerRepository());
    }

    private ChatBotService createChatBotService() {
        return new ChatBotService(createCarRepository());
    }



    //Controllers

    private ICarController createCarController() {
        return new CarController(createCarRepository());
    }


    // Application

    public MyApplication createApplication() {

        return new MyApplication(
                createCarController(),
                createUserService(),
                createChatBotService()
        );
    }



    //Close DB

    public void close() {
        db.close();
    }
}
