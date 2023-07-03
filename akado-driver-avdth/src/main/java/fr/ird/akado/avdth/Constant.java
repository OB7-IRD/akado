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
package fr.ird.akado.avdth;

import fr.ird.common.message.ErrorMessage;
import fr.ird.common.message.Message;

/**
 * The constant properties for AKaDo-AVDTH. All messages code and label useful
 * for the application.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 26 mai 2014
 */
public class Constant {

    public static String AKADO_AVDTH_BUNDLE_PROPERTIES = "AVDTH-inspector";

    public static final String CODE_INFO_DATABASE = "0001";
    public static final String LABEL_INFO_DATABASE = "database.info";
    public static final String CODE_DATABASE_NOT_COMPATIBLE = "0002";
    public static final String LABEL_DATABASE_NOT_COMPATIBLE = "database.version.not.compatible";

    public static final String CODE_VESSEL_IS_NOT_IN_DATABASE = "0003";
    public static final String LABEL_VESSEL_IS_NOT_IN_DATABASE = "avdth.vessel.not.in.db";

    //    public static final String CODE_ = "";
    //    public static final String LABEL_ = "";
    public static final String CODE_TRIP_ERROR_OR_NULL = "1001";
    public static final String LABEL_TRIP_ERROR_OR_NULL = "message.trip.null";

    public static final String CODE_VESSEL_NO_CAPACITY = "1002";
    public static final String LABEL_VESSEL_NO_CAPACITY = "message.vessel.nocapacity";

    public static final String CODE_TRIP_TIME_AT_SEA = "1011";
    public static final String LABEL_TRIP_TIME_AT_SEA = "message.trip.timeatsea";
    public static final String CODE_TRIP_START_DATE = "1012";
    public static final String LABEL_TRIP_START_DATE = "message.trip.startDate";
    public static final String CODE_TRIP_END_DATE = "1026";
    public static final String LABEL_TRIP_END_DATE = "message.trip.endDate";
    public static final String CODE_TRIP_RECOVERY_TIME = "1013";
    public static final String LABEL_TRIP_RECOVERY_TIME = "message.trip.recoverytime";
    public static final String CODE_TRIP_LOCH = "1014";
    public static final String LABEL_TRIP_LOCH = "message.trip.loch";
    public static final String CODE_TRIP_LOCH_UNKNOWN = "1015";
    public static final String LABEL_TRIP_LOCH_UNKNOWN = "message.trip.loch.unknown";
    public static final String CODE_TRIP_LANDING_TOTAL_WEIGHT = "1016";
    public static final String LABEL_TRIP_LANDING_TOTAL_WEIGHT = "message.trip.landingtotalweight";
    public static final String CODE_TRIP_FISHING_TIME = "1017";
    public static final String LABEL_TRIP_FISHING_TIME = "message.trip.fishingtime";
    public static final String CODE_TRIP_NO_ACTIVITY = "1018";
    public static final String LABEL_TRIP_NO_ACTIVTY = "message.trip.noactivities";
    public static final String CODE_TRIP_TOTAL_CATCH_WEIGHT = "1019";
    public static final String LABEL_TRIP_TOTAL_CATCH_WEIGHT = "message.trip.totalcatchweight";

    public static final String CODE_TRIP_HAS_ACTIVITY_NO_LOGBOOK = "1024";
    public static final String LABEL_TRIP_HAS_ACTIVTY_NO_LOGBOOK = "message.trip.activity.nologbook";

    public static final String CODE_TRIP_RAISING_FACTOR = "1020";
    public static final String LABEL_TRIP_RAISING_FACTOR = "message.trip.rf1";
    public static final String CODE_TRIP_RAISING_FACTOR_WITH_LOCAL_MARKET = "1021";
    public static final String LABEL_TRIP_RAISING_FACTOR_WITH_LOCAL_MARKET = "message.trip.rf1.localmarket";

