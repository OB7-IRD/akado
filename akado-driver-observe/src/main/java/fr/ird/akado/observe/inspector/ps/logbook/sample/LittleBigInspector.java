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
public class LittleBigInspector extends ObserveSampleInspector {

    public static double THRESHOLD = 0.9;

    public static double LD1_YFT = 24d;
    public static double LF_YFT = 80d;
    public static double LD1_BET = 25d;
    public static double LF_BET = 77d;
    public static double LD1_ALB = 23.5;
    public static double LF_ALB = 78d;

    public LittleBigInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the percentage of little and big fish sampled is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        return null;
    }
}
