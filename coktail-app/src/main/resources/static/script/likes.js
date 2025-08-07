// Page Likes - Gestion des favoris
document.addEventListener('DOMContentLoaded', function() {
    
    // Gestion des likes et panier
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
                // Supprimer la carte du cocktail de la page
                const cocktailCard = button.closest('.cocktail-card');
                cocktailCard.style.opacity = '0';
                setTimeout(() => {
                    cocktailCard.remove();
                    
                    // Vérifier s'il reste des cocktails
                    const remainingCards = document.querySelectorAll('.cocktail-card');
                    if (remainingCards.length === 0) {
                        showEmptyState();
                    }
                }, 300);
                
                showNotification('Cocktail retiré des favoris', 'info');
            }
        } catch (error) {
            console.error('Erreur lors du like:', error);
            showNotification('Erreur lors de la suppression du favori', 'error');
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
    
    // Fonction pour afficher l'état vide
    function showEmptyState() {
        const cocktailsGrid = document.querySelector('.cocktails-grid');
        if (cocktailsGrid) {
            cocktailsGrid.innerHTML = `
                <div class="empty-likes">
                    <div class="empty-likes-icon">💔</div>
                    <h2>Aucun favori</h2>
                    <p>Vous n'avez plus de cocktails dans vos favoris</p>
                    <button class="btn btn-primary" onclick="window.location.href='/'">
                        Découvrir des cocktails
                    </button>
                </div>
            `;
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
    
    // Animation d'entrée pour les cartes
    const cocktailCards = document.querySelectorAll('.cocktail-card');
    cocktailCards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        
        setTimeout(() => {
            card.style.transition = 'all 0.3s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 100);
    });
}); 