    public static final String CODE_TRIP_NO_CATCH = "1023";
    public static final String LABEL_TRIP_NO_CATCH = "message.trip.nocatch";

    public static final String CODE_TRIP_CAPACITY_OVERRIDE = "1022";
    public static final String LABEL_TRIP_CAPACITY_OVERRIDE = "message.trip.capacity.override";

    public static final String CODE_TRIP_HAS_DIFFERENT_HARBOUR = "1023";
    public static final String LABEL_TRIP_HAS_DIFFERENT_HARBOUR = "message.trip.different.harbour";

    public static final String CODE_ACTIVITY_TOTAL_CATCH_WEIGHT = "1210";
    public static final String LABEL_ACTIVITY_TOTAL_CATCH_WEIGHT = "message.activity.totalcaptureweight";

    public static final String CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY = "1211";
    public static final String LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY = "message.activity.operation.number.inconsistency";

    public static final String CODE_ACTIVITY_OCEAN_INCONSISTENCY = "1212";
    public static final String LABEL_ACTIVITY_OCEAN_INCONSISTENCY = "message.activity.ocean";
    public static final String CODE_ACTIVITY_QUADRANT_INCONSISTENCY = "1213";
    public static final String LABEL_ACTIVITY_QUADRANT_INCONSISTENCY = "message.activity.quadrant";
    public static final String CODE_ACTIVITY_POSITION_NOT_IN_OCEAN = "1214";
    public static final String LABEL_ACTIVITY_POSITION_NOT_IN_OCEAN = "message.activity.position.notinocean";
    public static final String CODE_ACTIVITY_POSITION_ON_COASTLINE = "1215";
    public static final String LABEL_ACTIVITY_POSITION_ON_COASTLINE = "message.activity.position.coastline";
    public static final String CODE_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION = "1216";
    public static final String LABEL_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION = "message.activity.quadrant.position";
    public static final String CODE_ACTIVITY_POSITION_WEIRD = "1217";
    public static final String LABEL_ACTIVITY_POSITION_WEIRD = "message.activity.position.weird";

    public static final String CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT = "1218";
    public static final String LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT = "message.activity.operation.number.inconsistency.with.catch.weight";

    public static final String CODE_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY = "1219";
    public static final String LABEL_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY = "message.activity.fishing.context.null";
    public static final String CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE = "1220";
    public static final String LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE = "message.activity.fishing.context.inconsistency.school.type";

    public static final String CODE_ACTIVITY_NO_TRACE_VMS = "1421";
    public static final String LABEL_ACTIVITY_NO_TRACE_VMS = "message.activity.no.trace.vms";
    public static final String CODE_ACTIVITY_TRACE_VMS_MATCH = "1422";
    public static final String LABEL_ACTIVITY_TRACE_VMS_MATCH = "message.activity.trace.vms.match";
    public static final String CODE_ACTIVITY_TRACE_VMS_CL2 = "1423";
    public static final String LABEL_ACTIVITY_TRACE_VMS_CL2 = "message.activity.trace.vms.cl2";
    public static final String CODE_INCONSISTENCY_VMS_POSITION_COUNT = "1424";
    public static final String LABEL_INCONSISTENCY_VMS_POSITION_COUNT = "message.trace.vms.inconsistent.position.count";
    public static final String CODE_ACTIVITY_TRACE_VMS_SCORE = "1425";
    public static final String LABEL_ACTIVITY_TRACE_VMS_SCORE = "message.activity.trace.vms.score";
    public static final String CODE_NO_ACTIVITY_TRACE_VMS = "1426";
    public static final String LABEL_NO_ACTIVITY_TRACE_VMS = "message.no.activity.trace.vms";
    public static final String CODE_ACTIVITY_TRACE_VMS_NO_MATCH = "1427";
    public static final String LABEL_ACTIVITY_TRACE_VMS_NO_MATCH = "message.activity.trace.vms.no.match";
    public static final String CODE_ANAPO_NO_ACTIVITY = "1428";
    public static final String LABEL_ANAPO_NO_ACTIVITY = "message.anapo.no.activity";

