package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    //private final String fileName;
    //private List<Measurement> list = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Measurement> readJson = null;
    private String myJson = "";

    public ResourcesFileLoader(String fileName) throws IOException {
        try (var bufferedReader = new BufferedReader(new FileReader("G:\\otus\\2022-09-otus-java-LADYNIN\\L16-JSON\\src\\test\\resources\\" + fileName))) {
            System.out.println("text from the file:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);

                this.myJson = line;

            }
        }
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат

        // System.out.println("rrrrrrrrr %" + myJson+"%");

         //readJson.add(mapper.readValue(myJson, new TypeReference<>(){}));

     //   for(List<Measurement> s : mapper.readValue(myJson, Measurement.class))

    /*    for (Measurement name : readJson) {
            System.out.print(name.getName());

        }
*/

      /*  ObjectReader reader = mapper.readerForListOf(Measurement.class);
        var list = new ArrayList<Measurement>();
        for (ObjectReader jsonNode : reader) {

            jsonNode.getAttributes()
            System.out.println("mapper: " +mapper.treeToValue(jsonNode, Measurement.class).getName());
            //list.add(mapper.treeToValue(jsonNode, Measurement.class));
        }
*/
      //  System.out.println("Размер  JSON" +readJson.size());
        return null;//list;
    }
}