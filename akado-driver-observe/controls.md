# Abstract

Ce document décrit tous les controles implantés dans ce module.

# Marée

| controle              | classe java                                                     | Codes                    | Avancement | Commentaires |
|:----------------------|:----------------------------------------------------------------|:-------------------------|:-----------|:-------------|
| Activité              | fr.ird.akado.observe.inspector.trip.ActivityInspector           | ```E1018```              |            |              |
| Temps de peche        | fr.ird.akado.observe.inspector.trip.FishingTimeInspector        | ```E1017```              |            |              |
| Temps en mer          | fr.ird.akado.observe.inspector.trip.TimeAtSeaInspector          | ```E1011```              |            |              |
| Capacité du navire    | fr.ird.akado.observe.inspector.trip.LandingConsistentInspector  | ```E1002```, ```E1022``` |            |              |
| Poids total débarqué  | fr.ird.akado.observe.inspector.trip.LandingTotalWeightInspector | ```E1016```              |            |              |
| Distance              | ???                                                             |                          |            |              |
| Couverture temporelle | fr.ird.akado.observe.inspector.trip.RecoveryTimeInspector       | ```E1013```              |            |              |
| Limite temporelle     | fr.ird.akado.observe.inspector.trip.TemporalLimitInspector      | ```E1012```              |            |              |
| Port                  | fr.ird.akado.observe.inspector.trip.HarbourInspector            | ```E1023```              |            |              |
| Raising factor        | fr.ird.akado.observe.inspector.metatrip.RaisingFactorInspector  | ```E1023```, ```W1020``` |            |              |

## Activité (Warning)

*Le controle est en ERROR et non pas en warning*

*Les messages d'erreur sont à adapter.*

Version AVDTH:
{0}> Mar\u00e9e[{1}] Il n''y a pas d''activit\u00e9s dans la mar\u00e9e mais le champ \u00ab F_ENQ \u00bb a la valeur 1.

{0}> Mar\u00e9e[{1}] Il y a au moins une activit\u00e9 dans la mar\u00e9e mais le champ \u00ab F_ENQ \u00bb a la valeur 0.

Version ObServe:

**à fournir** sur le champs logbookAcquisitionStatus

## Temps de peche

## Temps en mer

## Capacité du navire (Warning)

*Le controle est en ERROR et non pas en warning*

```E1002``` si le navire n'a pas de capacité de définie.

```E1022``` si le poids total (débarqué + marché local) > à la capacité du navire.

## Poids total débarqué

## Distance (Warning)

**? non trouvé**

## Couverture temporelle

## Limite temporelle

## Port

## Raising factor (Info)

```E1023``` si pas de capture mais un landingTotalWeight définie sur la marée

```W1020``` si le raising factor n'est pas conforme

# Activité

| controle                 | classe java                                                      | Codes                                           | Avancement | Commentaires |
|:-------------------------|:-----------------------------------------------------------------|:------------------------------------------------|:-----------|:-------------|
| Contexte de pêche        | fr.ird.akado.observe.inspector.activity.FishingContextInspector  | ```E1219```, ```E1210-0```, ```E1220-1```       |            |              |
| Opération                | fr.ird.akado.observe.inspector.activity.OperationInspector       |                                                 |            |              |
| Position                 | fr.ird.akado.observe.inspector.activity.PositionInspector        | ```E1214```,```W1217```, ```E1212```            |            |              |
| Quadrant                 | fr.ird.akado.observe.inspector.activity.QuadrantInspector        | ```E1229```,```E1230```,```E1213```,```E1216``` |            |              |
| Poids total des captures | fr.ird.akado.observe.inspector.activity.WeightInspector          | ```E1210```                                     |            |              |
|                          | fr.ird.akado.observe.inspector.activity.PositionInEEZInspector   | ```W1234```                                     |            | Non décrit   |
|                          | fr.ird.akado.observe.inspector.activity.EEZInspector             | ```W1232```                                     |            | Non décrit   |
|                          | fr.ird.akado.observe.inspector.activity.WeightingSampleInspector | ```E1233```                                     |            | Non décrit   |


## Contexte de pêche

Dans akado on parle de type de banc artificiel (code 1) 

Controle existant qu'on conserve ?

```E1219``` si type de banc objet et pas de systèmes observés

```E1220-0``` si type de banc objet et on n'a pas trouvé l'un des systèmes observés sur banc objet

```E1220-1``` si type de banc est libre et on a trouvé au moins un des systèmes observés sur banc objet

## Opération

