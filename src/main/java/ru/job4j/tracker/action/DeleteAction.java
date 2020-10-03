package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.storage.Tracker;

public class DeleteAction implements UserAction {
    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        String id = input.askStr("Enter the item id: ");
        if (tracker.delete(id)) {
            System.out.printf("Item (id: %s) successfully deleted\n", id);
        } else {
            System.out.printf("Item (id: %s) deletion failed\n", id);
        }
        return true;
    }
}
