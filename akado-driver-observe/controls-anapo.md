# Abstract

Ce document décrit tous les controles implantés pour les activités via anapo dans ce module.

| controle          | classe java                                                           |
|:------------------|:----------------------------------------------------------------------|
| Contexte de pêche | fr.ird.akado.observe.inspector.anapo.AnapoActivityConsistentInspector |
| Contexte de pêche | fr.ird.akado.observe.inspector.anapo.AnapoInspector                   |

# fr.ird.akado.observe.inspector.anapo.AnapoActivityConsistentInspector

| message                                              | clef de traduction        |
|:-----------------------------------------------------|:--------------------------|
| MessageDescriptions.I_0003_VESSEL_IS_NOT_IN_DATABASE | avdth.vessel.not.in.db    |
| MessageDescriptions.E1228_ANAPO_NO_ACTIVITY         | message.anapo.no.activity |

```properties
avdth.vessel.not.in.db={0}> Le navire {1} n''est pas répertorié dans la base AVDTH ({2}).
message.anapo.no.activity={0}> Activité[{1}] Il y a des positions VMS pour le {2} mais aucune activité.
```
# fr.ird.akado.observe.inspector.anapo.AnapoInspector

| message                                                     | clef de traduction                            |
|:------------------------------------------------------------|:----------------------------------------------|
| MessageDescriptions.I1221_ACTIVITY_NO_TRACE_VMS            | message.activity.no.trace.vms                 |
| MessageDescriptions.W1223_ACTIVITY_TRACE_VMS_CL2           | message.activity.trace.vms.cl2                |
| MessageDescriptions.I1224_INCONSISTENCY_VMS_POSITION_COUNT | message.trace.vms.inconsistent.position.count |
| MessageDescriptions.E1227_ACTIVITY_TRACE_VMS_NO_MATCH      | message.activity.trace.vms.no.match           |

```properties
message.activity.no.trace.vms={0}> Activité[{1}] Il n''y a pas de positions VMS dans la base de données pour la date d''activité.
message.activity.trace.vms.cl2={0}> Activité[{1}] Aucune position VMS ne correspond à la position de l''activité en CL1 et CL2 {2}, il y a {3} positions possible pour corriger la position de l''activité.
message.trace.vms.inconsistent.position.count={0}> Activité[{1}] Le nombre de position VMS est de {2} mais il devrait être supérieur à 20.
message.activity.trace.vms.no.match={0}> Activité[{1}] Aucune position VMS ne correspond à la position de l''activité.
```
