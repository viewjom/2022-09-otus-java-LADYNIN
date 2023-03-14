package ru.otus.crm.service;

import java.io.IOException;

public interface KladrServiceClient {

    String getAddresesByZip(String url) throws IOException;

}
