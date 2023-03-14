package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.cachehw.MyCache;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.KladrServiceClientImpl;
import ru.otus.exceptions.SaveClientException;
import ru.otus.gost.UsageSample;
import ru.otus.json.dataprocessor.ProcessorImpl;
import ru.otus.json.model.Result;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final DBServiceClient dbServiceClient;
    private int lastZip = 0;
    private List<Result> result;
    ProcessorImpl jsonProcessor = new ProcessorImpl();
    KladrServiceClientImpl kladrServiceClient = new KladrServiceClientImpl();
    //UsageSample usageSample = new UsageSample();
    //private UsageSample usageSample;
    MyCache<String, List<Result>> cache = new MyCache<>();
    //private final RabbitMqService rabbitMqService;


    @Value("${kladr.url}")
    private String kladr_url;


    public ClientController(DBServiceClient dbServiceClient/*, RabbitMqService rabbitMqService*/) {

        this.dbServiceClient = dbServiceClient;
      //  this.rabbitMqService = rabbitMqService;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @GetMapping("/client/file/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id, Model model) {

        var doc = dbServiceClient.loadDocument(id);
        var byteFile = doc.getFile();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + doc.getFileName()
        );
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");


        ByteArrayResource resource = new ByteArrayResource(byteFile);

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(byteFile.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new Client(null, null, null, null, null));
        return "clientCreate";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(String name, String number, String guid, MultipartFile[] fils) {

        try {
            if (guid == null) {
                throw new SaveClientException("clientSave");
            }

          //  List<Result> result;

            if (cache.containsKey(String.valueOf(lastZip))) {
               result = cache.get(String.valueOf(lastZip));

                //-----------------------
               return dbServiceClient.saveClient(result, name, number, guid, fils);
/*
                for (Result res : result) {


                    if (res.getGuid().equals(guid)) {
                        var receivedZip = res.getZip();
                        var receivedStreet = jsonProcessor.getParentInfo(res.getParents());
                        var receivedHouse = res.getName();

                        Set<Document> setDocument = new HashSet<>();
                        var docId = dbServiceClient.getLastId();


                        for (MultipartFile file : fils) {
                            docId++;

                            byte[] fileBytes = file.getBytes();

                            setDocument.add(new Document(docId, file.getOriginalFilename(), fileBytes, "hash was not generated", null));
                        }

                        dbServiceClient.saveClient(
                                new Client(null, name,
                                        new Address(receivedZip, receivedStreet, receivedHouse, guid, null),
                                        Set.of(new Phone(null, number, null)),
                                        setDocument));

                        setDocument.stream().forEach(s -> rabbitMqService.sendDocumentHashEvent(s.getId()));

                        return new RedirectView("/", true);
                    }
                }

 */
              //---------------

            }
        } catch (SaveClientException e) {
            logger.info("{} Cохранение пустого клиента", e.toString());
        }
        return new RedirectView("/client/create", true);
    }

    @GetMapping("/client/kladr")
    public String getKladr(int zip, String street, String house, Model model) throws IOException {

        if (cache.containsKey(String.valueOf(zip))) {
            result = cache.get(String.valueOf(zip));

        } else {

            result = jsonProcessor.getListResult(kladrServiceClient.getAddresesByZip(kladr_url + zip));

            if (!cache.containsKey(String.valueOf(zip))) {
                cache.put(String.valueOf(zip), result);
            }
        }

        if (!street.isEmpty() || !house.isEmpty()) {
            result = jsonProcessor.processF(result, street, house);
        }

        model.addAttribute("client", new

                Client(null, null, null, null, null));
        model.addAttribute("addresses", jsonProcessor.getAddreses(result));

        lastZip = zip;
        return "clientCreate";

    }

    @PostMapping("/client/hash")
    public String getHush(MultipartFile file2, Model model) throws IOException, SQLException, NoSuchAlgorithmException, NoSuchProviderException {
        UsageSample usageSample = new UsageSample();
        byte[] bytes = file2.getBytes();

        if (cache.containsKey(String.valueOf(lastZip)) && lastZip > 0) {
            var result = cache.get(String.valueOf(lastZip));
            model.addAttribute("addresses", jsonProcessor.getAddreses(result));
        }

        model.addAttribute("client", new Client(null, null, null, null, null));
        model.addAttribute("hash", usageSample.getHex(bytes));

        return "clientCreate";
    }
}