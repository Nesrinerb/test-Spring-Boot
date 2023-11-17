package com.rabianesrine.testspringboot.repositories;

import com.rabianesrine.testspringboot.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
}
