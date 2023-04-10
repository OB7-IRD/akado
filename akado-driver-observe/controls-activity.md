# Abstract

Ce document décrit tous les controles implantés pour les activités dans ce module.

| controle                                                                            | classe java                                                      |
|:------------------------------------------------------------------------------------|:-----------------------------------------------------------------|
| Contexte de pêche                                                                   | fr.ird.akado.observe.inspector.activity.FishingContextInspector  |
| Opération                                                                           | fr.ird.akado.observe.inspector.activity.OperationInspector       |
| Position                                                                            | fr.ird.akado.observe.inspector.activity.PositionInspector        |
| Quadrant                                                                            | fr.ird.akado.observe.inspector.activity.QuadrantInspector        |
| Poids total des captures                                                            | fr.ird.akado.observe.inspector.activity.WeightInspector          |
| Cohérence zone FPA / position activité                                              | fr.ird.akado.observe.inspector.activity.PositionInEEZInspector   |
| Activité de pêche sans zone FPA                                                     | fr.ird.akado.observe.inspector.activity.EEZInspector             |
| Cohérence somme des captures élémentaires / somme des poids pondérés des calées ech | fr.ird.akado.observe.inspector.activity.WeightingSampleInspector |
| Coordonnées nulles                                                                  | fr.ird.akado.observe.inspector.activity.NullPositionInspector    |

TODO Anapo
```properties
message.activity.no.trace.vms={0}> Activité[{1}] Il n''y a pas de positions VMS dans la base de données pour la date d''activité.
message.no.activity.trace.vms={0}> Il y a des positions VMS datant du {1} dans la base de données mais il n''y a pas de d''activités à cette date.
message.activity.trace.vms.match={0}> Activité[{1}] Il y a {2} positions VMS correspondant à la position de l''activité.
#message.activity.trace.vms.cl2={0}> Activité[{1}] No VMS position match with the activity position in CL1. The coordinate {3} is available to correct the activity position with {3} reliability.
message.activity.trace.vms.cl2={0}> Activité[{1}] Aucune position VMS ne correspond à la position de l''activité en CL1 et CL2 {2}, il y a {3} positions possible pour corriger la position de l''activité.
message.activity.trace.vms.no.match={0}> Activité[{1}] Aucune position VMS ne correspond à la position de l''activité.
```

# fr.ird.akado.observe.inspector.activity.FishingContextInspector

| message                                                                                  | clef de traduction                                                    |
|:-----------------------------------------------------------------------------------------|:----------------------------------------------------------------------|
| MessageDescriptions.E_1219_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY                        | message.activity.fishing.context.null                                 |
| MessageDescriptions.E_1240_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_ARTIFICIAL_SCHOOL_TYPE | message.activity.fishing.context.inconsistency.artificial.school.type |
| MessageDescriptions.E_1241_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_FREE_SCHOOL_TYPE       | message.activity.fishing.context.inconsistency.free.school.type       |

```properties
message.activity.fishing.context.inconsistency.artificial.school.type={0}> Activité[{1}] Le champ «Type de banc» est {2} mais aucun système observé sur banc objet n''a été détecté.
message.activity.fishing.context.inconsistency.free.school.type={0}> Activité[{1}] Le champ «Type de banc» est {2} mais au moins un système observé sur banc objet a été détecté.
message.activity.fishing.context.null={0}> Activité[{1}] Aucun système observé n'est renseigné.
```
# fr.ird.akado.observe.inspector.activity.OperationInspector

| message                                                                                                    | clef de traduction                                                                      |
|:-----------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|
| MessageDescriptions.E_1220_ACTIVITY_NOT_FISHING_OPERATION_INCONSISTENCY_WITH_SET_COUNT                     | message.activity.not.fishing.operation.inconsistency.with.set.count                     |
| MessageDescriptions.E_1218_ACTIVITY_FISHING_OPERATION_INCONSISTENCY_WITH_SET_COUNT                         | message.activity.fishing.operation.inconsistency.with.set.count                         |
| MessageDescriptions.E_1222_ACTIVITY_NOT_FISHING_OPERATION_INCONSISTENCY_WITH_CATCH_WEIGHT                  | message.activity.not.fishing.operation.inconsistency.with.catch.weight                  |
| MessageDescriptions.E_1216_ACTIVITY_FISHING_OPERATION_INCONSISTENCY_WITH_CATCH_WEIGHT                      | message.activity.fishing.operation.inconsistency.with.catch.weight                      |
| MessageDescriptions.W_1215_ACTIVITY_FISHING_OPERATION_INCONSISTENCY_CATCH_WEIGHT                           | message.activity.fishing.operation.inconsistency.catch.weight                           |
| MessageDescriptions.E_1225_ACTIVITY_FISHING_OPERATION_AND_REASON_FOR_NO_FISHING_INCONSISTENCY_CATCH_WEIGHT | message.activity.fishing.operation.and.reason.for.no.fishing.inconsistency.catch.weight |

