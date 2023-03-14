package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class KladrServiceClientImpl implements KladrServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(KladrServiceClientImpl.class);
    @Value("${kladr.url}")
    private String kladr_url;

    public KladrServiceClientImpl() {

    }

    @Override
    public String getAddresesByZip(String url) throws IOException {

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

            httpClient.setRequestMethod("GET");

        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = httpClient.getResponseCode();

        logger.info("Запрос: {}", url);

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;


            while (true) {
                try {
                    if (!((line = in.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                response.append(line);
            }

            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
