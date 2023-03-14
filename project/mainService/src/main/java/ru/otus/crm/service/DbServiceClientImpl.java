package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.*;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.json.dataprocessor.ProcessorImpl;
import ru.otus.json.model.Result;
import ru.otus.kafka.DataSender;
import ru.otus.kafka.MyProducer;
import ru.otus.kafka.StringValueSource;
import ru.otus.sessionmanager.TransactionManager;

import java.io.IOException;
import java.util.*;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;
    private ProcessorImpl jsonProcessor;
    private RabbitMqServiceImpl rabbitMqService;

    MyProducer producer = new MyProducer("192.168.10.173:9092");
    DataSender dataSender = new DataSender(producer, stringValue -> log.info("asked, value:{}", stringValue));
    StringValueSource valueSource = new StringValueSource(dataSender::dataHandler);


    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository, RabbitMqServiceImpl rabbitMqService) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
        this.rabbitMqService = rabbitMqService;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);

            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public RedirectView saveClient(List<Result> result, String name, String number, String guid, MultipartFile[] fils) {


        for (Result res : result) {


            if (res.getGuid().equals(guid)) {
                var receivedZip = res.getZip();
                var receivedStreet = jsonProcessor.getParentInfo(res.getParents());
                var receivedHouse = res.getName();

                Set<Document> setDocument = new HashSet<>();
                var docId = getLastId();


                for (MultipartFile file : fils) {
                    docId++;

                    byte[] fileBytes = new byte[0];
                    try {
                        fileBytes = file.getBytes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    setDocument.add(new Document(docId, file.getOriginalFilename(), fileBytes, "hash512 was not generated", "hash256 was not generated", null));
                }

                saveClient(
                        new Client(null, name,
                                new Address(receivedZip, receivedStreet, receivedHouse, guid, null),
                                Set.of(new Phone(null, number, null)),
                                setDocument));

                setDocument.stream().forEach(s -> {
                    rabbitMqService.sendDocumentHashEvent(s.getId());
                    valueSource.accept(s.getId(), "StribogB256");
                });
                //   setDocument.stream().forEach(s->valueSource.accept(s.getId(),"StribogB256")); //KAFKA

                return new RedirectView("/", true);
            }
        }
        return new RedirectView("/client/create", true);
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();

        clientRepository.findAll().forEach(clientList::add);

        log.info("clientList:{}", clientList);
        return clientList;
    }

    @Override
    public Document loadDocument(Long id) {
        try {
            String SQL = "select * from documents where id = ?";

            Document document = jdbcTemplateObject.queryForObject(SQL, new DocumentMapper(), new Object[]{id});
            //log.info("loadDocument Id:{}, fileName:{} ", id, document.getFileName());

            return document;

        } catch (EmptyResultDataAccessException e) {
            log.debug("No record found in database for id{}", id);
        }

        return null;
    }

    @Override
    public Long getLastId() {

        String SQL = "select max(id) from documents";

        Long lastId = jdbcTemplateObject.queryForObject(SQL, Long.class);

        lastId = lastId == null ? 0L : lastId;
        log.info("getLastId Id:{}", lastId);

        return lastId;
    }

    @Override
    public void updateDocument(Long id, String hash) {

        String SQL = "update documents set hash512 = ? where id = ?";

        jdbcTemplateObject.update(SQL, hash, id);

        log.info("updateDocument Id:{} ", id);
    }
}