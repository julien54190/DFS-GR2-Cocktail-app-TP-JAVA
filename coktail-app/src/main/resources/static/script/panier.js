// Page Panier - Gestion du panier d'ingrédients
document.addEventListener('DOMContentLoaded', function() {
    
    // Fonction pour supprimer un ingrédient du panier
    window.removeFromPanier = async function(ingredient) {
        try {
            const response = await fetch(`/api/panier/ingredient/${encodeURIComponent(ingredient)}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                // Supprimer l'élément de la liste avec animation
                const ingredientItems = document.querySelectorAll('.flex.justify-content-between');
                ingredientItems.forEach(item => {
                    const ingredientName = item.querySelector('.text-bold').textContent;
                    if (ingredientName === ingredient) {
                        item.style.opacity = '0';
                        item.style.transform = 'translateX(-20px)';
                        setTimeout(() => {
                            item.remove();
                            updatePanierDisplay();
                        }, 300);
                    }
                });
                
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
        const ingredients = Array.from(document.querySelectorAll('.text-bold'))
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
        const remainingItems = document.querySelectorAll('.flex.justify-content-between');
        
        if (remainingItems.length === 0) {
            // Recharger la page pour afficher l'état vide
            window.location.reload();
        } else {
            // Mettre à jour le compteur dans la navigation
            const countElements = document.querySelectorAll('.panier-count');
            countElements.forEach(element => {
                element.textContent = remainingItems.length;
            });
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
    const ingredientItems = document.querySelectorAll('.flex.justify-content-between');
    ingredientItems.forEach((item, index) => {
        item.style.opacity = '0';
        item.style.transform = 'translateX(-20px)';
        
        setTimeout(() => {
            item.style.transition = 'all 0.3s ease';
            item.style.opacity = '1';
            item.style.transform = 'translateX(0)';
        }, index * 100);
    });
    
    // Ajouter les événements de clic pour les boutons de suppression
    const removeButtons = document.querySelectorAll('.remove-ingredient-btn');
    removeButtons.forEach(button => {
        button.addEventListener('click', function() {
            const ingredient = this.getAttribute('data-ingredient');
            removeFromPanier(ingredient);
        });
    });
}); 