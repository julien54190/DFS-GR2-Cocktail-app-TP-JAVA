package com.cocktail.coktail_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.coktail_app.service.PanierService;

@RestController
@RequestMapping("api/panier")
@CrossOrigin(origins = "*")
public class PanierController {

    @Autowired
    private PanierService panierService;

    //  Récupérer les ingrédients du panier
    @GetMapping("/ingredients")
    public ResponseEntity<List<String>> getIngredients() {
        List<String> ingredients = panierService.getCurrentIngredients();
        return ResponseEntity.ok(ingredients);
    }
    
    // Ajouter des ingrédients au panier
    @PostMapping("/ingredients")
    public ResponseEntity<String> addIngredients(@RequestBody List<String> ingredients) {
        panierService.addIngredients(ingredients);
        return ResponseEntity.ok("Ingrédients ajoutés au panier");
    }
    
    // Ajouter un seul ingrédient
    @PostMapping("/ingredient")
    public ResponseEntity<String> addIngredient(@RequestBody String ingredient) {
        // Nettoyer la chaîne JSON si nécessaire
        String cleanIngredient = ingredient.replaceAll("^\"|\"$", "");
        panierService.addIngredients(List.of(cleanIngredient));
        return ResponseEntity.ok("Ingrédient ajouté au panier");
    }
    
    //  Vider le panier
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearPanier() {
        panierService.clearPanier();
        return ResponseEntity.ok("Panier vidé");
    }
    
    // Supprimer un ingrédient spécifique
    @DeleteMapping("/ingredient/{ingredient}")
    public ResponseEntity<String> removeIngredient(@PathVariable String ingredient) {
        panierService.removeIngredient(ingredient);
        return ResponseEntity.ok("Ingrédient supprimé du panier");
    }
    

}
