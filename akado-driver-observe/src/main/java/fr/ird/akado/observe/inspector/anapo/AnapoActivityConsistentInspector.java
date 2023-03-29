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
package fr.ird.akado.observe.inspector.anapo;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;


/**
 * The AnapoActivityConsistentInspector class check for each VMS position if an
 * activity exists. This inspector implements ANAPO functionalities.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveAnapoActivityListInspector.class)
public class AnapoActivityConsistentInspector extends ObserveAnapoActivityListInspector {

    public AnapoActivityConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check for each VMS position if an activity exists.";
    }

    //FIXME do this one
    @Override
    public Results execute() {
        Results results = new Results();
//
//        LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug(name + " " + description);
//        if (AAProperties.ANAPO_INSPECTOR != null
//                && AAProperties.ANAPO_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
//            return results;
//        }
//        String[] countriesCodeString = AAProperties.ANAPO_VMS_COUNTRY.split("\\s*\\|\\s*");
//        ArrayList countriesCode = new ArrayList<>();
//        for (String countryCodeString : countriesCodeString) {
//            countriesCode.add(Integer.valueOf(countryCodeString));
//        }
//        ArrayList<Integer> vesselsCode = new ArrayList<>();
//        if (AAProperties.VESSEL_SELECTED != null) {
//            String[] vesselsCodeString = AAProperties.VESSEL_SELECTED.split("\\s*\\|\\s*");
//            for (String vesselCodeString : vesselsCodeString) {
//                vesselsCode.add(Integer.valueOf(vesselCodeString));
//            }
//        }
//        LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("" + get());
//        List<Activity> activities = get();
//        LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("Size of activities " + activities.size());
//
//        Activity lastActivity = null;
//
//        if (activities.isEmpty()) {
//            return results;
//        }
//
//        lastActivity = activities.get(activities.size() - 1);
//        LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("Last activity " + lastActivity);
//
//        AnapoResult r;
//        Anapo anapo;
//
//        PosVMSDAO pvmsdao = new PosVMSDAO();
//        List<PosVMS> positions = null;
//        DateTime date = lastActivity.getDate();
//
//        if (vesselsCode.isEmpty()) {
//            if (AvdthInspector.getTemporalSelector() == null) {
//                positions = pvmsdao.listDistinctPositionsByDayForAllVessel(date);
//            } else {
//                if (AvdthInspector.getTemporalSelector().getStart() == null) {
//                    if (AvdthInspector.getTemporalSelector().getEnd() != null) {
//                        date = AvdthInspector.getTemporalSelector().getEnd();
//                    }
//                    positions = pvmsdao.listDistinctPositionsByDayForAllVessel(date);
//                } else {
//                    if (AvdthInspector.getTemporalSelector().getEnd() != null) {
//                        date = AvdthInspector.getTemporalSelector().getEnd();
//                    }
//                    positions = pvmsdao.listDistinctPositionsByDayForAllVessel(AvdthInspector.getTemporalSelector().getStart(), date);
//                }
//            }
//        } else {
//            positions = new ArrayList<>();
//            for (Integer vc : vesselsCode) {
//                if (AvdthInspector.getTemporalSelector() == null) {
//                    positions.addAll(pvmsdao.listDistinctPositionsByDayForVessel(date, vc));
//                } else {
//                    if (AvdthInspector.getTemporalSelector().getStart() == null) {
//                        if (AvdthInspector.getTemporalSelector().getEnd() != null) {
//                            date = AvdthInspector.getTemporalSelector().getEnd();
//                        }
//                        positions.addAll(pvmsdao.listDistinctPositionsByDayForVessel(date, vc));
//                    } else {
//                        if (AvdthInspector.getTemporalSelector().getEnd() != null) {
//                            date = AvdthInspector.getTemporalSelector().getEnd();
//                        }
//                        positions.addAll(pvmsdao.listDistinctPositionsByDayForVessel(AvdthInspector.getTemporalSelector().getStart(), date, vc));
//                    }
//                }
//            }
//        }
//
//        LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("Size of postions " + positions.size());
//        String lastCFRVessel = "";
//        DateTime lastDate = new DateTime(1000, 1, 1, 0, 0);
//        for (PosVMS posVMS : positions) {
//
//            if (posVMS.getCfrId().equals(lastCFRVessel) && DateTimeUtils.dateEqual(posVMS.getDate(), lastDate)) {
//                continue;
//            }
//            lastCFRVessel = posVMS.getCfrId();
//            lastDate = posVMS.getDate();
//
//            ActivityDAO activityDAO = new ActivityDAO();
//            VesselDAO vesselDAO = new VesselDAO();
//            Vessel vessel = vesselDAO.findVesselByCode(posVMS.getVesselId());
//
//            LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("Position VMS " + posVMS.toString());
//            LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("DATE " + DateTimeUtils.convertDate(posVMS.getDate()));
//            LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("VESSEL " + vessel);
//            LogService.getService(AnapoActivityConsistentInspector.class).logApplicationDebug("isExistAnActivityFor : " + activityDAO.isExistAnActivityFor(vessel, posVMS.getDate()));
//
//            if (vessel == null) {
//                Activity a = new Activity();
//                a.setVessel(vessel);
//                a.setDate(posVMS.getDate());
//
//                anapo = new Anapo(a, posVMS.getCfrId());
//                r = new AnapoResult();
//                r.set(anapo);
//                r.setMessageType(Message.INFO);
//                r.setMessageCode(Constant.CODE_VESSEL_IS_NOT_IN_DATABASE);
//                r.setMessageLabel(Constant.LABEL_VESSEL_IS_NOT_IN_DATABASE);
//
//                ArrayList parameters = new ArrayList<>();
//                parameters.add(posVMS.getVesselId());
//                parameters.add("VMS : " + posVMS.getDate().toString(DateTimeUtils.DATE_FORMATTER));
//                r.setMessageParameters(parameters);
//                results.add(r);
//            } else if (vessel.getCountry() != null && countriesCode.contains(vessel.getCountry().getCode())
//                    && !activityDAO.isExistAnActivityFor(vessel, posVMS.getDate())) {
//                Activity a = new Activity();
//                a.setVessel(vessel);
//                a.setDate(posVMS.getDate());
//
//                anapo = new Anapo(vessel);
//                anapo.setPosVMS(posVMS);
//
//                r = new AnapoResult();
//                r.set(anapo);
//                r.setMessageType(Message.ERROR);
//                r.setMessageCode(CODE_ANAPO_NO_ACTIVITY);
//                r.setMessageLabel(LABEL_ANAPO_NO_ACTIVITY);
//
//                ArrayList parameters = new ArrayList<>();
//                parameters.add(a.getID());
//                parameters.add(posVMS.getDate());
//                r.setMessageParameters(parameters);
//                results.add(r);
//            }
//
//        }
        return results;
    }

}
