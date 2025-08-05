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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cocktails")
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "l'url de l'image est obligatoire")
    private String imageUrl;

    @NotBlank(message = "le nom du cocktail est obligatoire")
    @Size(min = 2, max = 50, message = "le nom du cocktail doit contenir entre 2 et 50 caractères")
    private String name;

    @Column(columnDefinition= "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "cocktail_ingredients", joinColumns = @JoinColumn(name = "cocktail_id"))
    @Column(name = "ingredient")
    private List<String> ingredients;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    public Cocktail() {
        this.dateCreation = LocalDateTime.now();
    }

    // genenerer avec les outils de génération de code de spring boot

    public Cocktail(String description, Long id, String imageUrl, List<String> ingredients, String name) {
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Cocktail [id=" + id + ", imageUrl=" + imageUrl + ", name=" + name + ", description=" + description
                + ", ingredients=" + ingredients + ", dateCreation=" + dateCreation + "]";
    }
    

}