    public static final String CODE_ACTIVITY_QUADRANT_LAT_INCONSISTENCY = "1229";
    public static final String LABEL_ACTIVITY_QUADRANT_LAT_INCONSISTENCY = "message.activity.quadrant.lat";
    public static final String CODE_ACTIVITY_QUADRANT_LON_INCONSISTENCY = "1230";
    public static final String LABEL_ACTIVITY_QUADRANT_LON_INCONSISTENCY = "message.activity.quadrant.lon";

    public static final String CODE_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE = "1231";
    public static final String LABEL_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE = "message.activity.operation.inconsistency.with.school.type";

    public static final String CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY = "1232";
    public static final String LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY = "message.activity.operation.eez.inconsistency";

        public static final String CODE_ACTIVITY_POSITION_EEZ_INCONSISTENCY = "1234";
    public static final String LABEL_ACTIVITY_POSITION_EEZ_INCONSISTENCY = "message.activity.position.eez.inconsistency";

    
    public static final String CODE_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT = "1233";
    public static final String LABEL_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT = "message.activity.catch.weight.sample.weighted.weight";
    
    public static final String CODE_SAMPLE_NO_TRIP = "1310";
    public static final String LABEL_SAMPLE_NO_TRIP = "message.sample.notrip";
    public static final String CODE_SAMPLE_NO_SAMPLE_SPECIES = "1311";
    public static final String LABEL_SAMPLE_NO_SAMPLE_SPECIES = "message.sample.nospecies";
    public static final String CODE_SAMPLE_NO_SAMPLE_MEASURE = "1312";
    public static final String LABEL_SAMPLE_NO_SAMPLE_MEASURE = "message.sample.nomeasure";
    public static final String CODE_SAMPLE_WELL_INCONSISTENCY = "1313";
    public static final String LABEL_SAMPLE_WELL_INCONSISTENCY = "message.sample.well.inconsistency";
    public static final String CODE_SAMPLE_WELL_ACTIVITY_INCONSISTENCY = "1314";
    public static final String LABEL_SAMPLE_WELL_ACTIVITY_INCONSISTENCY = "message.sample.activity.inconsistency";
    public static final String CODE_SUBSAMPLE_NUMBER_INCONSISTENCY = "1315";
    public static final String LABEL_SUBSAMPLE_NUMBER_INCONSISTENCY = "message.subsample.number.inconsistency";
    public static final String CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_QUADRANT = "1316";
    public static final String LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_QUADRANT = "message.sample.position.inconsistency.quadrant";
    public static final String CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LATITUDE = "1317";
    public static final String LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LATITUDE = "message.sample.position.inconsistency.latitude";
    public static final String CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LONGITUDE = "1318";
    public static final String LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LONGITUDE = "message.sample.position.inconsistency.longitude";
    public static final String CODE_SAMPLE_WEIGHT_INCONSISTENCY = "1319";
    public static final String LABEL_SAMPLE_WEIGHT_INCONSISTENCY = "message.sample.weight.inconsistency";

    public static final String CODE_SAMPLE_WEIGHTING_SUP_100 = "1320";
    public static final String LABEL_SAMPLE_WEIGHTING_SUP_100 = "message.sample.weighting.sup100";

    public static final String CODE_SAMPLE_WEIGHTING_INF_WEIGHT = "1321";
    public static final String LABEL_SAMPLE_WEIGHTING_INF_WEIGHT = "message.sample.weighting.inf.weight";

    public static final String CODE_SAMPLE_WEIGHTING_RATIO = "1322";
    public static final String LABEL_SAMPLE_WEIGHTING_RATIO = "message.sample.weighting.ratio";

