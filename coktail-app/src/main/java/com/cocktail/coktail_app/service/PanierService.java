package com.cocktail.coktail_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.models.Panier;
import com.cocktail.coktail_app.repository.CocktailRepository;
import com.cocktail.coktail_app.repository.PanierRepository;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;
    
    @Autowired
    private CocktailRepository cocktailRepository;

    // Recuperer le panier 
    public List<String> getCurrentIngredients() {
        try {
            System.out.println("=== PanierService.getCurrentIngredients() appelé ===");
            
            Panier currentPanier = panierRepository.findFirstByOrderByDateCreationDesc();
            if (currentPanier != null) {
                List<String> ingredients = currentPanier.getIngredients();
                System.out.println("=== Ingrédients trouvés: " + ingredients + " ===");
                
                List<String> cleanedIngredients = ingredients.stream()
                    .map(ingredient -> ingredient.replaceAll("^\"|\"$", ""))
                    .collect(Collectors.toList());
                
                System.out.println("=== Ingrédients nettoyés: " + cleanedIngredients + " ===");
                return cleanedIngredients;
            }
            
            System.out.println("=== Aucun panier trouvé, retour d'une liste vide ===");
            return new ArrayList<>();
            
        } catch (Exception e) {
            System.out.println("=== Erreur dans getCurrentIngredients: " + e.getMessage() + " ===");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    // Ajouter un ingredient au panier
    public void addIngredient(String ingredient) {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        addIngredients(ingredients);
    }

    // Ajouter plusieurs ingredients au panier
    public void addIngredients(List<String> ingredients) {
        try {
            System.out.println("=== PanierService.addIngredients() appelé avec: " + ingredients + " ===");
            
            List<String> currentIngredients = new ArrayList<>(getCurrentIngredients());
            currentIngredients.addAll(ingredients);

            Panier updatedPanier = new Panier(currentIngredients);
            panierRepository.save(updatedPanier);
            
            System.out.println("=== Panier mis à jour avec succès ===");
            
        } catch (Exception e) {
            System.out.println("=== Erreur dans addIngredients: " + e.getMessage() + " ===");
            e.printStackTrace();
        }
    }

    //     //creer un nouveau panier avec les ingredients mis a jour
    //     Panier newPanier = new Panier(currentIngredients);
    //     panierRepository.save(newPanier);
    // }

        // Vider le panier
        public void clearPanier() {
            try {
                System.out.println("=== PanierService.clearPanier() appelé ===");
                
                Panier emptyPanier = new Panier(new ArrayList<>());
                panierRepository.save(emptyPanier);
                
                System.out.println("=== Panier vidé avec succès ===");
                
            } catch (Exception e) {
                System.out.println("=== Erreur dans clearPanier: " + e.getMessage() + " ===");
                e.printStackTrace();
            }
        }

        // Supprimer un ingrédient spécifique
        public void removeIngredient(String ingredient) {
            System.out.println("=== PanierService.removeIngredient() appelé avec ingredient: " + ingredient + " ===");
            
            List<String> currentIngredients = new ArrayList<>(getCurrentIngredients());
            System.out.println("=== Ingrédients actuels: " + currentIngredients + " ===");
            
            boolean removed = currentIngredients.remove(ingredient);
            System.out.println("=== Ingrédient supprimé: " + removed + " ===");
            System.out.println("=== Ingrédients après suppression: " + currentIngredients + " ===");
            
            Panier updatedPanier = new Panier(currentIngredients);
            panierRepository.save(updatedPanier);
            System.out.println("=== Panier mis à jour avec succès ===");
        }
        
        // Ajouter les ingrédients d'un cocktail au panier
        public boolean addCocktailIngredients(Long cocktailId) {
            System.out.println("=== PanierService.addCocktailIngredients() appelé avec cocktailId: " + cocktailId + " ===");
            
            try {
                // Récupérer le cocktail
                Cocktail cocktail = cocktailRepository.findById(cocktailId).orElse(null);
                if (cocktail == null) {
                    System.out.println("=== Cocktail non trouvé ===");
                    return false;
                }
                
                System.out.println("=== Cocktail trouvé: " + cocktail.getName() + " ===");
                
                // Récupérer les ingrédients actuels du panier
                List<String> currentIngredients = new ArrayList<>(getCurrentIngredients());
                
                // Ajouter les ingrédients du cocktail
                List<String> cocktailIngredients = cocktail.getIngredients();
                currentIngredients.addAll(cocktailIngredients);
                
                System.out.println("=== Ingrédients ajoutés: " + cocktailIngredients + " ===");
                System.out.println("=== Total ingrédients dans le panier: " + currentIngredients.size() + " ===");
                
                // Sauvegarder le nouveau panier
                Panier updatedPanier = new Panier(currentIngredients);
                panierRepository.save(updatedPanier);
                
                System.out.println("=== Panier mis à jour avec succès ===");
                return true;
                
            } catch (Exception e) {
                System.out.println("=== Erreur lors de l'ajout au panier: " + e.getMessage() + " ===");
                e.printStackTrace();
                return false;
            }
        }
}
