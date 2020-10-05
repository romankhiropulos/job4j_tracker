package ru.job4j.tracker.storage;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public SqlTracker() {
        init();
    }

    public void init() {
        try (InputStream in = SqlTracker.class
                .getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement ps = cn
                .prepareStatement("INSERT INTO items (0name) VALUES (?)")) {
            ps.setString(1, item.getName());
            ps.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) {

        return false;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement ps = cn
                .prepareStatement("SELECT * FROM items ORDER BY name, id")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new Item(String.valueOf(rs.getInt("id")), rs.getString("name")));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        return null;
    }

    @Override
    public Item findById(String id) {
        return null;
    }
}
