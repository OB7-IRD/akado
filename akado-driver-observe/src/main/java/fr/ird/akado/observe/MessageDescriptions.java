package fr.ird.akado.observe;

import fr.ird.akado.core.common.MessageDescription;

/**
 * All messages available for Observe.
 * <p>
 * Created on 04/04/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public enum MessageDescriptions implements MessageDescription {
    I_0001_INFO_DATABASE("info.database", false),
    E_0002_DATABASE_NOT_COMPATIBLE("avdth.not.compatible", false),

    E_1018_TRIP_NO_ACTIVITY("message.trip.noactivities", true),
    E_1024_TRIP_HAS_ACTIVITY_NO_LOGBOOK("message.trip.activity.nologbook", true),
    E_1017_TRIP_FISHING_TIME("message.trip.fishingtime", true),
    E_1011_TRIP_TIME_AT_SEA("message.trip.timeatsea", false),
    W_1002_VESSEL_NO_CAPACITY("message.vessel.nocapacity", false),
    E_1022_TRIP_CAPACITY_OVERRIDE("message.trip.capacity.override", false),
    E_1016_TRIP_LANDING_TOTAL_WEIGHT("message.trip.landingtotalweight", false),
    E_1013_TRIP_RECOVERY_TIME("message.trip.recoverytime", true),
    E_1012_TRIP_TEMPORAL_LIMIT("message.trip.temporallimit", false),
    E_1023_TRIP_HAS_DIFFERENT_HARBOUR("message.trip.different.harbour", true),
    E_1025_TRIP_NO_CATCH("message.trip.nocatch", true),
    W_1020_TRIP_RAISING_FACTOR("message.trip.rf1", true),

    W_1232_ACTIVITY_OPERATION_EEZ_INCONSISTENCY("message.activity.operation.eez.inconsistency", true),
    W_1234_ACTIVITY_POSITION_EEZ_INCONSISTENCY("message.activity.position.eez.inconsistency", true),
    E_1233_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT("message.activity.catch.weight.sample.weighted.weight", false),
    E_1229_ACTIVITY_QUADRANT_LAT_INCONSISTENCY("message.activity.quadrant.lat", true),
    E_1230_ACTIVITY_QUADRANT_LON_INCONSISTENCY("message.activity.quadrant.lon", true),
    E_1213_ACTIVITY_QUADRANT_INCONSISTENCY("message.activity.quadrant", true),
    E_1216_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION("message.activity.quadrant.position", true),
    E_1210_ACTIVITY_TOTAL_CATCH_WEIGHT("message.activity.totalcaptureweight", false),
    E_1214_ACTIVITY_POSITION_NOT_IN_OCEAN("message.activity.position.notinocean", false),
    W_1217_ACTIVITY_POSITION_WEIRD("message.activity.position.weird", false),
    E_1212_ACTIVITY_OCEAN_INCONSISTENCY("message.activity.ocean", false),
    E_1218_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT("message.activity.operation.number.inconsistency.with.catch.weight", true),
    E_1219_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY("message.activity.fishing.context.null", true),
    E_1240_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_ARTIFICIAL_SCHOOL_TYPE("message.activity.fishing.context.inconsistency.artificial.school.type", true),
    E_1241_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_FREE_SCHOOL_TYPE("message.activity.fishing.context.inconsistency.free.school.type", true),

    E_1335_SAMPLE_DISTRIBUTION_M10_P10("message.sample.distribution.m10.p10", true),
    E_1334_SAMPLE_LDLF_SPECIES_FORBIDDEN("message.sample.ldlf.species.forbidden", true),
    W_1332_SAMPLE_LDLF_P10("message.sample.ldlf.p10", true),
    W_1333_SAMPLE_LDLF_M10("message.sample.ldlf.m10", true),
    W_1329_SAMPLE_LENGTH_CLASS("message.sample.length.class", true),
    W_1330_SAMPLE_LITTLE_INF_THRESHOLD("message.sample.little.threshold", true),
    W_1337_SAMPLE_BIG_INF_THRESHOLD("message.sample.big.threshold", true),
    E_1323_SAMPLE_MEASURE_COUNT("message.sample.measure.count", true),
    E_1312_SAMPLE_NO_SAMPLE_MEASURE("message.sample.nomeasure", true),
    E_1311_SAMPLE_NO_SAMPLE_SPECIES("message.sample.nospecies", true),
    E_1338_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES("message.sample.subsample.no.sample.species", true),
    E_1331_SAMPLE_SUBSAMPLE_FLAG("message.sample.subsample.flag", true),
    E_1315_SUBSAMPLE_NUMBER_INCONSISTENCY("message.subsample.number.inconsistency", true),
    W_1320_SAMPLE_WEIGHTING_SUP_100("message.sample.weighting.sup100", true),
    W_1322_SAMPLE_WEIGHTING_RATIO("message.sample.weighting.ratio", true),
    E_1327_SAMPLE_WEIGHTING_BB_WEIGHT("message.sample.weighting.bb.weight", true),
    E_1326_SAMPLE_WEIGHTING_BB_LC("message.sample.weighting.bb.lc", true),
    E_1319_SAMPLE_WEIGHT_INCONSISTENCY("message.sample.weight.inconsistency", true),
    E_1313_SAMPLE_WELL_INCONSISTENCY("message.sample.well.inconsistency", true),
    ;

    private final MessageDescription messageDescription;

    MessageDescriptions(String messageLabel, boolean inconsistent) {
        String messageType = name().substring(0, 1);
        String messageCode = name().substring(2, name().indexOf("_", 3));
        switch (messageType) {
            case "I":
                messageDescription = MessageDescription.info(messageCode, messageLabel, inconsistent);
                break;
            case "W":
                messageDescription = MessageDescription.warning(messageCode, messageLabel, inconsistent);
                break;
            case "E":
                messageDescription = MessageDescription.error(messageCode, messageLabel, inconsistent);
                break;
            default:
                throw new IllegalStateException("name must start with I, W or E but was: " + messageType);
        }
    }

    @Override
    public String getMessageType() {
        return messageDescription.getMessageType();
    }

    @Override
    public String getMessageCode() {
        return messageDescription.getMessageCode();
    }

    @Override
    public String getMessageLabel() {
        return messageDescription.getMessageLabel();
    }

    @Override
    public boolean isInconsistent() {
        return messageDescription.isInconsistent();
    }

    @Override
    public String toString() {
        return messageDescription.toString();
    }
}
