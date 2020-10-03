package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.util.ItemCompare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ItemTest {

    @Test
    public void whenCompareTo() {
        List<Item> items = Arrays.asList(
                new Item("qqq"),
                new Item("www"),
                new Item("eee")
        );
        Collections.sort(items);
        assertThat(items,
                hasItems(
                        new Item("eee"),
                        new Item("qqq"),
                        new Item("www")
                )
        );
    }

    @Test
    public void whenInAscendingOrder() {
        List<Item> expected = Arrays.asList(
                new Item("eee"),
                new Item("qqq"),
                new Item("www")
        );
        List<Item> items = Arrays.asList(
                new Item("qqq"),
                new Item("www"),
                new Item("eee")
        );
        Collections.sort(items, new ItemCompare());
        assertThat(items, is(expected));
    }

    @Test
    public void whenInDescendingOrder() {
        List<Item> expected = Arrays.asList(
                new Item("www"),
                new Item("qqq"),
                new Item("eee")
        );
        List<Item> items = Arrays.asList(
                new Item("qqq"),
                new Item("www"),
                new Item("eee")
        );
        Collections.sort(items, new ItemCompare(true));
        assertThat(items, is(expected));
    }
}