```properties
message.activity.not.fishing.operation.inconsistency.with.set.count={0}> Activité[{1}] L''opération n''est pas un coup de pêche ( «operation code» {2}) , mais la valeur du champs « setCount » n''est pas nulle ( valur : {3} )
message.activity.not.fishing.operation.inconsistency.with.catch.weight={0}> Activité[{1}] L''opération n''est pas un coup de pêche ( «operation code» {2}) , mais la valeur du champs « poids total » n''est pas nulle ( valur : {3} )
message.activity.fishing.operation.inconsistency.with.set.count={0}> Activité[{1}] L''opération est un coup de pêche ( «operation code» {2}) , mais la valeur du champs « setCount » est nulle.
message.activity.fishing.operation.inconsistency.with.catch.weight={0}> Activité[{1}] L''opération est un coup de pêche ( «operation code» {2}) , mais la valeur du champs « poids total » est nulle.
message.activity.fishing.operation.inconsistency.catch.weight={0}> Activité[{1}] L''opération est un coup de pêche ( «operation code» {2}) , mais la valeur du champs « poids total » ( valeur : {3} ) est supérieure à cinq tonnes.
message.activity.fishing.operation.and.reason.for.no.fishing.inconsistency.catch.weight={0}> Activité[{1}] L''opération est un coup de pêche ( «operation code» {2} ) et le champs « raison de non coup de pêche » est renseigné ( valur : {3} ) , mais la valeur du champs « poids total » ( valeur : {4} ) n''est pas nulle.
```
# fr.ird.akado.observe.inspector.activity.PositionInspector

| message                                                   | clef de traduction                   |
|:----------------------------------------------------------|:-------------------------------------|
| MessageDescriptions.E_1214_ACTIVITY_POSITION_NOT_IN_OCEAN | message.activity.position.notinocean |
| MessageDescriptions.W_1217_ACTIVITY_POSITION_WEIRD        | message.activity.position.weird      |
| MessageDescriptions.E_1212_ACTIVITY_OCEAN_INCONSISTENCY   | message.activity.ocean               |

```properties
message.activity.position.notinocean={0}> Activité[{1}] La position {2} localise l''activité dans le pays: «{3}».
message.activity.position.weird={0}> Activité[{1}] La position {2} semble étrange. La position n''est ni dans l''océan ni dans un pays.
message.activity.ocean={0}> Activité[{1}] La valeur obtenue pour le champ «ocean» est {2} mais la valeur attendue est {3}, basé sur la position de l''activité.
```
# fr.ird.akado.observe.inspector.activity.QuadrantInspector

| message                                                    | clef de traduction        |
|:-----------------------------------------------------------|:--------------------------|
| MessageDescriptions.E_1213_ACTIVITY_QUADRANT_INCONSISTENCY | message.activity.quadrant |

```properties
message.activity.quadrant={0}> Activité[{1}] La valeur obtenue pour le champ «quadrant» est {2} mais le champ «ocean code»  de la marée est l''«Océan Indien».
```
# fr.ird.akado.observe.inspector.activity.WeightInspector

| message                                                | clef de traduction                  |
|:-------------------------------------------------------|:------------------------------------|
| MessageDescriptions.E_1210_ACTIVITY_TOTAL_CATCH_WEIGHT | message.activity.totalcaptureweight |

```properties
message.activity.totalcaptureweight={0}> Activité[{1}] La valeur obtenue pour le champ «total capture weight» est {2}  mais la valeur attendue, basée sur les captures élémentaires, est {3}.
```
# fr.ird.akado.observe.inspector.activity.PositionInEEZInspector

| message                                                           | clef de traduction                           |
|:------------------------------------------------------------------|:---------------------------------------------|
| MessageDescriptions.W_1235_ACTIVITY_POSITION_IN_EEZ_INCONSISTENCY | message.activity.position.eez.inconsistency  |

```properties
message.activity.position.eez.inconsistency={0}> Activité[{1}] La ZEE déclarée ({2}) semble incorrecte par rapport à la position déclarée ({3}), la ZEE déduite de la position est ({4}).
```
# fr.ird.akado.observe.inspector.activity.EEZInspector

| message                                                         | clef de traduction                           |
|:----------------------------------------------------------------|:---------------------------------------------|
| MessageDescriptions.W_1232_ACTIVITY_OPERATION_EEZ_INCONSISTENCY | message.activity.operation.eez.inconsistency |

```properties
message.activity.operation.eez.inconsistency={0}> Activité[{1}] L''activité est une opération de pêche ( «operation code» {2} ) mais la zone FPA n''est pas définie.
```
# fr.ird.akado.observe.inspector.activity.WeightingSampleInspector

**FIXME** pour calculer la somme des captures élémentaires, on filtre sur certaines espèces, est-ce normal ?

| message                                                                 | clef de traduction                                   |
|:------------------------------------------------------------------------|:-----------------------------------------------------|
| MessageDescriptions.E_1233_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT | message.activity.catch.weight.sample.weighted.weight |

```properties
message.activity.catch.weight.sample.weighted.weight={0}> Activité[{1}] La somme des captures élementaires ({2}) et la ponderation des échantillons ({3}) sont différentes.
```
# fr.ird.akado.observe.inspector.activity.NullPositionInspector

| message                                                           | clef de traduction                      |
|:------------------------------------------------------------------|:----------------------------------------|
| MessageDescriptions.E_1231_ACTIVITY_POSITION_INCONSISTENCY | message.activity.position.inconsistency |

```properties
message.activity.position.inconsistency={0}> Activité[{1}] L''activité ( «operation code» {2} ) n''a pas de coordinnées, ce qui est interdit sauf pour une opération sur objet flottant avec perte/fin de transmission ou fin d''utilisation de balise.
```