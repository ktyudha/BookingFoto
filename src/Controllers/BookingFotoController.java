/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.sql.*;
import Models.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ktyudha
 */
public class BookingFotoController {

    List<Category> categories = new ArrayList<>();
    List<Paket> packages = new ArrayList<>();

    List<Location> locations = new ArrayList<>();

    public List<Location> loadLocations() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dhagrafis", "root", "calonmertua2002");
            String sql = "Select * from locations";

            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            
            
            Location placeholderLocation = new Location();
            placeholderLocation.id = 0;
            placeholderLocation.name = "-- Pilih Location --";
            locations.add(placeholderLocation);

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int price = res.getInt("price");

                Location location = new Location();
                location.id = id;
                location.name = name;
                location.price = price;
                locations.add(location);

            }

        } catch (Exception e) {
            System.out.println("Data tidak tersedia");
        }
        return locations;
    }

    public List<Category> loadCategories() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dhagrafis", "root", "calonmertua2002");
            String sql = "Select * from categories";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);

            Category placeholderCategory = new Category();
            placeholderCategory.id = 0;
            placeholderCategory.name = "-- Pilih Category --";
            categories.add(placeholderCategory);

            while (res.next()) {
                String name = res.getString("name");
                int id = res.getInt("id");

                Category category = new Category();
                category.id = id;
                category.name = name;

                categories.add(category);
            }

        } catch (Exception e) {
            System.out.println("Data tidak tersedia");
        }

        return categories;
    }

    public List<Paket> loadPackages(int category_id) {
        packages.clear();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dhagrafis", "root", "calonmertua2002");
            String sql = "Select * from pakets where category_id=" + category_id;

            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);

            Paket placeholderPaket = new Paket();
            placeholderPaket.id = 0;
            placeholderPaket.name = "-- Pilih Paket --";
            packages.add(placeholderPaket);

            while (res.next()) {

                String name = res.getString("name");
                int price = res.getInt("price");
                int id = res.getInt("id");
                String deskripsi = res.getString("deskripsi");

                Paket paket = new Paket();
                paket.id = id;
                paket.name = name;
                paket.price = price;
                paket.deskripsi = deskripsi;
                packages.add(paket);
            }

        } catch (SQLException e) {
            System.out.println("Data tidak tersedia");
        }

        return packages;
    }

    public int calculatePrice(int locationId, int packageId) {
        int pricePaket = 0;
        int priceLocation = 0;
        int priceTotal = 0;

        for (Paket paket : packages) {
            if (paket.id == packageId) {
                pricePaket = paket.price;
                break;
            }
        }

        for (Location location : locations) {
            if (location.id == locationId) {
                priceLocation = location.price;
                break;
            }
        }

        priceTotal = pricePaket + priceLocation;
        return priceTotal;
    }

    public String loadDescPkt(int descID) {
        String desc = "";

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dhagrafis", "root", "calonmertua2002");
            String sql = "Select * from pakets where id=" + descID;

            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);

            while (res.next()) {

                String deskripsi = res.getString("deskripsi");

                desc = deskripsi;
//                Paket paket = new Paket();
//                paket.deskripsi =  deskripsi;

            }

        } catch (SQLException e) {
            System.out.println("Data tidak tersedia");
        }

        return desc;
    }

    public int countOrder(int userID) {
        int rowCount = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dhagrafis", "root", "calonmertua2002");
            String sql = "Select * from orders where user_id=" + userID;

            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            
            while (res.next()) {
                rowCount = res.getRow(); 
            }
            
        } catch (SQLException e) {
            System.out.println("Data tidak tersedia");
        }

        return rowCount;
    }

}
