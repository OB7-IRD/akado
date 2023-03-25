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
public class PositionInspector extends ObserveActivityInspector {

    public PositionInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the position activity are in ocean or inland, "
                + "and if the position activity and ocean are consistent.";
    }

    @Override
    public Results execute() throws Exception {
        return null;
    }
}
