package com.cocktail.coktail_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.service.LikeService;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "*")
public class LikeController {
    
    @Autowired
    private LikeService likeService;
    
    // POST - Ajouter un like
    @PostMapping("/{cocktailId}")
    public ResponseEntity<String> likeCocktail(@PathVariable Long cocktailId) {
        boolean success = likeService.likeCocktail(cocktailId);
        if (success) {
            return ResponseEntity.ok("Cocktail liké avec succès");
        }
        return ResponseEntity.badRequest().body("Impossible de liker le cocktail");
    }
    
    // DELETE - Retirer un like
    @DeleteMapping("/{cocktailId}")
    public ResponseEntity<String> unlikeCocktail(@PathVariable Long cocktailId) {
        boolean success = likeService.unlikeCocktail(cocktailId);
        if (success) {
            return ResponseEntity.ok("Like retiré avec succès");
        }
        return ResponseEntity.badRequest().body("Impossible de retirer le like");
    }
    
    // GET - Vérifier si un cocktail est liké
    @GetMapping("/{cocktailId}/status")
    public ResponseEntity<Boolean> isCocktailLiked(@PathVariable Long cocktailId) {
        boolean isLiked = likeService.isCocktailLiked(cocktailId);
        return ResponseEntity.ok(isLiked);
    }
    
    // GET - Récupérer tous les IDs des cocktails likés
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getLikedCocktailIds() {
        List<Long> likedIds = likeService.getLikedCocktailIds();
        return ResponseEntity.ok(likedIds);
    }
    
    // GET - Récupérer tous les cocktails likés
    @GetMapping("/cocktails")
    public ResponseEntity<List<Cocktail>> getLikedCocktails() {
        List<Cocktail> likedCocktails = likeService.getLikedCocktails();
        return ResponseEntity.ok(likedCocktails);
    }
    
    // GET - Compter les likes d'un cocktail
    @GetMapping("/{cocktailId}/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long cocktailId) {
        long count = likeService.getLikeCount(cocktailId);
        return ResponseEntity.ok(count);
    }
} 