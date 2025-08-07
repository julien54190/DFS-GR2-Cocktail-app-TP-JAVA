package com.cocktail.coktail_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocktail.coktail_app.models.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    
    Panier findFirstByOrderByDateCreationDesc();

    List<Panier> findAllByOrderByDateCreationDesc();
}
