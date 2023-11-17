package com.rabianesrine.testspringboot.repositories;

import com.rabianesrine.testspringboot.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {
}
