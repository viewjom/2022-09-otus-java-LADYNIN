package ru.otus.json.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.crm.model.Address;
import ru.otus.json.model.Fias;
import ru.otus.json.model.Parents;
import ru.otus.json.model.Result;

import java.util.ArrayList;
import java.util.List;

public class ProcessorImpl implements Processor {
    ObjectMapper mapper = new ObjectMapper();





    @Override
    public List<Result> processF(List<Result> data, String street, String house) {

        if (street.isEmpty() && house.isEmpty()) {
            return data;
        }

        List<Result> grJson = data.stream().filter(
                s -> {
                    if (!street.isEmpty() && !house.isEmpty()) {
                        return s.getParents().toString().contains(street) && s.getName().equalsIgnoreCase(house);
                    }
                    if (!street.isEmpty()) {
                        return s.getParents().toString().contains(street);
                    }
                    return s.getName().equalsIgnoreCase(house);
                }
        ).toList();


        return grJson;
    }

    @Override
    public long getCount(List<Result> data) {
        return data.stream().count();
    }


    @Override
    public List<Result> getListResult(String json/*, String street, String house*/) {

        Result result;
        List<Result> fias = null;
        try {
            fias = mapper.readValue(json, Fias.class).getResult();
/*
            if (cache != null) {
                cache.put(String.valueOf(zip), fias);
            }
*/
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

   /*     if (!street.isEmpty() && !house.isEmpty()) {
            fias = processF(fias, street, house);
        }
*/
        return fias;
    }


    public void printAddreses(List<Result> resultLst) {

        for (Result result : resultLst) {

            System.out.println(result.getZip() + " " +
                    // result.getParents().stream().map(s -> s.getName()).toList() + " " + // result.getParents() + " " +
                    getParentInfo(result.getParents()) + " " +
                    result.getTypeShort() + "." + result.getName());

        }
    }

    public List<Address> getAddreses(List<Result> resultLst) {

        List<Address> addresses = new ArrayList<>();


        for (Result result : resultLst) {

        var address =  new Address(result.getZip(),getParentInfo(result.getParents()),result.getName(),result.getGuid(),null);



            addresses.add(address);
       //     addresses.add(new Address(result.getZip(),getParentInfo(result.getParents()),result.getName(),null));

        }
        return addresses;
    }

    public static String getParentInfo(List<Parents> parents) {

        String res = "";
        for (Parents parent : parents) {

            res = res + " " + parent.getTypeShort() + "." + parent.getName();
        }
        return res;
    }

}