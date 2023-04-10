/*
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.observe.result;

import fr.ird.akado.core.common.MessageDescription;
import fr.ird.akado.observe.WithTrip;
import fr.ird.akado.observe.inspector.sample.DistributionInspector;
import fr.ird.akado.observe.inspector.sample.LDLFInspector;
import fr.ird.akado.observe.inspector.sample.LengthClassInspector;
import fr.ird.akado.observe.inspector.sample.LittleBigInspector;
import fr.ird.akado.observe.inspector.sample.MeasureInspector;
import fr.ird.akado.observe.inspector.sample.ObserveSampleInspector;
import fr.ird.akado.observe.inspector.sample.WeightingInspector;
import fr.ird.akado.observe.result.model.SampleDataSheet;
import fr.ird.common.DateUtils;
import fr.ird.common.Utils;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpeciesMeasure;
import fr.ird.driver.observe.business.referential.common.Species;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente les résultats d'une analyse pour l'échantillonnage.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleResult extends Result<Sample> implements WithTrip {

    private Trip trip;

    public SampleResult(Sample datum, MessageDescription messageDescription) {
        super(datum, messageDescription);
    }

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public List<SampleDataSheet> extractResults() {
        List<SampleDataSheet> list = new ArrayList<>();
        Sample sample = get();
        if (sample == null) {
            return list;
        }
        list.addAll(factory(sample));
        return list;
    }

    public List<SampleDataSheet> factory(Sample sample) {
        List<SampleDataSheet> list = new ArrayList<>();
        SampleDataSheet sampleDTO;
        String vesselCode = trip.getVessel().getCode();
        String engine = trip.getVessel().getVesselType().getLabel2();
        String landingDate = DateUtils.formatDate(trip.getEndDate());
        boolean hasLogbook = trip.hasLogbook();
        String harbourCode = trip.getLandingHarbour().getCode();
        String sampleType = "?";
        if (sample.getSampleType() != null) {
            sampleType = sample.getSampleType().getCode() + " " + sample.getSampleType().getLabel2();
        }

        double minus10Weight = sample.getSmallsWeight();
        double bigsWeight = sample.getBigsWeight();
        double totalWeight = sample.getTotalWeight();

        double sPoids = totalWeight;
        if (totalWeight == 0) {
            sPoids = minus10Weight + (double) bigsWeight;
        }
        double weightedWeight = WeightingInspector.weightedWeight(sample);

        double sampleSpeciesMeasuredCount = MeasureInspector.sampleSpeciesMeasuredCount(sample);
        double sampleSpeciesFrequencyCount = MeasureInspector.sampleSpeciesFrequencyCount(sample);
        String littleFish = "-";
        String bigFish = "-";
        if (LittleBigInspector.littleIsInfThreshold(sample) || LittleBigInspector.bigIsInfThreshold(sample)) {
            littleFish = "" + Utils.round(LittleBigInspector.littleFish(sample), 2);
            bigFish = "" + Utils.round(LittleBigInspector.bigFish(sample), 2);
        }

        int sampleNumber = sample.getNumber();
        // In ObServe Sample.well is mandatory
        boolean hasWell = true;
        String distribution = "";
        if (sample.getWell() != null && DistributionInspector.distributionIsInconsistent(getTrip(), sample)) {
            distribution = sample.getWell();
        }

        String activityConsistent = "";
        String positionConsistent = "";
        //FIXME
//        if (sample.getSampleWells().isEmpty()) {
//            activityConsistent = "-";
//            positionConsistent = "-";
//        } else {
//            for (SampleWell sampleWell : sample.getSampleWells()) {
//                if (!ActivityConsistentInspector.activityConsistent(trip, sampleWell)) {
//                    activityConsistent += " ? " + sampleWell.getActivityDate().toString(DateTimeUtils.DATE_FORMATTER) + ", " + sampleWell.getActivityNumber() + "|\n";
//                    if (trip != null) {
//                        for (Activity a : trip.getActivites()) {
//                            if (DateTimeUtils.dateEqual(sampleWell.getActivityDate(), a.getDate())
//                                    && sampleWell.getActivityNumber() == a.getNumber()) {
//
//                                positionConsistent += sampleWell.getActivityDate().toString(DateTimeUtils.DATE_FORMATTER) + ", " + sampleWell.getActivityNumber() + ": ";
//                                if (a.getQuadrant() != sampleWell.getQuadrant()) {
//                                    positionConsistent += "? ";
//                                }
//                                positionConsistent += sampleWell.getQuadrant() + " ";
//                                if (a.getLatitude() != sampleWell.getLatitude()) {
//                                    positionConsistent += "? ";
//                                }
//                                positionConsistent += sampleWell.getLatitude() + " ";
//                                if (a.getLongitude() != sampleWell.getLongitude()) {
//                                    positionConsistent += "? ";
//                                }
//                                positionConsistent += sampleWell.getLongitude() + "";
//                            }
//                        }
//                    }
//                }
//            }
//        }

        if (sample.getSampleSpecies().isEmpty()) {
            sampleDTO = new SampleDataSheet(vesselCode,
                                            engine,
                                            landingDate,
                                            hasLogbook,
                                            harbourCode,
                                            bigFish,
                                            littleFish,
                                            activityConsistent,
                                            positionConsistent,
                                            sampleSpeciesFrequencyCount,
                                            sampleSpeciesMeasuredCount,
                                            weightedWeight,
                                            sPoids,
                                            totalWeight,
                                            bigsWeight,
                                            minus10Weight,
                                            sampleType,
                                            hasWell,
                                            sampleNumber,
                                            distribution);
            list.add(sampleDTO);
        } else {
            List<SampleDataSheet> temporaryList = new ArrayList<>();
            boolean hasErrorOnSampleSpecies = false;
            for (SampleSpecies ss : sample.getSampleSpecies()) {

                sampleNumber = ss.getSubSampleNumber();
                int subSampleNumber = ss.getSubSampleNumber();
                String speciesCode = "";
                Species species = ss.getSpecies();
                if (!ObserveSampleInspector.specieMustBeSampled(species.getCode())) {
                    speciesCode += "??";
                    hasErrorOnSampleSpecies = true;
                }
                speciesCode += species.getCode();

                String ldlf = "" + ss.getSizeMeasureType().getLabel2();
                if (LDLFInspector.ldlfSpeciesInconsistent(ss)) {
                    ldlf = "!!" + ss.getSizeMeasureType().getLabel2() + "!!";
                    hasErrorOnSampleSpecies = true;
                } else if (LDLFInspector.ldlfP10(sample, ss) || LDLFInspector.ldlfM10(sample, ss)) {
                    ldlf = "?" + ss.getSizeMeasureType().getLabel2() + "";
                    hasErrorOnSampleSpecies = true;
                }
                if (ss.getSampleSpeciesMeasure().isEmpty()) {
                    sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, totalWeight, bigsWeight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, speciesCode);
                    temporaryList.add(sampleDTO);
                } else {
                    boolean sampleIsAdded = false;
                    boolean hasErrorOnSampleSpeciesFrequency = false;
                    String lengthClassCount = "-";
                    String ssfSpeciesCode = "";
                    for (SampleSpeciesMeasure sampleSpeciesFrequency : ss.getSampleSpeciesMeasure()) {
                        hasErrorOnSampleSpeciesFrequency = false;
                        lengthClassCount = sampleSpeciesFrequency.getSizeClass() + "(" + sampleSpeciesFrequency.getCount() + ")";
                        ssfSpeciesCode = "";
                        if (!ObserveSampleInspector.specieMustBeSampled(species.getCode())) {
                            ssfSpeciesCode += "?";
                            hasErrorOnSampleSpeciesFrequency = true;
                        }
                        ssfSpeciesCode += species.getCode();
                        if (LengthClassInspector.lengthClassLimits(species, sampleSpeciesFrequency)) {
                            ssfSpeciesCode += " ?LD1";
                            hasErrorOnSampleSpeciesFrequency = true;
                        }

                        if (hasErrorOnSampleSpeciesFrequency) {
                            sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, totalWeight, bigsWeight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, ssfSpeciesCode, lengthClassCount, ldlf);
                            temporaryList.add(sampleDTO);
                            sampleIsAdded = true;
                        }
                    }
                    if (!sampleIsAdded) {
                        lengthClassCount = "-";
                        sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, totalWeight, bigsWeight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, speciesCode, lengthClassCount, ldlf);
                        temporaryList.add(sampleDTO);
                    }
                }
            }
            if (hasErrorOnSampleSpecies) {
                list.addAll(temporaryList);
            } else {
                sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, totalWeight, bigsWeight, minus10Weight, sampleType, hasWell, sampleNumber, distribution);
                sampleDTO.setSpeciesCode("-");
                sampleDTO.setLengthClassCount("-");
                sampleDTO.setLdlf("-");
//                sampleDTO.setSubSampleNumber();
                list.add(sampleDTO);
            }
        }

        return list;
    }

}
