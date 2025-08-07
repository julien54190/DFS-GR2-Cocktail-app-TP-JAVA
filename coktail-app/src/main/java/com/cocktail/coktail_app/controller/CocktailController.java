package com.cocktail.coktail_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.service.CocktailService;



@RestController
@RequestMapping("api/cocktails")
@CrossOrigin(origins = "*")
public class CocktailController {

    @Autowired
    private CocktailService cocktailService;

    // Recuperer tous les cocktails
    @GetMapping
    public ResponseEntity<List<Cocktail>> getAllCocktails() {
        List<Cocktail> cocktails = cocktailService.getAllCocktails();
        return ResponseEntity.ok(cocktails);
    }

    // Recuperer un cocktail par son id
        @GetMapping("/{id:\\d+}")
    public ResponseEntity<Cocktail> getCocktailById(@PathVariable Long id) {
        Optional<Cocktail> cocktail = cocktailService.getCocktailById(id);
        return cocktail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Creer un cocktail
    @PostMapping
        public ResponseEntity<Cocktail> createCocktail(@RequestBody Cocktail cocktail) {
            Cocktail createdCocktail = cocktailService.createCocktail(cocktail);
            return ResponseEntity.ok(createdCocktail);
    }

    // Mettre a jour un cocktail
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<Cocktail> updateCocktail(@PathVariable Long id, @RequestBody Cocktail cocktailDetails) {
        Cocktail updateCocktail = cocktailService.updateCocktail(id, cocktailDetails);
        if (updateCocktail != null) {
            return ResponseEntity.ok(updateCocktail);
        } 
        return ResponseEntity.notFound().build();
    }
    // Supprimer un cocktail
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteCocktail(@PathVariable Long id) {
        boolean isDeleted = cocktailService.deleteCocktail(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
