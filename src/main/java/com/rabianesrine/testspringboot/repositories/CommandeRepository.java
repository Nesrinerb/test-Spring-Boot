package com.rabianesrine.testspringboot.repositories;

import com.rabianesrine.testspringboot.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long> {
}
