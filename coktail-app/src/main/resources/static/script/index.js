// Page Index - Gestion des likes et panier
document.addEventListener('DOMContentLoaded', function() {
    
    // Gestion des likes
    document.addEventListener('click', function(e) {
        if (e.target.dataset.action === 'toggle-like') {
            const cocktailId = e.target.dataset.cocktailId;
            toggleLike(cocktailId, e.target);
        }
        
        if (e.target.dataset.action === 'add-to-panier') {
            const cocktailId = e.target.dataset.cocktailId;
            addToPanier(cocktailId);
        }
    });
    
    // Fonction pour basculer le like
    async function toggleLike(cocktailId, button) {
        try {
            const response = await fetch(`/api/likes/${cocktailId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                const isLiked = button.classList.contains('btn-liked');
                
                if (isLiked) {
                    button.classList.remove('btn-liked');
                    button.classList.add('btn-like');
                    button.textContent = '🤍';
                } else {
                    button.classList.remove('btn-like');
                    button.classList.add('btn-liked');
                    button.textContent = '❤️';
                }
            }
        } catch (error) {
            console.error('Erreur lors du like:', error);
        }
    }
    
    // Fonction pour ajouter au panier
    async function addToPanier(cocktailId) {
        try {
            const response = await fetch(`/api/panier/add/${cocktailId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                // Mettre à jour le compteur du panier
                updatePanierCount();
                showNotification('Cocktail ajouté au panier !', 'success');
            }
        } catch (error) {
            console.error('Erreur lors de l\'ajout au panier:', error);
            showNotification('Erreur lors de l\'ajout au panier', 'error');
        }
    }
    
    // Fonction pour mettre à jour le compteur du panier
    async function updatePanierCount() {
        try {
            const response = await fetch('/api/panier/count');
            const count = await response.text();
            
            const panierCounts = document.querySelectorAll('.panier-count');
            panierCounts.forEach(countElement => {
                countElement.textContent = count;
            });
        } catch (error) {
            console.error('Erreur lors de la mise à jour du compteur:', error);
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
    
    // Initialiser le compteur du panier au chargement
    updatePanierCount();
});
