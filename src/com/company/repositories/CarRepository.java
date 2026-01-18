package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Car;
import com.company.repositories.interfaces.ICarRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CarRepository implements ICarRepository {
    private final IDB db;

    public CarRepository(IDB db) {
        this.db = db;
    }

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
            System.out.println("SQL error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public Car getCar(int carId) {
        String sql = "SELECT car_id, vin, brand, model, branch_city, year, color, engine_type, engine_volume, mileage, sale_price, status " +
                "FROM cars WHERE car_id=?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, carId);

            try (ResultSet rs = st.executeQuery()) {
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
                            rs.getString("status")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return null;
    }


    @Override
    public Car buyCar(int carId) {
        String updateSql = "UPDATE cars SET status = 'sold' " +
                "WHERE car_id = ? AND status <> 'sold'";

        String selectSql = "SELECT car_id, vin, brand, model, branch_city, year, color, " +
                "engine_type, engine_volume, mileage, sale_price, status " +
                "FROM cars WHERE car_id = ?";

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);

            int updated;
            try (PreparedStatement up = con.prepareStatement(updateSql)) {
                up.setInt(1, carId);
                updated = up.executeUpdate();
            }

            if (updated == 0) {
                con.rollback();
                return null;
            }

            Car car;
            try (PreparedStatement st = con.prepareStatement(selectSql)) {
                st.setInt(1, carId);
                try (ResultSet rs = st.executeQuery()) {
                    if (!rs.next()) {
                        con.rollback();
                        return null;
                    }

                    car = new Car(
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
                            rs.getString("status") // тут уже будет "sold"
                    );
                }
            }

            con.commit();
            return car;

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT car_id, vin, brand, model, branch_city, year, color, engine_type, engine_volume, mileage, sale_price, status FROM cars";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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


    private List<Car> mapCars(ResultSet rs) throws SQLException {
        List<Car> cars = new ArrayList<>();
        while (rs.next()) {
            cars.add(new Car(
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
            ));
        }
        return cars;
    }

    @Override
    public List<Car> filterByBrand(String car_brand) {
        String sql = "SELECT * FROM cars WHERE LOWER(brand)=LOWER(?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, car_brand);
            try (ResultSet rs = st.executeQuery()) {
                return mapCars(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Car> filterByCity(String car_city) {
        String sql = "SELECT * FROM cars WHERE LOWER(branch_city)=LOWER(?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, car_city);
            try (ResultSet rs = st.executeQuery()) {
                return mapCars(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Car> filterByYear(int car_year) {
        String sql = "SELECT * FROM cars WHERE year=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, car_year);
            try (ResultSet rs = st.executeQuery()) {
                return mapCars(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Car> filterByEngineType(String car_enginetype) {
        String sql = "SELECT * FROM cars WHERE LOWER(engine_type)=LOWER(?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, car_enginetype);
            try (ResultSet rs = st.executeQuery()) {
                return mapCars(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Car> filterByPriceRange(double car_price_low, double car_price_high) {
        String sql = "SELECT * FROM cars WHERE sale_price BETWEEN ? AND ? ORDER BY sale_price";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setDouble(1, car_price_low);
            st.setDouble(2, car_price_high);

            try (ResultSet rs = st.executeQuery()) {
                return mapCars(rs);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Car> FilterCarsByASC() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT car_id, vin, brand, model, branch_city, year, color, engine_type, engine_volume, mileage, sale_price, status FROM cars";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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

        Collections.sort(cars);

        return cars;
    }

    private List<String> fetchDistinct(String sql, String col) {
        List<String> list = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString(col));
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return list;
    }


    @Override
    public List<String> getAvailableBrands() {
        return fetchDistinct("SELECT DISTINCT brand FROM cars ORDER BY brand", "brand");
    }

    @Override
    public List<String> getAvailableCities() {
        return fetchDistinct("SELECT DISTINCT branch_city FROM cars ORDER BY branch_city", "branch_city");
    }

    @Override
    public List<String> getAvailableEngineTypes() {
        return fetchDistinct("SELECT DISTINCT engine_type FROM cars ORDER BY engine_type", "engine_type");
    }

}
