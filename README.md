# Projet Pokemon Android
Une fois l'application lancée, vous arriverez sur le menu principal vous proposant :
    
    1. De continuer une partie (Online)
    
Votre couple login / password sera alors exigé. Ainsi seule l'application UWP permettra de créer un nouveau compte.
Toutefois, une fois créé vous pourrez vous connecter, soit depuis l'application Android soit depuis l'application UWP. 
Aussi, il vous sera impossible de lancer un combat contre vous-même.

Une fois connecté, vous retrouverez votre personnage avec ses pokémons.

Pour débuter un combat il suffira de cliquer sur lancer "" ou sur "". Vous choisirez alors le pokémon que vous souhaitez utiliser 
pour le combat parmi les 3 pokémons que possède le personnage que vous avez choisi d'incarner.
Dans le premier cas on cherchera un combat où il manque un joueur durant 30 secondes. Si aucun adversaire n'est trouvé le combat
sera annulé et vous devrez lancer une nouvelle recherche. Dans le deuxième cas l'application cherchera un combat incomplet et 
si un combat est trouvé alors le combat commencera.

Dans tous les cas ce sera au joueur ayant instancié le combat d'attaquer en premier.

Le combat se terminera lorsqu'un des deux pokémons mourra. Le dresseur possédant le pokémon vainqueur sera considéré comme 
gagnant. Il sera également possible d'abandonner le combat dans ce cas le joueur qui abandonne sera considéré comme perdant.

Le résultat du combat sera enregistré en base de données incluant le dresseur victorieux, son pokémon, le dresseur perdant, 
son pokémon, ainsi que la durée du combat exprimée en nombre de tours.

