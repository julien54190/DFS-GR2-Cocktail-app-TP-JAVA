package com.cocktail.coktail_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocktail.coktail_app.models.Panier;
import com.cocktail.coktail_app.repository.PanierRepository;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    // Recuperer le panier 
    public List<String> getCurrentIngredients() {
        Panier currentPanier = panierRepository.FindFirstByOrderByDateCreationDesc();
        if (currentPanier != null) {
            List<String> ingredients = currentPanier.getIngredients();
            return ingredients.stream()
                .map(ingredient -> ingredient.replaceAll("^\"|\"$", ""))
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    // Ajouter un ingredient au panier
    public void addIngredients(List<String> newIngredients) {
        List<String> currentIngredients = new ArrayList<>(getCurrentIngredients());
        currentIngredients.addAll(newIngredients);

        //creer un nouveau panier avec les ingredients mis a jour
        Panier newPanier = new Panier(currentIngredients);
        panierRepository.save(newPanier);
    }

        // Vider le panier
        public void clearPanier() {
            Panier emptyPanier = new Panier(new ArrayList<>());
            panierRepository.save(emptyPanier);
        }

        // Supprimer un ingrédient spécifique
        public void removeIngredient(String ingredient) {
            List<String> currentIngredients = new ArrayList<>(getCurrentIngredients());
            currentIngredients.remove(ingredient);
            
            Panier updatedPanier = new Panier(currentIngredients);
            panierRepository.save(updatedPanier);
    }
}
