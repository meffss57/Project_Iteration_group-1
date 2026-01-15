package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Car;
import com.company.repositories.interfaces.ICarRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements ICarRepository {
    private final IDB db;  // Dependency Injection

    public CarRepository(IDB db) {
        this.db = db;
    }

    /// changed createUser to -> createCar
    @Override
    public boolean createCar(Car car) {
        String sql = "INSERT INTO cars(vin, brand, model, branch_city, year, color, engine_type, engine_volume, mileage, sale_price, status) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, car.getVin());
            st.setString(2, car.getBrand());
            st.setString(3, car.getModel());
            st.setString(4, car.getBranchCity());
            st.setInt(5, car.getYear());
            st.setString(6, car.getColor());
            st.setString(7, car.getEngineType());
            st.setDouble(8, car.getEngineVolume());
            st.setInt(9, car.getMileage());
            st.setDouble(10, car.getSalePrice());
            st.setString(11, car.getStatus());

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return false;
    }

    /// changed getUser to -> getCar
    @Override
    public Car getCar(int id) {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "SELECT car_id, vin, brand, model, branch_city, year, color, engine_type, engine_volume, mileage, sale_price, status FROM cars WHERE car_id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Car(
                        rs.getInt("car_id"),
                        rs.getString("vin"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("branch_city"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        rs.getString("engine_type"),
                        rs.getDouble("engine_volume"),
                        rs.getInt("mileage"),
                        rs.getDouble("sale_price"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return null;
    }

    /// changed getAllUser to -> getAllCars
    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT car_id, vin, brand, model, branch_city, year, color, engine_type, engine_volume, mileage, sale_price, status FROM cars"
             )) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("car_id"),
                        rs.getString("vin"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("branch_city"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        rs.getString("engine_type"),
                        rs.getDouble("engine_volume"),
                        rs.getInt("mileage"),
                        rs.getDouble("sale_price"),
                        rs.getString("status")
                );
                cars.add(car);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return cars;
    }
