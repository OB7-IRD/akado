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
package fr.ird.akado.observe.result;

import fr.ird.akado.core.common.AbstractResult;
import fr.ird.akado.core.common.AbstractResults;
import fr.ird.akado.observe.result.model.ActivityDataSheet;
import fr.ird.akado.observe.result.model.AnapoDataSheet;
import fr.ird.akado.observe.result.model.MetaTripDataSheet;
import fr.ird.akado.observe.result.model.SampleDataSheet;
import fr.ird.akado.observe.result.model.TripDataSheet;
import fr.ird.akado.observe.result.model.WellDataSheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une liste de résultat généré lors de l'analyse d'une base ObServe.
 * <p>
 * Il permet de préparer la sortie Excel de ces résultats.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Results extends AbstractResults<Result<?>> {
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

    public static Results of(Result<?> result) {
        Results results = new Results();
        results.add(result);
        return results;
    }

    public void writeInActivitySheet(String directoryPath) throws IOException {
        Results results = getActivityResults();
        if (results.isEmpty()) {
            return;
        }
        List<ActivityDataSheet> activities = new ArrayList<>();
        for (Result<?> r : results) {
            activities.addAll(r.extractResults());
        }
        String template = "activity_template.xlsx";
        writeInSheet("activities", activities, template, SHEET_NAME_ACTIVITY, directoryPath);
    }

    public void writeInTripSheet(String directoryPath) throws IOException {
        Results results = getTripResults();
        log.debug("TripResults size : " + results.size());
        if (results.isEmpty()) {
            return;
        }
        List<TripDataSheet> trips = new ArrayList<>();
        for (Result<?> r : results) {
//            log.debug("TripResult : " + r);
            List<TripDataSheet> l = r.extractResults();
//            log.debug("TripResult extractResults: " + l.size());
//            for (Object o : l) {
//                log.debug(o.toString());
//            }
            trips.addAll(l);
        }
        String template = "trip_template.xlsx";
        writeInSheet("trips", trips, template, SHEET_NAME_TRIP, directoryPath);
    }

    public void writeInSampleSheet(String directoryPath) throws IOException {
        Results results = getSampleResults();
        if (results.isEmpty()) {
            return;
        }
        List<SampleDataSheet> samples = new ArrayList<>();
        for (Result<?> r : results) {
            samples.addAll(r.extractResults());
        }
        String template = "sample_template.xlsx";
        writeInSheet("samples", samples, template, SHEET_NAME_SAMPLE, directoryPath);
    }

    public void writeInWellSheet(String directoryPath) throws IOException {
        Results results = getWellResults();
        if (results.isEmpty()) {
            return;
        }
        List<WellDataSheet> wdtos = new ArrayList<>();
        for (Result<?> r : results) {
            wdtos.addAll(r.extractResults());
        }
        String template = "well_template.xlsx";
        writeInSheet("wells", wdtos, template, SHEET_NAME_WELL, directoryPath);
    }

    public void writeInMetaTripSheet(String directoryPath) throws IOException {
        Results results = getMetaTripResults();
        if (results.isEmpty()) {
            return;
        }
        List<MetaTripDataSheet> mtdtos = new ArrayList<>();
        for (Result<?> r : results) {
            mtdtos.addAll(r.extractResults());
        }
        String template = "metatrip_template.xlsx";
        writeInSheet("metatrips", mtdtos, template, SHEET_NAME_TRIP_EXTENDED, directoryPath);
    }

    public void writeInAnapoSheet(String directoryPath) throws IOException {
        Results results = getAnapoResults();
        if (results.isEmpty()) {
            return;
        }
        List<AnapoDataSheet> adtos = new ArrayList<>();
        for (Result<?> r : results) {
            adtos.addAll(r.extractResults());
        }
        String template = "anapo_template.xlsx";
        writeInSheet("anapos", adtos, template, SHEET_NAME_ANAPO, directoryPath);
    }

    private void writeInSheet(String dataVarName, List<?> data, String templateName, String sheetName, String directoryPath) throws IOException {
        try (InputStream is = Results.class.getResourceAsStream(templateName)) {
            Context context = new Context();
            context.putVar(dataVarName, data);
            String filePath = directoryPath + File.separator + sheetName + "_akado_output.xlsx";
            log.info("Writing results in: {}", filePath);
            try (OutputStream os = new FileOutputStream(filePath)) {
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
//        JxlsHelper.getInstance().processTemplateAtCell(is, os, context, sheetName + "!A1");
    }

//    @Override
//    public void exportToXLS(String directoryPath) {
//
//        log.info("Running export to XLS file {}", directoryPath);
//
//        try {
//            writeInTripSheet(directoryPath);
//            writeInActivitySheet(directoryPath);
//            writeInMetaTripSheet(directoryPath);
//            writeInSampleSheet(directoryPath);
//            writeInWellSheet(directoryPath);
//            writeInAnapoSheet(directoryPath);
//            writeLogs(directoryPath);
//
//        } catch (IOException ex) {
//            log.error(ex.getMessage());
//        }
//    }

    public boolean in(Result<?> result) {
        for (AbstractResult r : this) {
            if (result.get().equals(r.get())) {
                return true;
            }
        }
        return false;
    }

    private Results getTripResults() {
        Results results = new Results();
        for (Result<?> result : this) {
            if (result instanceof TripResult && !results.in(result)) {
                log.debug(result.toString());
                results.add(result);
            }
        }
        return results;
    }

    private Results getAnapoResults() {
        Results results = new Results();
        for (Result<?> result : this) {
            if (result instanceof AnapoResult && !results.in(result)) {
                results.add(result);
            }
        }
        return results;
    }

    private Results getActivityResults() {
        Results results = new Results();
        for (Result<?> result : this) {
            if (result instanceof ActivityResult && !results.in(result)) {
                results.add(result);
            }
        }
        return results;
    }

    private Results getWellResults() {
        Results results = new Results();
        for (Result<?> result : this) {
            if (result instanceof WellResult && !results.in(result)) {
                results.add(result);
            }
        }
        return results;
    }

    private Results getSampleResults() {
        Results results = new Results();
        for (Result<?> result : this) {
            if (result instanceof SampleResult && !results.in(result)) {
                results.add(result);
            }
        }
        return results;
    }

    private Results getMetaTripResults() {
        Results results = new Results();
        for (Result<?> result : this) {
            if (result instanceof MetaTripResult && !results.in(result)) {
                results.add(result);
            }
        }
        return results;
    }

    public void writeLogs(String directoryPath) throws IOException {
        File file = new File(directoryPath + File.separator + "akado.log");
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Appending logs in: {}", file);
        //true = append file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, true))) {
            for (Result<?> result : this) {
                String message = result.getMessage().getContent();
                log.debug(message);
                writer.write(message);
                writer.newLine();
            }
        }
    }
}
