# Abstract

Ce document décrit tous les controles implantés pour les échantillons dans ce module.

| controle                                                         | classe java                                                         |
|:-----------------------------------------------------------------|:--------------------------------------------------------------------|
| Classe de taille                                                 | fr.ird.akado.observe.inspector.sample.LengthClassInspector          |
| Mesure                                                           | fr.ird.akado.observe.inspector.sample.MeasureInspector              |
| Echantillon sans mesure                                          | fr.ird.akado.observe.inspector.sample.SampleWithoutMeasureInspector |
| Echantillon sans Espèces                                         | fr.ird.akado.observe.inspector.sample.SampleWithoutSpeciesInspector |
| Cuve                                                             | fr.ird.akado.observe.inspector.sample.WellExistsInWellPlanInspector |
| Ratio de petit et de gros poissons                               | fr.ird.akado.observe.inspector.sample.LittleBigInspector            |
| Pondération                                                      | fr.ird.akado.observe.inspector.sample.WeightingInspector            |
| smallsWeight, bigsWeight, totalWeight                            | fr.ird.akado.observe.inspector.sample.WeightSampleInspector         |
| Cohérence smallsWeight, bigsWeight, totalWeight  / SampleSpecies | fr.ird.akado.observe.inspector.sample.LDLFInspector                 |
| Cohérence smallsWeight, bigsWeight / SampleSpeciesMeasure        | fr.ird.akado.observe.inspector.sample.DistributionInspector         |

# fr.ird.akado.observe.inspector.sample.LengthClassInspector

| message                                        | clef de traduction          |
|:-----------------------------------------------|:----------------------------|
| MessageDescriptions.W_1329_SAMPLE_LENGTH_CLASS | message.sample.length.class |

```properties
message.sample.length.class={0}> Echantillon[{1}] L''espèce avec le code {2} a le flag LD1 et une taille de classe égale à {3}.
```
# fr.ird.akado.observe.inspector.sample.MeasureInspector

| message                                         | clef de traduction           |
|:------------------------------------------------|:-----------------------------|
| MessageDescriptions.E_1323_SAMPLE_MEASURE_COUNT | message.sample.measure.count |

```properties
message.sample.measure.count={0}> Echantillon[{1}] Le nombre de mesure est de {2} mais le nombre de poisson mesurée est de {3}.
```
# fr.ird.akado.observe.inspector.sample.SampleWithoutMeasureInspector

| message                                             | clef de traduction       |
|:----------------------------------------------------|:-------------------------|
| MessageDescriptions.E_1312_SAMPLE_NO_SAMPLE_MEASURE | message.sample.nomeasure |

```properties
message.sample.nomeasure={0}> Echantillon[{1}] Le numéro du sous-échantillon {2} de l''espèce «{3}» et du champ «LDLF» {4} n''a pas de mesure.
```
# fr.ird.akado.observe.inspector.sample.SampleWithoutSpeciesInspector

| message                                             | clef de traduction       |
|:----------------------------------------------------|:-------------------------|
| MessageDescriptions.E_1311_SAMPLE_NO_SAMPLE_SPECIES | message.sample.nospecies |

```properties
message.sample.nospecies={0}> Echantillon[{1}] L''échantillon n''a pas d''échantillon espèce.
```
# fr.ird.akado.observe.inspector.sample.WellExistsInWellPlanInspector

| message                                              | clef de traduction                   |
|:-----------------------------------------------------|:-------------------------------------|
| MessageDescriptions.E_1314_SAMPLE_WELL_INCONSISTENCY | message.sample.well.not.in.well.plan |

```properties
message.sample.well.not.in.well.plan={0}> Echantillon[{1}] La cuve {2} de l''échantillon n''est pas définie dans le plan de cuve.
```
# fr.ird.akado.observe.inspector.sample.LittleBigInspector

| message                                                | clef de traduction              |
|:-------------------------------------------------------|:--------------------------------|
| MessageDescriptions.W_1330_SAMPLE_LITTLE_INF_THRESHOLD | message.sample.little.threshold |
| MessageDescriptions.W_1337_SAMPLE_BIG_INF_THRESHOLD    | message.sample.big.threshold    |

