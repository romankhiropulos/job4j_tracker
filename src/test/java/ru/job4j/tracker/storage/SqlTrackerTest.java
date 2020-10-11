package ru.job4j.tracker.storage;

import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.util.ConnectionRollback;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SqlTrackerTest {
    public Connection init() {
        try (InputStream in = SqlTracker.class
                .getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSizeOne() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("itemID", "desc"));
            assertThat(tracker.findByName("desc").size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("itemID", "desc"));
            Item matcher = new Item("itemID", "desc");
            Item itemDB = tracker.findByName("desc").get(0);
            assertThat(itemDB, is(matcher));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenReplaceItemThenGetSameItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item oldItem = new Item("itemID", "desc");
            Item newItem = new Item("itemID", "newDesc");
            tracker.add(oldItem);
            tracker.replace("itemID", newItem);
            assertThat(tracker.findById("itemID"), is(newItem));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteItemThenHasSizeZero() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("itemID", "desc"));
            assertThat(tracker.findByName("desc").size(), is(1));
            tracker.delete("itemID");
            assertThat(tracker.findByName("desc").size(), is(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindAllItemThenGetSameSize() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("itemID_1", "desc1"));
            tracker.add(new Item("itemID_2", "desc2"));
            tracker.add(new Item("itemID_3", "desc3"));
            assertThat(tracker.findAll().size(), is(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByNameRomanThenGetSameItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("itemID", "Roman");
            tracker.add(item);
            assertThat(tracker.findByName("Roman").get(0), is(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByIdThenGetSameItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("itemID", "Roman");
            tracker.add(item);
            assertThat(tracker.findById("itemID"), is(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}