package com.rabianesrine.testspringboot.controllers;

import com.rabianesrine.testspringboot.entities.Produit;
import com.rabianesrine.testspringboot.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v2")
public class ProduitController {


    @Autowired
    private ProduitService service;


    @GetMapping(value ="/produits")
    public List<Produit> getProducts(){ return service.getAllProduct();}

    @GetMapping(value = "/produit/produitId")
    public Produit getProduct(@PathVariable("produitId") Long id){return service.getProductById(id);}

    @PostMapping
    public Produit addProduct(@RequestBody Produit produit){return  service.addNewProduct(produit);}

    @PutMapping(value = "/produit/{produitId}")
    public Produit updateProduct(@PathVariable("produitId") Long id, @RequestBody Produit produit){return service.updateProductById(id, produit);}

    @DeleteMapping(value = "/produit/{produitId}")
    public void deleteProduct(@PathVariable("produitId") Long id) { service.deleteProductByID(id);}
}
