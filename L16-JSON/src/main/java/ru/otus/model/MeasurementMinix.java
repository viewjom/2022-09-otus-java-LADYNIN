package ru.otus.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class MeasurementMinix {

    @JsonCreator
    public MeasurementMinix(
            @JsonProperty("name") String name,
            @JsonProperty("value") double value) {
    }

}
