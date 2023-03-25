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

import fr.ird.akado.observe.result.model.SampleDataSheet;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

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
public class SampleResult extends Result<Sample> {

    public static List<SampleDataSheet> factory(Sample sample) {
        List<SampleDataSheet> list = new ArrayList<>();
//        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(sample.getVessel(), sample.getLandingDate());
//
//        SampleDataSheet sampleDTO;
//        int vesselCode = sample.getVessel().getCode();
//        String engine = sample.getVessel().getVesselType().getName();
//        String landingDate = sample.getLandingDate().toString(DateTimeUtils.DATE_FORMATTER);
//        boolean tripExist = TripDAO.exist(trip);
//        int hasLogbook = -1;
//        if (trip != null) {
//            hasLogbook = trip.getFlagEnquete();
//        }
//        int harbourCode = 0;
//        if (sample.getLandingHarbour() != null) {
//            harbourCode = sample.getLandingHarbour().getCode();
//        }
//        String sampleType = "?";
//        if (sample.getSampleType() != null) {
//            sampleType = sample.getSampleType().getCode() + " " + sample.getSampleType().getLibelle();
//        }
//
//        double minus10Weight = sample.getMinus10Weight();
//        double plus10Weight = sample.getPlus10Weight();
//        double globalWeight = sample.getGlobalWeight();
//
//        double sPoids = globalWeight;
//        if (sample.getGlobalWeight() == 0) {
//            sPoids = minus10Weight + plus10Weight;
//        }
//        double weightedWeight = WeightingInspector.weightedWeight(sample);
//
//        double sampleSpeciesMeasuredCount = MeasureInspector.sampleSpeciesMeasuredCount(sample);
//        double sampleSpeciesFrequencyCount = MeasureInspector.sampleSpeciesFrequencyCount(sample);
//        String littleFish = "-";
//        String bigFish = "-";
//        if (LittleBigInspector.littleIsInfThreshold(sample) || LittleBigInspector.bigIsInfThreshold(sample)) {
//            littleFish = "" + Utils.round(LittleBigInspector.littleFish(sample), 2);
//            bigFish = "" + Utils.round(LittleBigInspector.bigFish(sample), 2);
//        }
//
//        int sampleNumber = sample.getSampleNumber();
//        boolean hasWell = wellIsConsistent(sample);
//        String distribution = "";
//        if (sample.getWell() != null && DistributionInspector.distributionIsInconsistent(sample)) {
//            distribution = sample.getWell().getID();
//        }
//
//        String activityConsistent = "";
//        String positionConsistent = "";
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
//
//        if (sample.getSampleSpecies().isEmpty()) {
//            sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution);
//            list.add(sampleDTO);
//        } else {
//            List<SampleDataSheet> temporaryList = new ArrayList<>();
//            boolean hasErrorOnSampleSpecies = false;
//            for (SampleSpecies ss : sample.getSampleSpecies()) {
//
//                sampleNumber = ss.getSampleNumber();
//                int subSampleNumber = ss.getSubSampleNumber();
//                String speciesCode = "";
//                if (!SpeciesInspector.specieMustBeSampled(ss.getSpecies().getCode())) {
//                    speciesCode += "??";
//                    hasErrorOnSampleSpecies = true;
//                }
//                speciesCode += ss.getSpecies().getCode();
//
//                String ldlf = "" + ss.getLdlf();
//                if (LDLFInspector.ldlfSpeciesInconsistent(ss.getSpecies(), ss.getLdlf())) {
//                    ldlf = "!!" + ss.getLdlf() + "!!";
//                    hasErrorOnSampleSpecies = true;
//                } else if (LDLFInspector.ldlfP10(sample, ss) || LDLFInspector.ldlfM10(sample, ss)) {
//                    ldlf = "?" + ss.getLdlf() + "";
//                    hasErrorOnSampleSpecies = true;
//                }
//                if (ss.getSampleSpeciesFrequencys().isEmpty()) {
//                    sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, speciesCode);
//                    temporaryList.add(sampleDTO);
//                } else {
//                    boolean sampleIsAdded = false;
//                    boolean hasErrorOnSampleSpeciesFrequency = false;
//                    String lengthClassCount = "-";
//                    String ssfSpeciesCode = "";
//                    for (SampleSpeciesFrequency sampleSpeciesFrequency : ss.getSampleSpeciesFrequencys()) {
//                        hasErrorOnSampleSpeciesFrequency = false;
//                        lengthClassCount = sampleSpeciesFrequency.getLengthClass() + "(" + sampleSpeciesFrequency.getNumber() + ")";
//                        ssfSpeciesCode = "";
//                        if (!SpeciesInspector.specieMustBeSampled(ss.getSpecies().getCode())) {
//                            ssfSpeciesCode += "?";
//                            hasErrorOnSampleSpeciesFrequency = true;
//                        }
//                        ssfSpeciesCode += ss.getSpecies().getCode();
//                        if (LengthClassInspector.lengthClassLimits(sampleSpeciesFrequency)) {
//                            ssfSpeciesCode += " ?LD1";
//                            hasErrorOnSampleSpeciesFrequency = true;
//                        }
//
//                        if (hasErrorOnSampleSpeciesFrequency) {
//                            sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, ssfSpeciesCode, lengthClassCount, ldlf);
//                            temporaryList.add(sampleDTO);
//                            sampleIsAdded = true;
//                        }
//                    }
//                    if (!sampleIsAdded) {
//                        lengthClassCount = "-";
//                        sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, speciesCode, lengthClassCount, ldlf);
//                        temporaryList.add(sampleDTO);
//                    }
//                }
//            }
//            if (hasErrorOnSampleSpecies) {
//                list.addAll(temporaryList);
//            } else {
//                sampleDTO = new SampleDataSheet(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution);
//                sampleDTO.setSpeciesCode("-");
//                sampleDTO.setLengthClassCount("-");
//                sampleDTO.setLdlf("-");
////                sampleDTO.setSubSampleNumber();
//                list.add(sampleDTO);
//            }
//        }

        return list;
    }

    public SampleResult() {
        super();
    }

    @Override
    public List extractResults() {
        List<Object> list = new ArrayList<>();

        Sample sample = get();
        if (sample == null) {
            return list;
        }
        list.addAll(factory(sample));

        return list;
    }

}
