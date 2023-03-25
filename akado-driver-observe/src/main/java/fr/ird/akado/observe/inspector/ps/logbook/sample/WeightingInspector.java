package fr.ird.akado.observe.inspector.ps.logbook.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class WeightingInspector extends ObserveSampleInspector {

    public WeightingInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the weighting information for each sample well is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        return null;
    }
}
