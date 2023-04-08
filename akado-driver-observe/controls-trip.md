# Abstract

Ce document décrit tous les controles implantés pour les marées dans ce module.

| controle              | classe java                                                     |
|:----------------------|:----------------------------------------------------------------|
| Activité              | fr.ird.akado.observe.inspector.trip.ActivityInspector           |
| Temps de pêche        | fr.ird.akado.observe.inspector.trip.FishingTimeInspector        |
| Temps en mer          | fr.ird.akado.observe.inspector.trip.TimeAtSeaInspector          |
| Capacité du navire    | fr.ird.akado.observe.inspector.trip.LandingConsistentInspector  |
| Poids total débarqué  | fr.ird.akado.observe.inspector.trip.LandingTotalWeightInspector |
| Couverture temporelle | fr.ird.akado.observe.inspector.trip.RecoveryTimeInspector       |
| Limite temporelle     | fr.ird.akado.observe.inspector.trip.TemporalLimitInspector      |
| Port                  | fr.ird.akado.observe.inspector.trip.HarbourInspector            |
| Raising factor        | fr.ird.akado.observe.inspector.metatrip.RaisingFactorInspector  |

# fr.ird.akado.observe.inspector.trip.ActivityInspector

| message                                                 | clef de traduction              |
|:--------------------------------------------------------|:--------------------------------|
| MessageDescriptions.E_1018_TRIP_NO_ACTIVITY             | message.trip.noactivities       |
| MessageDescriptions.E_1024_TRIP_HAS_ACTIVITY_NO_LOGBOOK | message.trip.activity.nologbook |

```properties
message.trip.noactivities={0}> Marée[{1}] Il n''y a pas de route dans la marée mais le status de la collecte du livre de bord est « saisie de données autorisée ».
message.trip.activity.nologbook={0}> Marée[{1}] Il y a au moins une route dans la marée mais le status de la collecte du livre de bord est « saisie de données non autorisée ».
```

# fr.ird.akado.observe.inspector.trip.FishingTimeInspector

| message                                      | clef de traduction       |
|:---------------------------------------------|:-------------------------|
| MessageDescriptions.E_1017_TRIP_FISHING_TIME | message.trip.fishingtime |

```properties
message.trip.fishingtime={0}> Marée[{1}] Le «Temps de pêche» sur la marée {2} est différent de la somme des «Temps de pêche» sur les routes {3}.
```

# fr.ird.akado.observe.inspector.trip.TimeAtSeaInspector

| message                                        | clef de traduction        |
|:-----------------------------------------------|:--------------------------|
| MessageDescriptions.E_1010_TRIP_NO_TIME_AT_SEA | message.trip.no.timeatsea |
| MessageDescriptions.E_1011_TRIP_TIME_AT_SEA    | message.trip.timeatsea    |

```properties
message.trip.timeatsea={0}> Marée[{1}] La valeur obtenue pour le champ «time at sea» est {2}, mais la valeur attendue est {3}.
message.trip.no.timeatsea=={0}> Marée[{1}] La valeur obtenue pour le champ «time at sea» est 0.
```

# fr.ird.akado.observe.inspector.trip.LandingConsistentInspector

| message                                           | clef de traduction             |
|:--------------------------------------------------|:-------------------------------|
| MessageDescriptions.W_1002_VESSEL_NO_CAPACITY     | message.vessel.nocapacity      |
| MessageDescriptions.E_1022_TRIP_CAPACITY_OVERRIDE | message.trip.capacity.override |

```properties
message.vessel.nocapacity={0}> La capacité du navire {1} n''est pas remplie.
message.trip.capacity.override={0}> Marée[{1}] Le poids total des captures est {2} tonnes et semble surcharger la capacité du navire qui est de {3} tonnes.
```

# fr.ird.akado.observe.inspector.trip.LandingTotalWeightInspector


| message                                              | clef de traduction              |
|:-----------------------------------------------------|:--------------------------------|
| MessageDescriptions.E_1016_TRIP_LANDING_TOTAL_WEIGHT | message.trip.landingtotalweight |

```properties
message.trip.landingtotalweight={0}> Marée[{1}] La valeur obtenue pour le champ «landing total weight» est {2}  mais la valeur attendue, basée sur les débarquements, est {3} .
```

# fr.ird.akado.observe.inspector.trip.RecoveryTimeInspector

| message                                           | clef de traduction             |
|:--------------------------------------------------|:-------------------------------|
| MessageDescriptions.E_1014_TRIP_ROUTE_NO_ACTIVITY | message.trip.route.no.activity |
| MessageDescriptions.E_1013_TRIP_RECOVERY_TIME     | message.trip.recoverytime      |

```properties
message.trip.route.no.activity={0}> Marée[{1}] Il manque au moins une activité sur la route {2}.
message.trip.recoverytime={0}> Marée[{1}] Il manque au moins une route entre {2} et {3}.
```

# fr.ird.akado.observe.inspector.trip.TemporalLimitInspector

| message                                        | clef de traduction     |
|:-----------------------------------------------|:-----------------------|
| MessageDescriptions.E_1012_TRIP_TEMPORAL_LIMIT | message.trip.startDate |
| MessageDescriptions.E_1012_TRIP_TEMPORAL_LIMIT | message.trip.endDate   |

```properties
message.trip.startDate={0}> Marée[{1}] La date de départ saisie est {2} mais la date de la première activité est {3}.
message.trip.endDate={0}> Marée[{1}] La date de débarquement saisie est {2} mais la date de la dernière activité est {3}.
```

# fr.ird.akado.observe.inspector.trip.HarbourInspector

| message                                               | clef de traduction             |
|:------------------------------------------------------|:-------------------------------|
| MessageDescriptions.E_1023_TRIP_HAS_DIFFERENT_HARBOUR | message.trip.different.harbour |

```properties
message.trip.different.harbour={0}> Marée[{1}] Le port de départ de la marée courante est ({2}). Il diffère du port de débarquement ({3}) de la marée précédente.
```

# fr.ird.akado.observe.inspector.metatrip.RaisingFactorInspector

| message                                        | clef de traduction   |
|:-----------------------------------------------|:---------------------|
| MessageDescriptions.W_1020_TRIP_RAISING_FACTOR | message.trip.rf1     |
| MessageDescriptions.E_1025_TRIP_NO_CATCH       | message.trip.nocatch |

```properties
message.trip.rf1={0}> Marée[{1}] Le facteur d''élévation est {2}.
message.trip.rf1.localmarket={0}> Marée[{1}] Le facteur d''élévation incluant le marché local est {2}.
```
