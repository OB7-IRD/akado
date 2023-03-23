package fr.ird.akado.observe.inspector.ps.logbook.activities;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AbstractResults;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivitiesInspector.class)
public class PositionsInEEZInspector extends ObserveActivitiesInspector {
    public PositionsInEEZInspector() {
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with the eez calculated from the position activity.";
    }

    @Override
    public AbstractResults<?> execute() throws Exception {
        return null;
    }
}
