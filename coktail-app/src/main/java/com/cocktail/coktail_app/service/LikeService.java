package com.cocktail.coktail_app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.models.Like;
import com.cocktail.coktail_app.repository.CocktailRepository;
import com.cocktail.coktail_app.repository.LikeRepository;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CocktailRepository cocktailRepository;

    // Ajouter un like à un cocktail
    public boolean likeCocktail(Long cocktailId) {
        System.out.println("=== LikeService.likeCocktail() appelé avec cocktailId: " + cocktailId + " ===");
        
        Optional<Cocktail> optionalCocktail = cocktailRepository.findById(cocktailId);
        if (optionalCocktail.isPresent()) {
            Cocktail cocktail = optionalCocktail.get();
            System.out.println("=== Cocktail trouvé: " + cocktail.getName() + " ===");

            Optional<Like> existingLike = likeRepository.findByCocktail_Id(cocktailId);
            if (existingLike.isPresent()) {
                System.out.println("=== Like déjà existant pour ce cocktail ===");
                return false;
            }
            
            Like like = new Like();
            like.setCocktail(cocktail);
            likeRepository.save(like);
            System.out.println("=== Like créé avec succès ===");
            return true;
        }
        System.out.println("=== Cocktail non trouvé ===");
        return false;
    }

    // Supprimer un like d'un cocktail
    public boolean unlikeCocktail(Long cocktailId) {
        Optional<Like> optionalLike = likeRepository.findByCocktail_Id(cocktailId);
        if (optionalLike.isPresent()) {
            likeRepository.delete(optionalLike.get());
            return true;
        }
        return false;
    }

    // Vérifier si un cocktail est aimé
    public boolean isCocktailLiked(Long cocktailId) {
        return likeRepository.findByCocktail_Id(cocktailId).isPresent();
    }

    // Récuperer les IDs des cocktails aimés
    public List<Long> getLikedCocktailIds() {
        List<Like> likes = likeRepository.findAll();
        return likes.stream()
                .map(like -> like.getCocktail().getId())
                .collect(Collectors.toList());
    }

        // Récupérer tous les cocktails aimés
        public List<Cocktail> getLikedCocktails() {
            List<Like> likes = likeRepository.findAll();
            return likes.stream()
                    .map(Like::getCocktail)
                    .collect(Collectors.toList());
        }

        // Compter les likes d'un cocktail
        public long getLikeCount(Long cocktailId) {
            Optional<Cocktail> optionalCocktail = cocktailRepository.findById(cocktailId);
            if (optionalCocktail.isPresent()) {
                return likeRepository.countByCocktail(optionalCocktail.get());
            }
            return 0;
        }
        
        // Compter le total des likes
        public long getTotalLikes() {
            return likeRepository.count();
        }

}
