package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл

        Map<String, Double> sortData = data.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, TreeMap::new));

        try (var bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {

            bufferedWriter.write(new ObjectMapper().writeValueAsString(sortData));

        } catch (IOException e) {
            throw new FileProcessException(e);
        }

    }
}