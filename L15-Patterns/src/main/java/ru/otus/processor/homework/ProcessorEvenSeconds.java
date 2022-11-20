package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSeconds implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSeconds(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {

        var second = dateTimeProvider.getCurrentTime().getSecond();

        if (second % 2 == 0){
            throw new RuntimeException("Четная секунда: " + second);
        }
        return message;
    }
}
