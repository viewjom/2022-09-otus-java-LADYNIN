package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final List<Message> history = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {

        history.add((Message) msg.copy());

        //throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {

        Optional<Message> optional = history.stream().filter(i -> i.getId() == id).findAny();

        if (optional.isPresent()) {

            return optional;
        } else {
            throw new UnsupportedOperationException();
        }

    }

    public List<Message> gethistory() {
        return history;
    }
}