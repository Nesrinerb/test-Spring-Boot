package com.rabianesrine.testspringboot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produit {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String description;
    private Long quantité;
    private Long prix;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

}
