// Page Admin - Gestion de l'administration
document.addEventListener('DOMContentLoaded', function() {
    
    // Fonction pour modifier un cocktail
    window.editCocktail = function(cocktailId) {
        // Rediriger vers une page d'édition ou ouvrir un modal
        window.location.href = `/admin/cocktails/${cocktailId}/edit`;
    };
    
    // Fonction pour supprimer un cocktail
    window.deleteCocktail = async function(cocktailId) {
        if (!confirm('Êtes-vous sûr de vouloir supprimer ce cocktail ?')) {
            return;
        }
        
        try {
            const response = await fetch(`/api/cocktails/${cocktailId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                // Supprimer la carte du cocktail avec animation
                const cocktailCard = document.querySelector(`[data-cocktail-id="${cocktailId}"]`);
                if (cocktailCard) {
                    cocktailCard.style.opacity = '0';
                    cocktailCard.style.transform = 'scale(0.8)';
                    setTimeout(() => {
                        cocktailCard.remove();
                        updateStats();
                    }, 300);
                }
                
                showNotification('Cocktail supprimé avec succès', 'success');
            } else {
                const error = await response.text();
                showNotification('Erreur lors de la suppression: ' + error, 'error');
            }
        } catch (error) {
            console.error('Erreur lors de la suppression:', error);
            showNotification('Erreur lors de la suppression', 'error');
        }
    };
    
    // Fonction pour exporter les données
    window.exportData = async function() {
        try {
            const response = await fetch('/api/admin/export');
            const data = await response.json();
            
            const blob = new Blob([JSON.stringify(data, null, 2)], { 
                type: 'application/json' 
            });
            const url = URL.createObjectURL(blob);
            
            const a = document.createElement('a');
            a.href = url;
            a.download = `cocktails-data-${new Date().toISOString().split('T')[0]}.json`;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
            
            showNotification('Données exportées avec succès', 'success');
        } catch (error) {
            console.error('Erreur lors de l\'export:', error);
            showNotification('Erreur lors de l\'export', 'error');
        }
    };
    
    // Fonction pour vider toutes les données
    window.clearAllData = async function() {
        if (!confirm('ATTENTION: Cette action supprimera TOUTES les données. Êtes-vous sûr ?')) {
            return;
        }
        
        if (!confirm('Cette action est irréversible. Continuer ?')) {
            return;
        }
        
        try {
            const response = await fetch('/api/admin/clear-all', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                showNotification('Toutes les données ont été supprimées', 'success');
                setTimeout(() => {
                    window.location.reload();
                }, 2000);
            } else {
                const error = await response.text();
                showNotification('Erreur lors de la suppression: ' + error, 'error');
            }
        } catch (error) {
            console.error('Erreur lors de la suppression:', error);
            showNotification('Erreur lors de la suppression', 'error');
        }
    };
    
    // Fonction pour mettre à jour les statistiques
    async function updateStats() {
        try {
            const response = await fetch('/api/admin/stats');
            const stats = await response.json();
            
            // Mettre à jour les statistiques affichées
            const statNumbers = document.querySelectorAll('.stat-number');
            if (statNumbers.length >= 3) {
                statNumbers[0].textContent = stats.totalCocktails || 0;
                statNumbers[1].textContent = stats.totalLikes || 0;
                statNumbers[2].textContent = stats.ingredientCount || 0;
            }
        } catch (error) {
            console.error('Erreur lors de la mise à jour des stats:', error);
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
    
    // Ajouter les attributs data-cocktail-id pour la suppression
    const cocktailCards = document.querySelectorAll('.admin-card');
    cocktailCards.forEach(card => {
        const cocktailId = card.querySelector('[onclick*="deleteCocktail"]')
            ?.getAttribute('onclick')?.match(/deleteCocktail\((\d+)\)/)?.[1];
        if (cocktailId) {
            card.setAttribute('data-cocktail-id', cocktailId);
        }
    });
    
    // Animation d'entrée pour les cartes admin
    cocktailCards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        
        setTimeout(() => {
            card.style.transition = 'all 0.3s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 100);
    });
    
    // Animation pour les statistiques
    const statCards = document.querySelectorAll('.stat-card');
    statCards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'scale(0.8)';
        
        setTimeout(() => {
            card.style.transition = 'all 0.3s ease';
            card.style.opacity = '1';
            card.style.transform = 'scale(1)';
        }, index * 200);
    });
}); 