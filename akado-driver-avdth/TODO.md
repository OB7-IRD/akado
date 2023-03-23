TODO
====

- Il serait même bien vu de rajouter un champ en tête de ligne Flag = erreur ou alerte.

TRIP:
-----

- Raising Factor:
Suivant le pavillon, la prise en compte du FP est nécessaire dans ce calcul.
Les ghanéens, guinéens, béliziens entre autres débarquent beaucoup de FP parfois 100%.
Peut-être pour faire plus simple sur la feuille TRIP tu pourrais faire une colonne RF avec FP et une autre RF sans FP. 
  + Dans un premier temps, ajouter le pavillon dans la feuille MetaTrip.

ACTIVITY:
---------

- Operation: 
    + pour les codes 12, 13 et 14, je pense qu'il faudrait en discuter car ces trois opérations impliquent des prises mais faut-il les prendre ou non en compte, il faut demander à nos chefs.


WELL
----
- Je ne sais pas si c'est encore possible mais je pense qu'il serait bon de rajouter un test dans Akado au niveau des dates de pêche contenues dans le plan de cuves.
Ces dates doit impérativement appartenir à la marée en cours, sinon cela bloque lors de l'importation dans T3.
Je m'explique un bateau fait un débarquement partiel et repars en mer il revient et nous remet ses documents de bord où apparait des cuves de la première partie de marée.
Il ne faut pas saisir ces calées. Cette erreur n'est pas signalée dans le Akado (version actuelle). 


DONE
====

- Juste un détail pour faciliter l'accès à la BD car souvent il faut faire tourner plusieurs fois AKaDo sur la même BD.
Lors de la demande FICHIER -> OUVRIR serait-il possible de lancer la fenêtre de sélection dans le dossier où l'on a ouvert la BD précédente.

TRIP
----
- Je souhaiterai que tu rajoutes un test: Un bateau qui rentre dans un port doit OBLIGATOIREMENT sortir de ce même port. 
Oui pour OA et OI. Moi, je vois plutôt une erreur cela signifie qu'il manque des jours de mer.
- LandingTotalWeightInspector: Au poids des espèces commerciales débarquées il 
faut rajouter le poids du Faux-poisson. Une seconde solution serait d'utiliser 
les tables FP mais **je dois au préalable intégrer le faux-poisson dans le driver AVDTH.**
- Ajouter un critère de mise en evidence dans le template TRIP pour F_ENQ
- Recovery Time :  
Dans la marée 08/04/2015 du 977, j'ai supprimé la journée du 12/03.
J'ai pris soin de faire correspondre les heures de mer et les heures de pêche entre la table MAREE et la table ACTIVITE.
Il y a donc rupture dans les dates.
Dans la page LOGS, cette rupture est bien listée en erreur mais elle n'apparait sur la page TRIP. 

ACTIVITY
--------
- Fishing Context: Après avoir introduit plusieurs erreurs dans AC_ASSOC ou ACTIVITE, AKaDo les détectent bien.
Problème les liste dans LOGS mais ne les affiche pas dans ACTIVITY. 
- Quadrant: Le test tourne, il y a juste une erreur dans l'affichage AI au lieu de OI. 
- Operation: 
    + J'ai mis un C_OPERA = 3 (recherches) sur une ligne avec V_POIDS_CAP <> 0 ==> Pas d'erreur signalée dans Logs et Activity.
- Position: Ce test n'a pas l'air de fonctionner du tout, j'ai mis une position sur terre, une position en OI et une position en dehors du carré délimité dans le paramétrage d'AVDTH --> Riens dans les pages "Logs" et "Activity". 
Tiens  tu compte du paramétrage de zone AVDTH (Utilitaires -> Initialisation des paramétres généraux -> Zone géographique).

SAMPLE
------
- Activity: Les erreurs de positions sont bien détectées dans "Logs" mais n'apparaissent pas sur la feuille "Sample", il n'y a aucune référence à la position.
- Species : J'ai rentré un code requin en espèce (7) le test détecte bien l'erreur mais ne la met pas en évidence (couleur). 
- Length class: Erreur les LF sont possible jusqu'à 80 cm et non 55 cm comme paramétré dans le test.
