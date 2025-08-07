package com.cocktail.coktail_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocktail.coktail_app.models.Cocktail;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    
    List<Cocktail> findByNameContainingIgnoreCase(String name);

    List<Cocktail> findAllByOrderByNameAsc();

    List<Cocktail> findAllByOrderByDateCreationDesc();

}
