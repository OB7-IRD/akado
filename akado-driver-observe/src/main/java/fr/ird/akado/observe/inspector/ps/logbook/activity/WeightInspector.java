package fr.ird.akado.observe.inspector.ps.logbook.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class WeightInspector extends ObserveActivityInspector {

    public WeightInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the total catch weight is consistent with elementary catches.";
    }

    @Override
    public Results execute() throws Exception {
        return null;
    }
}
