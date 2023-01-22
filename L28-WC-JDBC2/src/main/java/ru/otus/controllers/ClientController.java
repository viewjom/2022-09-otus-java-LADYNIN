package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;
import java.util.Set;

@Controller
public class ClientController {


    private final DBServiceClient dbServiceClient;

    public ClientController(DBServiceClient dbServiceClient) {

        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new Client(null, null, null, null));
        return "clientCreate";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(String name, String street, String number) {

        dbServiceClient.saveClient(new Client(null, name, new Address(null, street, null), Set.of(new Phone(null, number, null))));

        return new RedirectView("/", true);
    }

}
