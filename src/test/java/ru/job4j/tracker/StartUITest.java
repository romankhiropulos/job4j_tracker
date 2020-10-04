package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.action.StubAction;
import ru.job4j.tracker.action.UserAction;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.StubInput;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.storage.MemTracker;
import ru.job4j.tracker.storage.Store;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class StartUITest {
    @Test
    public void whenAddItem() {
        String[] answers = {"Fix PC"};
        Input input = new StubInput(answers);
        Store tracker = new MemTracker();
        StartUI.createItem(input, tracker);
        Item created = tracker.findAll().get(0);
        Item expected = new Item("Fix PC");
        assertThat(created.getName(), is(expected.getName()));
    }

    @Test
    public void whenReplaceItem() {
        Store tracker = new MemTracker();
        Item item = new Item("new item");
        tracker.add(item);
        String[] answer = {item.getId(), "replaced item"};
        StartUI.editItem(new StubInput(answer), tracker);
        Item replaced = tracker.findById(item.getId());
        assertThat(replaced.getName(), is("replaced item"));
    }

    @Test
    public void whenDeleteItem() {
        Store tracker = new MemTracker();
        Item item = new Item("new item");
        tracker.add(item);
        String[] answer = {item.getId()};
        StartUI.deleteItem(new StubInput(answer), tracker);
        assertThat(tracker.findById(item.getId()), is(nullValue()));
    }

    @Test
    public void whenStartUIInit1() {
        StubInput input = new StubInput(new String[]{"0"});
        List<UserAction> actions = new ArrayList<>();
        StubAction action = new StubAction();
        actions.add(action);
        new StartUI().init(input, new MemTracker(), actions);
        assertThat(action.isCall(), is(true));
    }

    @Test
    public void whenStartUIInit2() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream standart = System.out;
        System.setOut(new PrintStream(out));
        List<UserAction> actions = new ArrayList<>();
        StubAction action = new StubAction();
        actions.add(action);
        new StartUI().init(new StubInput(new String[]{"0"}), new MemTracker(), actions);
        String expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("\n---------Menu---------")
                .add("0. Stub action")
                .add("----------------------")
                .toString();
        assertThat(out.toString(), is(expected));
        System.setOut(standart);
    }
}
