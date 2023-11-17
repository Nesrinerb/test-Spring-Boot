package com.rabianesrine.testspringboot.controllers;

import com.rabianesrine.testspringboot.entities.Commande;
import com.rabianesrine.testspringboot.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v3")
public class CommandeController {
    @Autowired
    private CommandeService service;


    @GetMapping(value ="/commandes")
    public List<Commande> getOrders(){ return service.getAllCommandes();}

    @GetMapping(value = "/commande/{commandeId}")
    public Commande getOrder(@PathVariable("commandeId") Long id){return service.getCommandeById(id);}

    @PostMapping
    public Commande addOrder(@RequestBody Commande
                             commande
    ){return  service.addNewCommande(commande);}

    @PutMapping(value = "/commande/{commandeId}")
    public Commande updateOrder(@PathVariable("commandeId") Long id, @RequestBody Commande commande){return service.updateCommandById(id, commande);}

    @DeleteMapping(value = "/commande/{commandeId}")
    public void deleteProduct(@PathVariable("commandeId") Long id) { service.deleteCommandeByID(id);}
}
