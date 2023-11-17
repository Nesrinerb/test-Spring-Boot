package com.rabianesrine.testspringboot.services;

import com.rabianesrine.testspringboot.entities.Client;
import com.rabianesrine.testspringboot.repositories.ClientRepository;
import com.rabianesrine.testspringboot.responses.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> getAllClient(){return repository.findAll();}

    public Client getClientById(Long id){
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()){
            return optionalClient.get();
        }else{
            throw new MessageNotFoundException("Client not found ...");
        }
    }

    public  Client addNewClient(Client client){
        var exist = repository.existsByEmail(client.getEmail());
        System.out.println("test !!");
        if (exist){
            return repository.save(client);
        }
        else {
            return null;
        }

    }

    public  Client updateClientById(Long id, Client client){
        client.setId(id);
        return repository.save(client);
    }

    public void deleteClientByID(Long id){
        repository.deleteById(id);
    }
}
