package com.rabianesrine.testspringboot.services;

import com.rabianesrine.testspringboot.entities.Client;
import com.rabianesrine.testspringboot.entities.Commande;
import com.rabianesrine.testspringboot.entities.Produit;
import com.rabianesrine.testspringboot.repositories.CommandeRepository;
import com.rabianesrine.testspringboot.responses.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository repository;

    public List<Commande> getAllCommandes(){return repository.findAll();}

    public Commande getCommandeById(Long id){
        Optional<Commande> optionalCommande = repository.findById(id);
        if (optionalCommande.isPresent()){
            return optionalCommande.get();
        }else{
            throw new MessageNotFoundException("Order not found ...");
        }
    }

    public  Commande addNewCommande(Commande commande){
            return repository.save(commande);
    }

    public  Commande updateCommandById(Long id, Commande commande){
        commande.setId(id);
        return repository.save(commande);
    }

    public void deleteCommandeByID(Long id){
        repository.deleteById(id);
    }

    @Autowired
    private ProduitService produitService;

    public Commande passerCommande(List<Produit> produits) {
        Commande commande = new Commande();
        commande.setProduits(new ArrayList<>());


        commande= repository.save(commande);


        for (Produit produit : produits) {
            produit.setCommande(commande);
            produitService.diminuerQuantite(produit.getId(), produit.getQuantité());
        }

        // Ajouter les produits à la commande
        commande.getProduits().addAll(produits);

        return commande;
    }
}
