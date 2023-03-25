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
public class LengthClassInspector extends ObserveSampleInspector {

    public static final int LENGTH_CLASS_YFT = 90;
    public static final int LENGTH_CLASS_BET = 90;
    public static final int LENGTH_CLASS_ALB = 42;

    public LengthClassInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the length class is consistent with each "
                + "length class of species (L=90cm for YFT and BET and L=42cm for ALB) and LD1.";
    }

    @Override
    public Results execute() throws Exception {
        return null;
    }
}
