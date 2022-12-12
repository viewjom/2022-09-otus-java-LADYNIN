package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import ru.otus.model.Measurement;
import ru.otus.model.MeasurementMinix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Data
public class ResourcesFileLoader implements Loader {

    private final ObjectMapper mapper = new ObjectMapper();

    private final String fileName;

    public ResourcesFileLoader(String fileName) {

        this.fileName = fileName;
    }

    public List<Measurement> load() throws FileProcessException {

        String myJson = "";

        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            System.out.println("text from the file:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                myJson += line;
            }


            mapper.addMixIn(Measurement.class, MeasurementMinix.class);
            List<Measurement> list = mapper.readValue(myJson, new TypeReference<List<Measurement>>() {
            });
            return list;

        } catch (FileProcessException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}