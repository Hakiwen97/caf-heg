# Projet CAF - HEG
## Équipe OH - AS (Océane Henry et Adrien Sigrist)

## 1. Compréhension du projet

Ce projet gère le back-end d'une application de Caisse d'Allocations Familiales (CAF). 

Sa principale fonctionnalité est un algorithme qui permet de déterminer, selon une situation, quel parent bénéficie de l'allocation pour son enfant.

L'application expose une API qui permet plusieurs manipulations en relation avec son activité, telles que : 
- Détermination du droit à l'allocation pour une situation
- Affichage de tous les allocataires 
- Affichage de toutes les allocations et versements
- Export des allocations et versements pour un allocataire.
 
## 2. Structure du projet 

Le projet est décomposé en trois packages princpaux : integration-test, main et test 


Le package **main** possède deux sous-packages : 
- **Business** : contient toutes les classes ayant-trait à l'activité métier de la CAF. C'est aussi notamment dans celle-ci, et plus précisement dans la classe *Allocation Service* que l'on retrouve l'algorithme de décision du droit d'allocation. 
- **Infrastructure** : contient toutes les classes permettant de rendre l'application fonctionnelle. Il s'agit de l'API, des mécanismes de persistance, du transformateur PDF ainsi que du launcher de l'application.

Le package **test** contient des classes de tests pour les différentes fonctionnalités de l'application. Pour une meilleure compréhension de la suite, en voici la strucuture :

- java
  - allocations
    - AllocationServiceTest
    - CantonTest
    - GetParentDroitallocationStepDef
    - RunCucumberTest
  - versement
    -   VersementServiceTest
- resources
  - allocations
    - GetParentDroitAllocation.feature
    
D'une part, il y a les tests au niveau des versements, et d'autres les tests au niveau des allocations. Dans ce dernier point, le package des tests d'allocations contient une classe de tests unitaires : *AllocationServiceTest*.

Les tests de comportement se trouvent également au niveau des tests allocations, à l'exception de la définition des scénarios, qui se trouve dans les ressources de test.

Le package **integration-test** permet de réaliser des tests en incluant les données de la base de données de la CAF. Ici, il est réalisé à l'aide d'un dataset.xml et d'une classe de test.

##3. Compréhension des outils
### Frameworks  
1. Spring - Spring permet la construction en Java.
2. Spring Boot - Spring Boot  se base sur Spring et permet de créer et gérer notre API. 
3. JUnit - JUnit permet la réalisation et l'assertion des tests unitaires.
    - DBUnit - Extension de JUnit qui permet de tester les interactions avec la base de données. 
4. Springfox - Springfox génère le contrat d'API et lie Spring et le Swagger
5. Cucumber - Cucumber permet la gestion des tests de comportement.
### Librairies
1. Apache PDF Box - Permet la gestion et la transformation de données en PDF.
2. JSON-PAth - Permet le requêtage sur des String JSON.
3. HikariCP - Implémente JDBC DataSource et qui fourni des pools de connexion.
4. Flyway - Outil de migration des bases de données.
5. H2 Database - H2 est une librarie de gestion de système de base de données. Permet de créer une base données en mémoire.
6. Jackson Databind - Permet la transformation entre du JSON et une classe Java.

##4. Compétences acquises durant la réalisation du projet
Ce projet nous a permis de mettre en oeuvre différentes compétences théoriques vues durant notre formation. Les deux principales que nous relevons sont le pair-programming ainsi que le Test Driven Development (TDD).

Le **pair-programming** n'est pas toujours simple, et encore moins à distance, les outils de télécommunications présentant facilement des latences.

Le **TDD** était aussi une nouvelle approche dont nous n'avions pas l'habitude et qu'il a été compliqué de respecter par moments. 

Malgré toutes ces belles paroles, il s'avère que nous avons initialement oublié l'exigence de TDD pour ce projet (emportés par la fougue de vouloir programmer un if dantesque). Mais nous avons corrigé le tir plus tard et le reste du projet s'est fait en TDD..

Aussi, nous avons tout deux grandement amélioré nos compétences en debug à l'aide des différents outils que l'IDE nous proposait, ainsi que par l'usage des logs.

## 5. Choix effectués

Le cadre du projet étant très défini, il n'y a que peu de choix que nous avons été amenés à faire. 

### Choix n°1
Le premier choix notable est celui que nous avons fait en refactor la structure de données. Nous sommes passés d'une Map<String, Object> a une classe ParentDroitAllocation. Cette dernière est composées des attributs suivants : 
```java
  private String childAddress;
  private Canton childCanton;
  private boolean parentsTogether;
  private Parent parent1;
  private Parent parent2;
```
Nous avons dès lors également créé une classe Parent, comme suit : 

```java
  private boolean lucrativeActivity;
  private String parentAddress;
  private boolean isFreelancer;
  private Canton workingCanton;
  private BigDecimal salary;
  private boolean parentalAuthority;
```

Il était pour nous plus logique d'utiliser des objets dans le cadre de manipulations telles.

### Choix n°2

Le second choix est relativement mineur puisqu'il s'agit simplement d'une préoccupation métier.

Dans l'algorithme de décision du droit à l'allocation, il est possible de se retrouver dans une situation où le salaire est le facteur de décision. C'est en général le parent au salaire le plus élevé qui obtient le droit. 
Dans la situation où les salaires sont égaux, nous ne savions pas quoi faire. 

Nous avons décidé, qu'à salaire égal, c'était le parent1 qui bénéficiait de l'allocation. Ce choix nous a été validé par le métier.


##6. Difficultés rencontrées 

Les difficultés rencontrées sont plutôt d'ordre technique. Cela s'explique par un manque de compétences de programmations (bien que nous nous soyons bien améliorés depuis le début du projet).

En voici quelques unes des plus notables : 

- Dans *AllocationService* nous avons voulu utiliser un instance de *VersementMapper*, mais celle-ci ne retournait rien. Nous avons donc dû faire une injection de dépendance afin que cela fonctionne.
- Dans le cadre des tests d'intégration, nous avons eu du mal à configurer l'accès à la base de données et le fait qu'il fallait spécifier que les manipulations se faisaient dans le cadre d'une transaction. L'enseignant nous a aidé à régler ce problème et nous avons compris les lignes de code qui permettaient de faire ce qui était demandé.
- Le if de *AllocationService* était assez compliqué à faire, car il fallait tenir compte de toutes les possibilités et des situations inversées. Il n'est pas impossible que du Dafalgan ait été utilisé dans le cadre de l'implémentation de cet algorithme. Mais nous y sommes arrivés ! L'utilisation du XOR permet aussi de simplifier un peu l'implémentation.

##7. Eléments que vous auriez voulu intégrer

Avec plus de temps, nous aurions apprécié pouvoir rajouter des améliorations proposées à notre projet. Nous aurions par exemple mieux géré les transactions. Cependant, limités par le temps (en raison du volume de travail dans les autres cours), nous avons préféré ne pas risquer de casser notre projet à quelques heures du rendu.
