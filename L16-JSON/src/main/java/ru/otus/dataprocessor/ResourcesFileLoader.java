package ru.otus.dataprocessor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.NoArgsConstructor;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ResourcesFileLoader implements Loader {

//    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {

        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws FileProcessException, JsonProcessingException {
        String myJson = "";
        Gson gson = new Gson();

        URL resource = getClass().getClassLoader().getResource(fileName);
        System.out.println("getResourceAsStream: " + resource.getFile());

        try (var bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            System.out.println("text from the file:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                myJson += line;
            }
        } catch (FileProcessException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



         ObjectMapper mapper = new ObjectMapper();


        //var stringCollectionType = mapper.getTypeFactory().constructCollectionType(List.class, Measurement.class);

        //    List<Measurement> list = mapper.readValue(myJson, new TypeReference<List<Measurement>>(){});

        //List<Measurement> list = mapper.readValue(myJson, stringCollectionType);

        //List<Measurement> list = gson.fromJson(myJson, empTypeList);

        List<Measurement> list = mapper.readValue(myJson,  new TypeReference<List>(){});

        return list;

    }
}