```properties
message.sample.little.threshold={0}> Echantillon[{1}] Il y a {2}% de petits poissons (seuil:{3}%) et le ratio de mesures est de LF:{4}% et de LD1:{5}%.
message.sample.big.threshold={0}> Echantillon[{1}]  Il y a {2}% de gros poissons (seuil:{3}%) et le ratio de mesures est de LF:{4}% et de LD1:{5}%.
```
# fr.ird.akado.observe.inspector.sample.WeightingInspector

| message                                               | clef de traduction                 |
|:------------------------------------------------------|:-----------------------------------|
| MessageDescriptions.W_1320_SAMPLE_WEIGHTING_SUP_100   | message.sample.weighting.sup100    |
| MessageDescriptions.W_1322_SAMPLE_WEIGHTING_RATIO     | message.sample.weighting.ratio     |
| MessageDescriptions.E_1327_SAMPLE_WEIGHTING_BB_WEIGHT | message.sample.weighting.bb.weight |
| MessageDescriptions.E_1326_SAMPLE_WEIGHTING_BB_LC     | message.sample.weighting.bb.lc     |

```properties
message.sample.weighting.sup100={0}> Echantillon[{1}] Le poids est de {2}, il est supérieur à 100 T.
message.sample.weighting.ratio={0}> Echantillon[{1}] PS - La pondération est de {2}, et il est inférieur à la valeur poids qui est de {3}, et le ratio est {4}.
message.sample.weighting.bb.weight={0}> Echantillon[{1}] BB - Le poids pesé est {2} et le poids échantillonné est de {3}.
message.sample.weighting.bb.lc={0}> Echantillon[{1}] BB - Le poids pesé est {2} et le poids des débarquements est de {3}.
```
# fr.ird.akado.observe.inspector.sample.WeightSampleInspector

| message                                                | clef de traduction                  |
|:-------------------------------------------------------|:------------------------------------|
| MessageDescriptions.E_1319_SAMPLE_WEIGHT_INCONSISTENCY | message.sample.weight.inconsistency |

```properties
message.sample.weight.inconsistency={0}> Echantillon[{1}] La somme des champs «Minus10Weight» (M10) et «Plus10Weight» (P10) est {2} mais la valeur «Global Weight» (P_E) est {3}.
```
# fr.ird.akado.observe.inspector.sample.LDLFInspector

| message                                                  | clef de traduction                    |
|:---------------------------------------------------------|:--------------------------------------|
| MessageDescriptions.E_1334_SAMPLE_LDLF_SPECIES_FORBIDDEN | message.sample.ldlf.species.forbidden |
| MessageDescriptions.W_1332_SAMPLE_LDLF_P10               | message.sample.ldlf.p10               |
| MessageDescriptions.W_1333_SAMPLE_LDLF_M10               | message.sample.ldlf.m10               |

```properties
message.sample.ldlf.m10={0}> Echantillon[{1}] La valeur LDLF est {2}, la valeur de «Minus10Weight» (M10) est {3} et la valeur de «Global Weight» (P_E) est {4}.
message.sample.ldlf.p10={0}> Echantillon[{1}] La valeur LDLF est {2}, la valeur de «Plus10Weight» (P10) est {3} et la valeur de «Global Weight» (P_E) est {4}.
message.sample.ldlf.species.forbidden={0}> Echantillon[{1}] L''espèce avec le code {2} ne peut pas avoir le flag LDLF a {3}.
```
# fr.ird.akado.observe.inspector.sample.DistributionInspector

| message                                                | clef de traduction                  |
|:-------------------------------------------------------|:------------------------------------|
| MessageDescriptions.E_1335_SAMPLE_DISTRIBUTION_M10_P10 | message.sample.distribution.m10.p10 |

```properties
message.sample.distribution.m10.p10={0}> Echantillon[{1}] Les valeurs de répartition -10/+10 sont incohérentes avec celle de la cuve {2}.
```
