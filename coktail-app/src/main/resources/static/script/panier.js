// Page Panier - Gestion du panier d'ingrédients
document.addEventListener('DOMContentLoaded', function() {
    
    // Fonction pour supprimer un ingrédient du panier
    window.removeFromPanier = async function(ingredient) {
        try {
            const response = await fetch(`/api/panier/remove/${encodeURIComponent(ingredient)}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                // Supprimer l'élément de la liste avec animation
                const ingredientItem = document.querySelector(`[data-ingredient="${ingredient}"]`);
                if (ingredientItem) {
                    ingredientItem.style.opacity = '0';
                    ingredientItem.style.transform = 'translateX(-20px)';
                    setTimeout(() => {
                        ingredientItem.remove();
                        updatePanierDisplay();
                    }, 300);
                }
                
                showNotification('Ingrédient retiré du panier', 'info');
            }
        } catch (error) {
            console.error('Erreur lors de la suppression:', error);
            showNotification('Erreur lors de la suppression', 'error');
        }
    };
    
    // Fonction pour vider le panier
    window.clearPanier = async function() {
        if (!confirm('Êtes-vous sûr de vouloir vider votre panier ?')) {
            return;
        }
        
        try {
            const response = await fetch('/api/panier/clear', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                showNotification('Panier vidé avec succès', 'success');
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
        } catch (error) {
            console.error('Erreur lors du vidage:', error);
            showNotification('Erreur lors du vidage du panier', 'error');
        }
    };
    
    // Fonction pour exporter la liste
    window.exportPanier = function() {
        const ingredients = Array.from(document.querySelectorAll('.ingredient-name'))
            .map(item => item.textContent);
        
        if (ingredients.length === 0) {
            showNotification('Aucun ingrédient à exporter', 'info');
            return;
        }
        
        const listText = ingredients.join('\n');
        const blob = new Blob([listText], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        
        const a = document.createElement('a');
        a.href = url;
        a.download = 'liste-ingredients.txt';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
        
        showNotification('Liste exportée avec succès', 'success');
    };
    
    // Fonction pour mettre à jour l'affichage du panier
    function updatePanierDisplay() {
        const ingredientsList = document.querySelector('.ingredients-list');
        const remainingItems = ingredientsList.querySelectorAll('.ingredient-item');
        
        if (remainingItems.length === 0) {
            // Afficher l'état vide
            const panierContent = document.querySelector('.panier-content');
            panierContent.innerHTML = `
                <div class="empty-likes">
                    <div class="empty-likes-icon">🛒</div>
                    <h2>Panier vide</h2>
                    <p>Votre panier ne contient aucun ingrédient</p>
                    <button class="btn btn-primary" onclick="window.location.href='/'">
                        Découvrir des cocktails
                    </button>
                </div>
            `;
        } else {
            // Mettre à jour le compteur
            const countElement = document.querySelector('.panier-count-text span');
            if (countElement) {
                countElement.textContent = remainingItems.length;
            }
        }
    }
    
    // Fonction pour afficher une notification
    function showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        
        document.body.appendChild(notification);
        
        // Supprimer la notification après 3 secondes
        setTimeout(() => {
            notification.remove();
        }, 3000);
    }
    
    // Animation d'entrée pour les éléments du panier
    const ingredientItems = document.querySelectorAll('.ingredient-item');
    ingredientItems.forEach((item, index) => {
        item.style.opacity = '0';
        item.style.transform = 'translateX(-20px)';
        
        setTimeout(() => {
            item.style.transition = 'all 0.3s ease';
            item.style.opacity = '1';
            item.style.transform = 'translateX(0)';
        }, index * 100);
    });
    
    // Ajouter les attributs data-ingredient pour la suppression
    ingredientItems.forEach(item => {
        const ingredientName = item.querySelector('.ingredient-name').textContent;
        item.setAttribute('data-ingredient', ingredientName);
    });
}); 