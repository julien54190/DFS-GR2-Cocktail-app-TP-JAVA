// Page Nouveau Cocktail - Gestion du formulaire
document.addEventListener('DOMContentLoaded', function() {
    
    const form = document.querySelector('form');
    const ingredientInput = document.getElementById('ingredientInput');
    const ingredientsList = document.getElementById('ingredientsList');
    const ingredientsField = document.getElementById('ingredientsField');
    let ingredients = [];
    
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        if (ingredients.length === 0) {
            alert('Veuillez ajouter au moins un ingrédient');
            return;
        }
        
        const formData = new FormData(form);
        const cocktailData = {
            name: formData.get('name'),
            description: formData.get('description'),
            imageUrl: formData.get('imageUrl'),
            ingredients: ingredients
        };
        
        try {
            const response = await fetch('/api/cocktails', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cocktailData)
            });
            
            if (response.ok) {
                showNotification('Cocktail créé avec succès !', 'success');
                setTimeout(() => {
                    window.location.href = '/';
                }, 1500);
            } else {
                const error = await response.text();
                showNotification('Erreur lors de la création: ' + error, 'error');
            }
        } catch (error) {
            console.error('Erreur lors de la création:', error);
            showNotification('Erreur lors de la création du cocktail', 'error');
        }
    });
    
    window.addIngredient = function() {
        const ingredient = ingredientInput.value.trim();
        
        if (ingredient === '') {
            alert('Veuillez entrer un nom d\'ingrédient');
            return;
        }
        
        if (ingredients.includes(ingredient)) {
            alert('Cet ingrédient est déjà ajouté');
            return;
        }
        
        ingredients.push(ingredient);
        updateIngredientsDisplay();
        ingredientInput.value = '';
        ingredientInput.focus();
    };
    
    window.removeIngredient = function(index) {
        ingredients.splice(index, 1);
        updateIngredientsDisplay();
    };
    
    function updateIngredientsDisplay() {
        ingredientsList.innerHTML = '';
        ingredientsField.value = ingredients.join(',');
        
        ingredients.forEach((ingredient, index) => {
            const tag = document.createElement('div');
            tag.className = 'px-12 py-6 bkg-light radius text-sm flex gap-12 align-items-center';
            tag.innerHTML = `
                <span>${ingredient}</span>
                <button type="button" class="btn btn-danger" onclick="removeIngredient(${index})" style="padding: 4px 8px; font-size: 12px;">
                    ❌
                </button>
            `;
            ingredientsList.appendChild(tag);
        });
    }
    
    ingredientInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            addIngredient();
        }
    });
    
    const inputs = form.querySelectorAll('input, textarea');
    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            validateField(this);
        });
    });
    
    function validateField(field) {
        const value = field.value.trim();
        const fieldName = field.name;
        
        const existingError = field.parentNode.querySelector('.error');
        if (existingError) {
            existingError.remove();
        }
        
        let isValid = true;
        let errorMessage = '';
        
        switch (fieldName) {
            case 'name':
                if (value.length < 2) {
                    isValid = false;
                    errorMessage = 'Le nom doit contenir au moins 2 caractères';
                }
                break;
            case 'description':
                if (value.length < 10) {
                    isValid = false;
                    errorMessage = 'La description doit contenir au moins 10 caractères';
                }
                break;
            case 'imageUrl':
                if (!isValidUrl(value)) {
                    isValid = false;
                    errorMessage = 'Veuillez entrer une URL valide';
                }
                break;
        }
        
        if (!isValid) {
            const errorElement = document.createElement('div');
            errorElement.className = 'error';
            errorElement.textContent = errorMessage;
            field.parentNode.appendChild(errorElement);
        }
    }
    
    function isValidUrl(string) {
        try {
            new URL(string);
            return true;
        } catch (_) {
            return false;
        }
    }
    
    function showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.remove();
        }, 3000);
    }
}); 