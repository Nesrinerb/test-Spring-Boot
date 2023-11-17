package com.rabianesrine.testspringboot.controllers;

import com.rabianesrine.testspringboot.entities.Client;
import com.rabianesrine.testspringboot.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ClientController {

    @Autowired
    private ClientService service;


    @GetMapping(value ="/clientes")
    public List<Client> getClients(){ return service.getAllClient();}

    @GetMapping(value = "/cliente/{clientId}")
    public Client getClient(@PathVariable("clientId") Long id){return service.getClientById(id);}

    @PostMapping
    public Client addClient(@RequestBody Client
                                     client
    ){return  service.addNewClient(client);}

    @PutMapping(value = "/cliente/{clientId}")
    public Client updateOrder(@PathVariable("clientId") Long id, @RequestBody Client client){return service.updateClientById(id, client);}

    @DeleteMapping(value = "/cliente/{clientId}")
    public void deleteClient(@PathVariable("clientId") Long id) { service.deleteClientByID(id);}

}
