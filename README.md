# Rapport Projet Android


## Introduction
L'application est une application Android développée en Kotlin et Java, utilisant le système de build Gradle. Elle permet aux utilisateurs de tester leurs connaissances en géographie en essayant de nommer des pays limitrophes les uns des autres. De plus, elle offre une fonctionnalité de recherche de pays par nom et capitale, un affichage détaillé des informations sur les pays, un mode hors ligne avec une fonctionnalité de favoris, et une base de données locale utilisant Room.

## Jeu des pays limitrophes

### Page d'accueil
Lorsque le jeu est lancée, l'utilisateur est accueilli par une page d'accueil qui explique les règles du jeu. Cette page contient un TextView qui affiche le texte explicatif du jeu. Le texte est défini dans le code Kotlin de l'activité BorderGameHomeActivity.
Sur la page d'accueil, l'utilisateur est invité à saisir le nom du pays de départ. Une fois que l'utilisateur a saisi le nom du pays de départ, il peut commencer le jeu en appuyant sur le bouton "Jouer".

### Activité « BorderGameActivity »
L'activité BorderGameActivity est responsable de la logique du jeu. Elle utilise une API pour récupérer des informations sur le pays saisi par l'utilisateur. Si le pays saisi est limitrophe du pays précédent, le jeu continue. Sinon, une exception est levée et le jeu se termine.

### Mise en œuvre technique
Le jeu des pays limitrophes est mis en œuvre principalement dans l'activité BorderGameActivity. Cette activité est responsable de la logique du jeu et de l'interaction avec l'API des pays. Cette logique est mise en œuvre dans l'activité BorderGameActivity, principalement dans la méthode synchro. Cette méthode fait une requête à l'API des pays pour obtenir des informations sur le pays entré par l'utilisateur, y compris une liste de tous les pays limitrophes. Elle compare ensuite cette liste au pays "actuel" pour déterminer si le jeu doit continuer ou se terminer. Si le pays "actuel" ne figure pas dans la liste des pays limitrophes du nouveau pays, alors le jeu se termine. L'utilisateur a perdu. Le jeu continue jusqu'à ce que l'utilisateur entre un pays qui n'est pas limitrophe du pays "actuel", ou jusqu'à ce que l'utilisateur décide de terminer le jeu.

### Listing des pays
L'application affiche une liste de pays dans une RecyclerView. Cette liste est alimentée par des données récupérées de l'API "www.apicountries.com". Chaque élément de la liste représente un pays et affiche des informations de base sur ce pays quand il est cliqué, comme son nom et sa capitale.

## Recherche de pays
Les utilisateurs peuvent rechercher des pays dans la liste en utilisant une fonctionnalité de recherche. Ils peuvent rechercher des pays par nom et capitale. La recherche est effectuée en temps réel à mesure que l'utilisateur tape dans le champ de recherche.

### Affichage des détails du pays
Lorsqu'un utilisateur clique sur un pays dans la liste, l'application affiche une page de détails avec plus d'informations sur ce pays. Ces informations incluent le nom du pays, la capitale, la population, et d'autres détails pertinents. Ces informations sont également récupérées de l'API des pays.

### Mode hors ligne
L'application offre un mode hors ligne qui permet aux utilisateurs de marquer des pays comme favoris. Ces pays favoris sont stockés dans une base de données locale utilisant Room, ce qui permet aux utilisateurs d'accéder à leurs pays favoris même sans connexion Internet.

### Mise en œuvre technique
La liste des pays est mise en œuvre en utilisant une RecyclerView dans le fichier de layout XML. Un adaptateur personnalisé est utilisé pour peupler la RecyclerView avec des données. Cet adaptateur est responsable de la création des vues pour chaque élément de la liste et de la liaison des données à ces vues. La recherche de pays est mise en œuvre en utilisant un SearchView. Lorsque l'utilisateur tape dans le SearchView, une requête est envoyée à l'API des pays pour récupérer les pays qui correspondent à la recherche. L'affichage des détails du pays est mis en œuvre en utilisant une autre activité. Lorsqu'un utilisateur clique sur un pays dans la liste, une nouvelle activité est lancée avec un Intent qui contient les détails du pays à afficher. Cette activité récupère les détails du pays de l'Intent et les affiche à l'utilisateur.

### Ouverture
Le développement futur de l'application comportera plusieurs améliorations importantes :
1.	Carte du Monde Interactive : Une carte du monde sera intégrée pour visualiser les pays saisis, en les coloriant successivement pour créer un effet de serpent ("snake").
2.	Validation de Saisie : Une validation sera mise en place pour empêcher la saisie d'un même pays plus d'une fois.
3.	Tableau des Scores : Un tableau des scores sera implémenté pour afficher les performances des utilisateurs. Les meilleurs joueurs seront mis en avant pour encourager la compétition et l'engagement.
Ces fonctionnalités visent à enrichir l'expérience utilisateur en rendant l'application plus interactive, compétitive et intuitive.
