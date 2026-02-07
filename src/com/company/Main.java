package com.company;

import com.company.factory.AppFactory;

public class Main {

    public static void main(String[] args) {

        AppFactory factory = AppFactory.create();

        MyApplication app = factory.createApplication();

        app.start();

        factory.close();
    }
}
