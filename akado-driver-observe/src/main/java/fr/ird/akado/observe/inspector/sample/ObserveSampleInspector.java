package fr.ird.akado.observe.inspector.sample;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.WithTrip;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

import java.util.List;
import java.util.Set;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveSampleInspector extends ObserveInspector<Sample> implements WithTrip {
    public static List<ObserveSampleInspector> loadInspectors() {
        return loadInspectors(ObserveSampleInspector.class);
    }

    public static List<ObserveSampleInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveSampleInspector.class, inspectors);
    }

    public static final Set<String> SPECIES_FOR_SAMPLE = Set.of(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "0",
            "10",
            "11"
    );

    public static boolean specieMustBeSampled(String code) {
        return SPECIES_FOR_SAMPLE.contains(code);
    }

    private Trip trip;

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    protected SampleResult createResult(Sample datum, String messageLevel, String messageCode, String messageLabel, boolean inconsistent, Object... parameters) {
        SampleResult r = createResult(datum);
        createResult(r, messageLevel, messageCode, messageLabel, inconsistent, parameters);
        return r;
    }

    private SampleResult createResult(Sample datum) {
        return WithTrip.copy(new SampleResult(datum), this);
    }
}
