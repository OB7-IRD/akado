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
public class DistributionInspector extends ObserveSampleInspector {
    public DistributionInspector() {
        this.name = getClass().getName();
        this.description = "Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie dans l'échantillon.";
    }

    @Override
    public AbstractResults<?> execute() throws Exception {
        return null;
    }
}
