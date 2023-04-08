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
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.selector.TemporalSelector;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.ObserveDataBaseInspector;
import fr.ird.akado.observe.result.AnapoResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.object.Anapo;
import fr.ird.common.DateTimeUtils;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.anapo.dao.PosVMSDAO;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.dao.data.ps.logbook.ActivityDao;
import fr.ird.driver.observe.service.ObserveService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


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

    private static final Logger log = LogManager.getLogger(AnapoActivityConsistentInspector.class);

    public AnapoActivityConsistentInspector() {
        this.description = "Check for each VMS position if an activity exists.";
    }

    @Override
    public Results execute() {

        String[] countriesCodeString = AAProperties.ANAPO_VMS_COUNTRY.split("\\s*\\|\\s*");
        List<Integer> countriesCode = new ArrayList<>();
        for (String countryCodeString : countriesCodeString) {
            countriesCode.add(Integer.valueOf(countryCodeString));
        }
        List<Integer> vesselsCode = new ArrayList<>();
        if (AAProperties.VESSEL_SELECTED != null) {
            String[] vesselsCodeString = AAProperties.VESSEL_SELECTED.split("\\s*\\|\\s*");
            for (String vesselCodeString : vesselsCodeString) {
                vesselsCode.add(Integer.valueOf(vesselCodeString));
            }
        }
        List<Activity> activities = get();
        log.debug(activities);
        log.debug("Size of activities " + activities.size());

        if (activities.isEmpty()) {
            return null;
        }
        Activity lastActivity;

        lastActivity = activities.get(activities.size() - 1);
        log.debug("Last activity " + lastActivity);

        PosVMSDAO dao = ObserveService.getService().getDaoSupplier().getVmsDao();
        List<PosVMS> positions;
        DateTime date = DateTimeUtils.convertDate(lastActivity.getDate());

        TemporalSelector temporalSelector = ObserveDataBaseInspector.getTemporalSelector();
        if (vesselsCode.isEmpty()) {
            if (temporalSelector == null) {
                positions = dao.listDistinctPositionsByDayForAllVessel(date);
            } else {
                if (temporalSelector.getStart() == null) {
                    if (temporalSelector.getEnd() != null) {
                        date = temporalSelector.getEnd();
                    }
                    positions = dao.listDistinctPositionsByDayForAllVessel(date);
                } else {
                    if (temporalSelector.getEnd() != null) {
                        date = temporalSelector.getEnd();
                    }
                    positions = dao.listDistinctPositionsByDayForAllVessel(temporalSelector.getStart(), date);
                }
            }
        } else {
            positions = new ArrayList<>();
            for (Integer vc : vesselsCode) {
                if (temporalSelector == null) {
                    positions.addAll(dao.listDistinctPositionsByDayForVessel(date, vc));
                } else {
                    if (temporalSelector.getStart() == null) {
                        if (temporalSelector.getEnd() != null) {
                            date = temporalSelector.getEnd();
                        }
                        positions.addAll(dao.listDistinctPositionsByDayForVessel(date, vc));
                    } else {
                        if (temporalSelector.getEnd() != null) {
                            date = temporalSelector.getEnd();
                        }
                        positions.addAll(dao.listDistinctPositionsByDayForVessel(temporalSelector.getStart(), date, vc));
                    }
                }
            }
        }

        log.debug("Size of positions " + positions.size());
        Results results = new Results();

        String lastCFRVessel = "";
        DateTime lastDate = new DateTime(1000, 1, 1, 0, 0);
        for (PosVMS posVMS : positions) {

            if (posVMS.getCfrId().equals(lastCFRVessel) && DateTimeUtils.dateEqual(posVMS.getDate(), lastDate)) {
                continue;
            }
            lastCFRVessel = posVMS.getCfrId();
            lastDate = posVMS.getDate();

            ActivityDao activityDAO = new ActivityDao();
            Vessel vessel = ObserveService.getService().getReferentialCache().getOne(Vessel.class, v -> v.getCode().equals(String.valueOf(posVMS.getVesselId())));

            log.debug("Position VMS " + posVMS);
            log.debug("DATE " + DateTimeUtils.convertDate(posVMS.getDate()));
            log.debug("VESSEL " + vessel);

            if (vessel == null) {
                Activity activity = new Activity();
                activity.setDate(posVMS.getDate().toDate());

                Anapo anapo = new Anapo(activity, posVMS.getCfrId());
                AnapoResult r = createResult(MessageDescriptions.I_0003_VESSEL_IS_NOT_IN_DATABASE, anapo,
                                             posVMS.getVesselId(),
                                             "VMS : " + posVMS.getDate().toString(DateTimeUtils.DATE_FORMATTER)
                );
                results.add(r);
                continue;
            }
            boolean existAnActivityFor = activityDAO.isExistAnActivityFor(vessel.getTopiaId(), posVMS.getDate().toDate());
            log.debug("isExistAnActivityFor : " + existAnActivityFor);
            if (countriesCode.contains(vessel.getFleetCountry().getCodeAsInt()) && !existAnActivityFor) {
                Activity a = new Activity();
                a.setVessel(vessel);
                a.setDate(posVMS.getDate().toDate());

                Anapo anapo = new Anapo(a, posVMS.getCfrId());
                anapo.setPosVMS(posVMS);

                AnapoResult r = createResult(MessageDescriptions.E_1228_ANAPO_NO_ACTIVITY, anapo,
                                             a.getID(),
                                             posVMS.getDate());
                results.add(r);
            }
        }
        return results;
    }

}
