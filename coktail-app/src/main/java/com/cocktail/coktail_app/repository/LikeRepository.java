package com.cocktail.coktail_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocktail.coktail_app.models.Cocktail;
import com.cocktail.coktail_app.models.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByCocktail(Cocktail cocktail);

    Optional<Like> findByCocktail_Id(Long cocktailId);

    Long countByCocktail(Cocktail cocktail);


}
