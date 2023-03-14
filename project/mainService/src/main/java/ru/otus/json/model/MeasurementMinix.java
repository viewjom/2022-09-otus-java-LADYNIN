package ru.otus.json.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class MeasurementMinix {

    @JsonCreator
    public MeasurementMinix(
            @JsonProperty("result") String name) {
    }

}