Il y avait un controle non compatible avec observe (si pas d'activité et setCount > 0 ...)
Il y avait un controle sur le type de banc (3) si opération de peche...

Dans le controle akado on utilise activity.totalWeight et non pas somme (catch.weight)

Il me faut les messages pour les quatre cas décrits dans le doc

## Position

Si pas de coordonnées sur l'activité, ne rien faire.

Si sur un port, ne rien faire.

```E1214``` si pas dans un des deux océans et dans un pays (sur les terres)
```W1217``` si pas dans un des deux océans et pas dans un pays (sur les terres)
```E1212``` si dans un des deux océans mais pas celui déclaré dans la marée

## Quadrant

```E1229``` si pas de latitude et quadrant pas à 1 ou 4 
```E1230``` si pas de longitude et quadrant pas à 1 ou 2
```E1213``` si quadrant 3 ou 4 et ocean de la marée indien
```E1216``` si quadrant 3 ou 4 et ocean de l'activité indien

## Poids total des captures

## Controle manquant dans le document....

### fr.ird.akado.observe.inspector.activity.PositionInEEZInspector

*Check if the EEZ reported is consistent with the eez calculated from the position activity.*

### fr.ird.akado.observe.inspector.activity.EEZInspector

*Check if the EEZ reported is consistent with operation.*

### fr.ird.akado.observe.inspector.activity.WeightingSampleInspector

???

{0}> Activit\u00e9[{1}] La somme des captures \u00e9lementaires ({2}) et la ponderation des \u00e9chantillons ({3}) sont diff\u00e9rentes.

# Echantillon

| controle                           | classe java                                                         | Codes                                             | Avancement | Commentaires |
|:-----------------------------------|:--------------------------------------------------------------------|:--------------------------------------------------|:-----------|:-------------|
| Classe de taille                   | fr.ird.akado.observe.inspector.sample.LengthClassInspector          | ```W1329```                                       |            |              |
| Espèces                            |                                                                     |                                                   | 100%       | Supprimé     |
| Mesure                             | fr.ird.akado.observe.inspector.sample.MeasureInspector              | ```E1323```                                       |            |              |
| Position                           |                                                                     |                                                   | 100%       | Supprimé     |
| Echantillon sans mesure            | fr.ird.akado.observe.inspector.sample.SampleWithoutMeasureInspector | ```E1312```                                       |            |              |
| Echantillon sans Espèces           | fr.ird.akado.observe.inspector.sample.SampleWithoutSpeciesInspector | ```E1311```                                       |            |              |
| Echantillon sans Marée             |                                                                     |                                                   | 100%       | Supprimé     |
| Super Echantillon                  |                                                                     |                                                   | 100%       | Supprimé     |
| Cuve                               | fr.ird.akado.observe.inspector.sample.WellNumberConsistentInspector | ```E1313```                                       |            |              |
| Ratio de petit et de gros poissons | fr.ird.akado.observe.inspector.sample.LittleBigInspector            | ```W1329```,```W1330```                           |            |              |
| Pondération                        | fr.ird.akado.observe.inspector.sample.WeightingInspector            | ```W1320```, ```W1322```,```E1327```, ```E1326``` |            |              |
|                                    | fr.ird.akado.observe.inspector.sample.WeightSampleInspector         | ```E1319```                                       |            | Non décrit   |
|                                    | fr.ird.akado.observe.inspector.sample.LDLFInspector                 | ```E1334``` ,```W1332```, ```W1333```             |            | Non décrit   |
|                                    | fr.ird.akado.observe.inspector.sample.DistributionInspector         | ```E1335```                                       |            | Non décrit   |

## Classe de taille

On teste uniquement pour trois espèces

* YFT (code 1) <= 90
* BET (code 3) <= 90
* YFT (code 4) <= 42

On en tient pas compte du type de mesure

## Mesure


## Echantillon sans mesure


## Echantillon sans Espèces

## Cuve

## Ratio de petit et de gros poissons

```W1330``` si ratio little fish pas bon
```W1329``` si ratio big fish pas bon

## Pondération

```W1320```
```W1322```

Si bait boat
```E1327```
```E1326```

## Controles manquants

### fr.ird.akado.observe.inspector.sample.WeightSampleInspector

Check if the sample weight (m10 and p10) is consistent with the global weight sample.

### fr.ird.akado.observe.inspector.sample.LDLFInspector

Check if the LDLF information for each sample species is consistent.

### fr.ird.akado.observe.inspector.sample.DistributionInspector

Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie dans l'échantillon.

# Cuve

Tous les controles ont été supprimés
