package ru.otus.json.dataprocessor;

import ru.otus.cachehw.HwCache;
import ru.otus.crm.model.Address;
import ru.otus.json.model.Fias;
import ru.otus.json.model.Result;

import java.util.List;

public interface Processor {

    List<Result> processF(List<Result> data, String street, String house);

    long getCount(List<Result> data);

    public List<Address> getAddreses(List<Result> json);

    public List<Result> getListResult(String json/*, String street, String house*/);
    }