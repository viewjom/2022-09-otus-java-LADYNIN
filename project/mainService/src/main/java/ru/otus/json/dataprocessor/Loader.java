package ru.otus.json.dataprocessor;

import ru.otus.json.model.Measurement;

import java.util.List;

public interface Loader {
    List<Measurement> load() ;
}