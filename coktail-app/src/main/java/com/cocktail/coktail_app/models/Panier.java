package com.cocktail.coktail_app.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "paniers")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name =  "panier-ingredients", joinColumns = @JoinColumn(name = "panier_id"))
    private java.util.List<String> ingredients;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;




    // genenerer avec les outils de génération de code de spring boot

    public Panier(LocalDateTime dateCreation, Long id, List<String> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Panier [id=" + id + ", ingredients=" + ingredients + ", dateCreation=" + dateCreation + "]";
    }

}
