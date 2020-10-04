package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.action.FindByNameAction;
import ru.job4j.tracker.input.StubInput;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.storage.MemTracker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FindByNameActionTest {

    @Test
    public void whenFindByNameAction() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream standart = System.out;
        System.setOut(new PrintStream(out));
        MemTracker tracker = new MemTracker();
        Item item = new Item("new item");
        tracker.add(item);
        FindByNameAction action = new FindByNameAction();
        action.execute(new StubInput(new String[]{"new item"}), tracker);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add(item.toString())
                .toString();
        assertThat(out.toString(), is(expect));
        System.setOut(standart);
    }
}
