package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {
    Map<String, Double> grJson = new HashMap<>();
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value

        grJson = data.stream().collect(Collectors.groupingBy(Measurement::getName, Collectors.summingDouble(Measurement::getValue)));


        System.out.println("Печатаем groupingBy "+  grJson.size());
        for(Map.Entry<String, Double> item : grJson.entrySet()){

            System.out.println("tttttttt "+ item.getKey() + " - " + item.getValue());
        }

        return grJson;
    }
}