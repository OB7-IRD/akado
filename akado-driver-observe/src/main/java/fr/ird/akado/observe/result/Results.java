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
import fr.ird.common.log.LogService;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        for (Result<?> r : results) {
            activities.addAll(r.extractResults());
        }
        String template = "activity_template.xlsx";
        writeInSheet("activities", activities, template, SHEET_NAME_ACTIVITY, filename);
    }

    private void writeInTripSheet(String filename) throws IOException {
        Results results = getTripResults();
        LogService.getService(Results.class).logApplicationDebug("TripResults size : " + results.size());
        if (results.isEmpty()) {
            return;
        }
        List<TripDataSheet> trips = new ArrayList<>();
        for (Result<?> r : results) {
//            LogService.getService(Results.class).logApplicationDebug("TripResult : " + r);
            List<TripDataSheet> l = r.extractResults();
//            LogService.getService(Results.class).logApplicationDebug("TripResult extractResults: " + l.size());
//            for (Object o : l) {
//                LogService.getService(Results.class).logApplicationDebug(o.toString());
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
        for (Result<?> r : results) {
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
        for (Result<?> r : results) {
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
        for (Result<?> r : results) {
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
        for (Result<?> r : results) {
            adtos.addAll(r.extractResults());
        }
        String template = "anapo_template.xlsx";
        writeInSheet("anapos", adtos, template, SHEET_NAME_ANAPO, filename);
    }

    private void writeInSheet(String dataVarname, List<?> data, String templateName, String sheetName, String directoryPath) throws IOException {
        InputStream is = Results.class.getResourceAsStream(templateName);
        Context context = new Context();
        context.putVar(dataVarname, data);
        OutputStream os = new FileOutputStream(directoryPath + File.separator + sheetName + "_akado_output.xlsx");
        JxlsHelper.getInstance().processTemplate(is, os, context);
//        JxlsHelper.getInstance().processTemplateAtCell(is, os, context, sheetName + "!A1");
    }

    @Override
    public void exportToXLS(String directoryPath) {

        LogService.getService(Results.class).logApplicationInfo("Running export to XLS file");

        try {
            writeInTripSheet(directoryPath);
            writeInActivitySheet(directoryPath);
            writeInMetaTripSheet(directoryPath);
            writeInSampleSheet(directoryPath);
            writeInWellSheet(directoryPath);
            writeInAnapoSheet(directoryPath);
            writeLogs(directoryPath);

        } catch (IOException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        }
    }

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
                LogService.getService(Results.class).logApplicationDebug(result.toString());
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

    private void writeLogs(String directoryPath) throws IOException {
        File file = new File(directoryPath + File.separator + "akado.log");
        if (!file.exists()) {
            file.createNewFile();
        }

        //true = append file
        try (BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(file, true))) {
            for (Result<?> result : this) {
                String message = result.getMessage().getContent();
                LogService.getService(Results.class).logApplicationDebug(message);
                bufferWritter.write(message + "\n");
            }
        }
    }
}
