package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ResourcesFileLoader implements Loader {

    private final ObjectMapper mapper = new ObjectMapper();
    private String myJson = "";

    public ResourcesFileLoader(String fileName) throws IOException {

        try (var bufferedReader = new BufferedReader(new FileReader("G:\\otus\\2022-09-otus-java-LADYNIN\\L16-JSON\\src\\test\\resources\\" + fileName))) {
            System.out.println("text from the file:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.myJson += line;
            }
        }
    }
    @Override
    public List<Measurement> load() throws IOException {

        Gson gson = new Gson();
        Type empTypeList = new TypeToken<ArrayList<Measurement>>() {
        }.getType();

        try {

            List<Measurement> list = gson.fromJson(myJson, empTypeList);
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}