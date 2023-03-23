
package fr.ird.akado.observe.inspector.ps.logbook.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AbstractResults;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class WellNumberConsistentInspector extends ObserveSampleInspector {

    public WellNumberConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the well number is consistent with the sample.";
    }

    @Override
    public AbstractResults<?> execute() throws Exception {
        return null;
    }

}
