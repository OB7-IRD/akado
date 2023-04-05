# Abstract

Ce document décrit tous les controles implantés dans ce module.

# Marée

| controle              | classe java                                                     | Codes                    | Avancement |
|:----------------------|:----------------------------------------------------------------|:-------------------------|:-----------|
| Activité              | fr.ird.akado.observe.inspector.trip.ActivityInspector           | ```E1018```,```E1024```  | 100%       |
| Temps de peche        | fr.ird.akado.observe.inspector.trip.FishingTimeInspector        | ```E1017```              | 100%       |
| Temps en mer          | fr.ird.akado.observe.inspector.trip.TimeAtSeaInspector          | ```E1011```              |            |
| Capacité du navire    | fr.ird.akado.observe.inspector.trip.LandingConsistentInspector  | ```E1002```, ```E1022``` |            |
| Poids total débarqué  | fr.ird.akado.observe.inspector.trip.LandingTotalWeightInspector | ```E1016```              |            |
| Distance              | ???                                                             |                          |            |
| Couverture temporelle | fr.ird.akado.observe.inspector.trip.RecoveryTimeInspector       | ```E1013```              |            |
| Limite temporelle     | fr.ird.akado.observe.inspector.trip.TemporalLimitInspector      | ```E1012```              |            |
| Port                  | fr.ird.akado.observe.inspector.trip.HarbourInspector            | ```E1023```              |            |
| Raising factor        | fr.ird.akado.observe.inspector.metatrip.RaisingFactorInspector  | ```E1023```, ```W1020``` |            |

## Distance (Warning)

**? non trouvé**

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
