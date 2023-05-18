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
import ru.otus.crm.service.ServiceGost3411;
import ru.otus.exceptions.SaveClientException;
import ru.otus.json.dataprocessor.ProcessorImpl;
import ru.otus.json.model.Result;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private DBServiceClient dbServiceClient;
    private int lastZip = 0;
    private List<Result> result;
    ProcessorImpl jsonProcessor = new ProcessorImpl();
    KladrServiceClientImpl kladrServiceClient = new KladrServiceClientImpl();
    static MyCache<String, List<Result>> cache = new MyCache<>();


    @Value("${kladr.url}")
    private String kladr_url;


    public ClientController(DBServiceClient dbServiceClient) {

        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @GetMapping("/client/file/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id, Model model) {

        var byteFile = dbServiceClient.loadDocument(id).getFile();
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dbServiceClient.loadDocument(id).getFileName()
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

            if (cache.containsKey(String.valueOf(lastZip))) {
                result = cache.get(String.valueOf(lastZip));

                return dbServiceClient.saveClient(result, name, number, guid, fils);
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
            //result = jsonProcessor.getListResult(kladrServiceClient.getAddresesByZip("https://kladr-api.ru/api.php?contentType=building&withParent=1&zip=" + zip));

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
        ServiceGost3411 usageSample = new ServiceGost3411();
        byte[] bytes = file2.getBytes();

        model.addAttribute("client", new Client(null, null, null, null, null));
        model.addAttribute("hash", usageSample.getHex(bytes));

        return "clientCreate";
    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}