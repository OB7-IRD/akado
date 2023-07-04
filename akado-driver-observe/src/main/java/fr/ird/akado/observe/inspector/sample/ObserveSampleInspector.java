package fr.ird.akado.observe.inspector.sample;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.MessageDescription;
import fr.ird.akado.observe.WithTrip;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.referential.common.Species;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveSampleInspector extends ObserveInspector<Sample> implements WithTrip {
    private Trip trip;

    public static List<ObserveSampleInspector> loadInspectors() {
        return loadInspectors(ObserveSampleInspector.class);
    }

    public static List<ObserveSampleInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveSampleInspector.class, inspectors);
    }

    public static boolean specieMustBeSampled(Species species) {
        return species.isYFT()
                ||species.isSKJ()
                ||species.isBET()
                ||species.isALB()
                ||species.isLTA()
                ||species.isKAW()
                ||species.isTUN()
                ||species.isLOT()
                ||species.isALB()
                ;
    }

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    protected SampleResult createResult(MessageDescription messageDescription, Sample datum, Object... parameters) {
        SampleResult r = createResult(datum, messageDescription);
        createResult(r, parameters);
        return r;
    }

    private SampleResult createResult(Sample datum, MessageDescription messageDescription) {
        return WithTrip.copy(new SampleResult(datum, messageDescription), this);
    }
}
