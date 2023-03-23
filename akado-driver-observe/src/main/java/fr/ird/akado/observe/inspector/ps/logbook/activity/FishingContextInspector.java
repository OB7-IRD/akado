
package fr.ird.akado.observe.inspector.ps.logbook.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AbstractResults;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class FishingContextInspector extends ObserveActivityInspector {

    public FishingContextInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the school type and the fishing context are consistent.";
    }

    @Override
    public AbstractResults<?> execute() throws Exception {
        return null;
    }
}
