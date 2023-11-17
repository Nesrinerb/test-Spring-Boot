package com.rabianesrine.testspringboot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Commande {

    @Id
    @GeneratedValue
    private Long id;
    private Long prixTotalDeVente;

    @OneToOne
    @JoinColumn(name ="client_id")
    private Client client;


    @OneToMany(mappedBy = "commande")
    private List<Produit> produits;
}