    public static final String CODE_SAMPLE_MEASURE_COUNT = "1323";
    public static final String LABEL_SAMPLE_MEASURE_COUNT = "message.sample.measure.count";
    public static final String CODE_SAMPLE_SPECIES_MEASURE_COUNT = "1324";
    public static final String LABEL_SAMPLE_SPECIES_MEASURE_COUNT = "message.sample.measure.species.count";
    public static final String CODE_SAMPLE_SPECIES_FREQ_MEASURE_COUNT = "1325";
    public static final String LABEL_SAMPLE_SPECIES_FREQ_MEASURE_COUNT = "message.sample.measure.species.freq.count";

    public static final String CODE_SAMPLE_WEIGHTING_BB_LC = "1326";
    public static final String LABEL_SAMPLE_WEIGHTING_BB_LC = "message.sample.weighting.bb.lc";
    public static final String CODE_SAMPLE_WEIGHTING_BB_WEIGHT = "1327";
    public static final String LABEL_SAMPLE_WEIGHTING_BB_WEIGHT = "message.sample.weighting.bb.weight";

    public static final String CODE_SAMPLE_SPECIES_NOT_SAMPLE = "1328";
    public static final String LABEL_SAMPLE_SPECIES_NOT_SAMPLE = "message.sample.not.species";

    public static final String CODE_SAMPLE_LENGTH_CLASS = "1329";
    public static final String LABEL_SAMPLE_LENGTH_CLASS = "message.sample.length.class";

    public static final String CODE_SAMPLE_BIG_INF_THRESHOLD = "1329";
    public static final String LABEL_SAMPLE_BIG_INF_THRESHOLD = "message.sample.big.threshold";
    public static final String CODE_SAMPLE_LITTLE_INF_THRESHOLD = "1330";
    public static final String LABEL_SAMPLE_LITTLE_INF_THRESHOLD = "message.sample.little.threshold";

    public static final String CODE_SAMPLE_SUBSAMPLE_FLAG = "1331";
    public static final String LABEL_SAMPLE_SUBSAMPLE_FLAG = "message.sample.subsample.flag";
    public static final String CODE_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES = "1335";
    public static final String LABEL_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES = "message.sample.subsample.no.sample.species";

    public static final String CODE_SAMPLE_LDLF_P10 = "1332";
    public static final String LABEL_SAMPLE_LDLF_P10 = "message.sample.ldlf.p10";
    public static final String CODE_SAMPLE_LDLF_M10 = "1333";
    public static final String LABEL_SAMPLE_LDLF_M10 = "message.sample.ldlf.m10";

    public static final String CODE_SAMPLE_LDLF_SPECIES_FORBIDDEN = "1334";
    public static final String LABEL_SAMPLE_LDLF_SPECIES_FORBIDDEN = "message.sample.ldlf.species.forbidden";

    public static final String CODE_SAMPLE_DISTRIBUTION_M10_P10 = "1335";
    public static final String LABEL_SAMPLE_DISTRIBUTION_M10_P10 = "message.sample.distribution.m10.p10";

    public static final String CODE_SAMPLE_NULL_DERPARTURE_HARBOUR = "1336";
    public static final String LABEL_SAMPLE_NULL_DERPARTURE_HARBOUR = "message.sample.null.departure.harbour";

    public static final String CODE_WELL_NO_TRIP = "1410";
    public static final String LABEL_WELL_NO_TRIP = "message.well.notrip";
    public static final String CODE_WELL_NO_WELLPLAN = "1411";
    public static final String LABEL_WELL_NO_WELLPLAN = "message.well.noplan";
    public static final String CODE_WELL_PLAN_ACTIVITY_INCONSISTENCY = "1412";
    public static final String LABEL_WELL_PLAN_ACTIVITY_INCONSISTENCY = "message.well.activity.inconsistency";

    public static Message createErrorMessageNullTrip() {
        return new ErrorMessage(CODE_TRIP_ERROR_OR_NULL, LABEL_TRIP_ERROR_OR_NULL);
    }
}
