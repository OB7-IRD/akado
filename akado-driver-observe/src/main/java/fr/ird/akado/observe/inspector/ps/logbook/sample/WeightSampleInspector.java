
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
public class WeightSampleInspector extends ObserveSampleInspector {

    public WeightSampleInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sample weight (m10 and p10) is consistent with the global weight sample.";
    }

    @Override
    public AbstractResults<?> execute() throws Exception {
        return null;
    }
}
