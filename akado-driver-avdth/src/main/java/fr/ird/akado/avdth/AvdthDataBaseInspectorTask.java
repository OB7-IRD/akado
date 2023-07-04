package fr.ird.akado.avdth;

import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.DataBaseInspectorTask;
import fr.ird.akado.core.common.AAProperties;
import io.ultreia.java4all.util.TimeLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created on 04/07/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 4.0.0
 */
public abstract class AvdthDataBaseInspectorTask extends DataBaseInspectorTask {
    private static final Logger log = LogManager.getLogger(AvdthDataBaseInspectorTask.class);
    private static final TimeLog TIMELOG = new TimeLog(AvdthDataBaseInspectorTask.class, 500, 1000);
    private final String exportDirectoryPath;

    public AvdthDataBaseInspectorTask(Results r, String exportDirectoryPath) {
        super(r);
        this.exportDirectoryPath = exportDirectoryPath;
    }

    protected abstract void inspect() throws Exception;

    protected abstract void writeResults(String directoryPath, Results results) throws IOException;

    @Override
    public final void run() {
        String name = getClass().getSimpleName();
        log.info("Running task: {}", name);
        long t0 = TimeLog.getTime();
        try {
            inspect();
            TIMELOG.log(t0, "inspect", name);
        } catch (Exception e) {
            log.error("Error while inspecting data in database inspector task: {}", name, e);
        }
        if (!AAProperties.isResultsEnabled()) {
            return;
        }
        t0 = TimeLog.getTime();
        Results results = getResults();
        if (!results.isEmpty()) {
            try {
                writeResults(exportDirectoryPath, results);
            } catch (IOException e) {
                log.error("Error while writing results in database inspector task: {}", name, e);
            } finally {
                results.clear();
                TIMELOG.log(t0, "write results", name);
            }
        }
    }

    @Override
    public Results getResults() {
        return (Results) super.getResults();
    }

}
