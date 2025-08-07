package com.cocktail.coktail_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.service.CocktailService;
import com.cocktail.coktail_app.service.LikeService;
import com.cocktail.coktail_app.service.PanierService;

@Controller
public class WebController {
    
    @Autowired
    private CocktailService cocktailService;
    
    @Autowired
    private LikeService likeService;
    
    @Autowired
    private PanierService panierService;
    
    // Page d'accueil - Liste des cocktails
    @GetMapping("/")
    public String home(Model model) {
        System.out.println("=== WebController.home() appelé ===");
        List<Cocktail> cocktails = cocktailService.getAllCocktails();
        List<Long> likedIds = likeService.getLikedCocktailIds();
        List<String> panierIngredients = panierService.getCurrentIngredients();
        
        model.addAttribute("cocktails", cocktails);
        model.addAttribute("likedIds", likedIds);
        model.addAttribute("panierIngredients", panierIngredients);
        
        return "index";
    }
    
    // Page de détails d'un cocktail
    @GetMapping("/details/{id}")
    public String cocktailDetails(@PathVariable Long id, Model model) {
        System.out.println("=== WebController.cocktailDetails(" + id + ") appelé ===");
        Cocktail cocktail = cocktailService.getCocktailById(id).orElse(null);
        if (cocktail == null) {
            return "redirect:/";
        }
        
        boolean isLiked = likeService.isCocktailLiked(id);
        List<String> panierIngredients = panierService.getCurrentIngredients();
        
        model.addAttribute("cocktail", cocktail);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("panierIngredients", panierIngredients);

        
        return "cocktail-details";
    }
    
    // Page de création d'un cocktail
    @GetMapping("/nouveau")
    public String newCocktailForm(Model model) {
        System.out.println("=== WebController.newCocktailForm() appelé ===");
        model.addAttribute("cocktail", new Cocktail());
        return "cocktail-form";
    }
    
    // Page d'édition d'un cocktail
    @GetMapping("/modifier/{id}")
    public String editCocktailForm(@PathVariable Long id, Model model) {
        System.out.println("=== WebController.editCocktailForm(" + id + ") appelé ===");
        Cocktail cocktail = cocktailService.getCocktailById(id).orElse(null);
        if (cocktail == null) {
            return "redirect:/";
        }
        
        model.addAttribute("cocktail", cocktail);
        return "cocktail-form";
    }
    
    // Page du panier
    @GetMapping("/panier")
    public String panier(Model model) {
        System.out.println("=== WebController.panier() appelé ===");
        List<String> ingredients = panierService.getCurrentIngredients();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("ingredientCount", ingredients.size());
        return "panier";
    }
    
    // Page des cocktails likés
    @GetMapping("/likes")
    public String likedCocktails(Model model) {
        System.out.println("=== WebController.likedCocktails() appelé ===");
        List<Cocktail> likedCocktails = likeService.getLikedCocktails();
        model.addAttribute("cocktails", likedCocktails);

        return "likes";
    }
} 