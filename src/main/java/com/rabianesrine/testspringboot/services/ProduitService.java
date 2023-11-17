package com.rabianesrine.testspringboot.services;

import com.rabianesrine.testspringboot.entities.Produit;
import com.rabianesrine.testspringboot.repositories.ProduitRepository;
import com.rabianesrine.testspringboot.responses.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository repository;

    public List<Produit> getAllProduct(){return repository.findAll();}

    public Produit getProductById(Long id){
        Optional<Produit> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }else{
            throw new MessageNotFoundException("Product not found ...");
        }
    }

    public  Produit addNewProduct(Produit produit){
        return repository.save(produit);
    }

    public  Produit updateProductById(Long id, Produit produit){
        produit.setId(id);
        return repository.save(produit);
    }

    public void deleteProductByID(Long id){
        repository.deleteById(id);
    }

    public void diminuerQuantite(Long produitId, Long quantite) {
        Produit produit = repository.findById(produitId)
                .orElseThrow(() -> new MessageNotFoundException("Produit non trouvé avec l'ID: " + produitId));

        long nouvelleQuantite = produit.getQuantité() - quantite;

        if (nouvelleQuantite < 0) {
            throw new RuntimeException("Quantité insuffisante pour le produit: " + produit.getNom());
        }

        produit.setQuantité(nouvelleQuantite);
        repository.save(produit);
    }
}
