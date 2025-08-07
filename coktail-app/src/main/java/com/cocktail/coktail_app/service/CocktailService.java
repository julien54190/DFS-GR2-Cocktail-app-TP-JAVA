package com.cocktail.coktail_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.repository.CocktailRepository;




@Service
public class CocktailService {

    @Autowired
    private CocktailRepository cocktailRepository;

    // Recuperation des cocktails
    public List<Cocktail> getAllCocktails() {
        return cocktailRepository.findAllByOrderByDateCreationDesc();
    }

    // Recuperation d'un cocktail par son id
    public Optional<Cocktail> getCocktailById(Long id) {
        return cocktailRepository.findById(id);
    }

    // Rechercher un cocktail par son nom
    public List<Cocktail> getAllCocktailsByName(String name) {
        return cocktailRepository.findByNameContainingIgnoreCase(name);
    }

    // Créer un cocktail
    public Cocktail createCocktail(Cocktail cocktail) {
        return cocktailRepository.save(cocktail);
    }

    // Mettre à jour un cocktail
    public Cocktail updateCocktail(Long id, Cocktail cocktailDetails) {
        Optional<Cocktail> optionalCocktail = cocktailRepository.findById(id);
        if (optionalCocktail.isPresent()) {
            Cocktail cocktail = optionalCocktail.get();
            cocktail.setName(cocktailDetails.getName());
            cocktail.setDescription(cocktailDetails.getDescription());
            cocktail.setIngredients(cocktailDetails.getIngredients());
            cocktail.setImageUrl(cocktailDetails.getImageUrl());
            return cocktailRepository.save(cocktail);
        }
        return null;
    }

    // Supprimer un cocktail

    public boolean deleteCocktail(Long id) {
        Optional<Cocktail> optionalCocktail = cocktailRepository.findById(id);
        if (optionalCocktail.isPresent()) {
            cocktailRepository.delete(optionalCocktail.get());
            return true;
        }
        return false;
    }

}
