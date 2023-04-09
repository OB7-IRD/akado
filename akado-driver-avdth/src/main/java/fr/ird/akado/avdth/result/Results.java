/*
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.avdth.result;

import fr.ird.akado.avdth.result.model.ActivityDataSheet;
import fr.ird.akado.avdth.result.model.AnapoDataSheet;
import fr.ird.akado.avdth.result.model.MetaTripDataSheet;
import fr.ird.akado.avdth.result.model.SampleDataSheet;
import fr.ird.akado.avdth.result.model.TripDataSheet;
import fr.ird.akado.avdth.result.model.WellDataSheet;
import fr.ird.akado.core.common.AbstractResult;
import fr.ird.akado.core.common.AbstractResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une liste de résultat généré lors de l'analyse d'une base AVDTH.
 * Il permet de préparer la sortie Excel de ces résultats.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 11 juil. 2014
 */
public class Results extends AbstractResults<Result> {
    private static final Logger log = LogManager.getLogger(Results.class);
    public static final String SHEET_NAME_TRIP = "Trip";
    public static final String SHEET_NAME_TRIP_EXTENDED = "Trip with partials landings";
    public static final String SHEET_NAME_ACTIVITY = "Activity";
    public static final String SHEET_NAME_WELL = "Well";
    public static final String SHEET_NAME_SAMPLE = "Sample";
    public static final String SHEET_NAME_ANAPO = "Anapo";

    public static final boolean WB_COMPRESS_TMP_FILES = true;
    public static final boolean WB_USE_SHARED_STRINGS_TABLE = true;
    public static final int WB_ROW_ACCESS_WINDOW_SIZE = 200;

    private void writeInActivitySheet(String filename) throws IOException {
        Results results = getActivityResults();
        if (results.isEmpty()) {
            return;
        }
        List<ActivityDataSheet> activities = new ArrayList<>();
        for (Result r : results) {
            activities.addAll(r.extractResults());
        }
        String template = "activity_template.xlsx";
        writeInSheet("activities", activities, template, SHEET_NAME_ACTIVITY, filename);
    }

    private void writeInTripSheet(String filename) throws IOException {
        Results results = getTripResults();
        log.debug("TripResults size : " + results.size());
        if (results.isEmpty()) {
            return;
        }
        List<TripDataSheet> trips = new ArrayList<>();
        for (Result r : results) {
//            log.debug("TripResult : " + r);
            List l = r.extractResults();
//            log.debug("TripResult extractResults: " + l.size());
//            for (Object o : l) {
//                log.debug(o.toString());
//            }
            trips.addAll(l);
        }
        String template = "trip_template.xlsx";
        writeInSheet("trips", trips, template, SHEET_NAME_TRIP, filename);
    }

    private void writeInSampleSheet(String filename) throws IOException {
        Results results = getSampleResults();
        if (results.isEmpty()) {
            return;
        }
        List<SampleDataSheet> samples = new ArrayList<>();
        for (Result r : results) {
            samples.addAll(r.extractResults());
        }
        String template = "sample_template.xlsx";
        writeInSheet("samples", samples, template, SHEET_NAME_SAMPLE, filename);
    }

    private void writeInWellSheet(String filename) throws IOException {
        Results results = getWellResults();
        if (results.isEmpty()) {
            return;
        }
        List<WellDataSheet> wdtos = new ArrayList<>();
        for (Result r : results) {
            wdtos.addAll(r.extractResults());
        }
        String template = "well_template.xlsx";
        writeInSheet("wells", wdtos, template, SHEET_NAME_WELL, filename);
    }

    private void writeInMetaTripSheet(String filename) throws IOException {
        Results results = getMetaTripResults();
        if (results.isEmpty()) {
            return;
        }
        List<MetaTripDataSheet> mtdtos = new ArrayList<>();
        for (Result r : results) {
            mtdtos.addAll(r.extractResults());
        }
        String template = "metatrip_template.xlsx";
        writeInSheet("metatrips", mtdtos, template, SHEET_NAME_TRIP_EXTENDED, filename);
    }

    private void writeInAnapoSheet(String filename) throws IOException {
        Results results = getAnapoResults();
        if (results.isEmpty()) {
            return;
        }
        List<AnapoDataSheet> adtos = new ArrayList<>();
        for (Result r : results) {
            adtos.addAll(r.extractResults());
        }
        String template = "anapo_template.xlsx";
        writeInSheet("anapos", adtos, template, SHEET_NAME_ANAPO, filename);
    }

    private void writeInSheet(String dataVarname, List data, String templateName, String sheetName, String directoryPath) throws IOException {
        InputStream is = Results.class.getResourceAsStream(templateName);
        Context context = new Context();
        context.putVar(dataVarname, data);
        OutputStream os = new FileOutputStream(directoryPath + File.separator + sheetName + "_akado_output.xlsx");
        JxlsHelper.getInstance().processTemplate(is, os, context);
//        JxlsHelper.getInstance().processTemplateAtCell(is, os, context, sheetName + "!A1");
    }

    @Override
    public void exportToXLS(String directoryPath) {

        log.info("Running export to XLS file");

        try {
            writeInTripSheet(directoryPath);
            writeInActivitySheet(directoryPath);
            writeInMetaTripSheet(directoryPath);
            writeInSampleSheet(directoryPath);
            writeInWellSheet(directoryPath);
            writeInAnapoSheet(directoryPath);
            writeLogs(directoryPath);

        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public boolean in(AbstractResult result) {
        for (AbstractResult r : this) {
            if (result.get().equals(r.get())) {
                return true;
            }
        }
        return false;
    }

    private Results getTripResults() {
        Results results = new Results();
        for (AbstractResult result : this) {
            if (result instanceof TripResult && !results.in(result)) {
                log.debug(result.toString());
                results.add((TripResult) result);
            }
        }
        return results;
    }

    private Results getAnapoResults() {
        Results results = new Results();
        for (AbstractResult result : this) {
            if (result instanceof AnapoResult && !results.in(result)) {
                results.add((AnapoResult) result);
            }
        }
        return results;
    }

    private Results getActivityResults() {
        Results results = new Results();
        for (AbstractResult result : this) {
            if (result instanceof ActivityResult && !results.in(result)) {
                results.add((ActivityResult) result);
            }
        }
        return results;
    }

    private Results getWellResults() {
        Results results = new Results();
        for (AbstractResult result : this) {
            if (result instanceof WellResult && !results.in(result)) {
                results.add((WellResult) result);
            }
        }
        return results;
    }

    private Results getSampleResults() {
        Results results = new Results();
        for (AbstractResult result : this) {
            if (result instanceof SampleResult && !results.in(result)) {
                results.add((SampleResult) result);
            }
        }
        return results;
    }

    private Results getMetaTripResults() {
        Results results = new Results();
        for (AbstractResult result : this) {
            if (result instanceof MetaTripResult && !results.in(result)) {
                results.add((MetaTripResult) result);
            }
        }
        return results;
    }

    private void writeLogs(String directoryPath) throws FileNotFoundException, IOException {
        File file = new File(directoryPath + File.separator + "akado.log");
        if (!file.exists()) {
            file.createNewFile();
        }

        //true = append file
        FileWriter fileWritter = new FileWriter(file, true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

        for (AbstractResult result : this) {
            log.debug(result.getMessage().getContent());
            bufferWritter.write(result.getMessage().getContent() + "\n");
        }
        bufferWritter.close();
    }
}
