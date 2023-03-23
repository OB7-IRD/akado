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
package fr.ird.akado.ui.cli;

import fr.ird.akado.core.AkadoCore;
import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.common.AkadoMessage;
import fr.ird.akado.core.common.MessageAdapter;
import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.core.common.AkadoException;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 * The RunCLI class implements a command-line interfaces. It can run AKaDo with
 * a gui or run in console. Designed primarily for development, the CLI allows
 * you to automate most easily via a script.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 juin 2014
 */
public class RunCLI {

    public static void main(String[] args) {

        ArgumentParser parser = ArgumentParsers.newArgumentParser("AKaDo CLI")
                .defaultHelp(true)
                .description("");

        parser.addArgument("-b", "--base")
                .type(String.class)
                .help("Specify the AVDTH's database to use");

        parser.addArgument("-s", "--spreadsheet")
                .action(Arguments.storeTrue())
                .help("Export the results in a spreadsheet file");

        parser.addArgument("-x", "--xml")
                .action(Arguments.storeTrue())
                .help("Export the results in a XML file");

        parser.addArgument("-g", "--gui")
                .action(Arguments.storeTrue())
                .help("Run AKaDo in graphical mode");

//        parser.addArgument("-w", "--gisdb")
//                .action(Arguments.storeTrue())
//                .help("Generate the GIS database");
//        parser.addArgument("-i", "--install")
//                .action(Arguments.storeTrue())
//                .help("Install the configuration file");
        Namespace ns = null;
        if (args.length == 0) {
            System.out.println("You must specify arguments. See help. \n");
            System.out.println(parser.formatHelp());
            System.exit(1);
        }
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        String dataBasePath = ns.getString("base");
//        Boolean writeGisDB = ns.getBoolean("gisdb");
        Boolean guiMode = ns.getBoolean("gui");
//        Boolean installMode = ns.getBoolean("install");
        Boolean xmlExport = ns.getBoolean("xml");
        Boolean xlsExport = ns.getBoolean("spreadsheet");
//        if (installMode) {
//            new InstallConfig().run();
//            return;
//        }
//        if (writeGisDB) {
//            new GISCreator().create();
//            return;
//        }
        if (guiMode) {
            new AkadoController();
        } else {
            try {
                AkadoAvdthProperties.getService().init();

                //System.out.println("The DB path " + dataBasePath);
                //System.out.println("Create Akado ");
                AkadoCore akado = new AkadoCore();
                Constructor ctor = AkadoAvdthProperties.THIRD_PARTY_DATASOURCE.getDeclaredConstructor(String.class, String.class, String.class, String.class);
                ctor.setAccessible(true);

                akado = new AkadoCore();
                DataBaseInspector inspector = (DataBaseInspector) ctor.newInstance(AkadoAvdthProperties.PROTOCOL_JDBC_ACCESS + dataBasePath, DataBaseInspector.CONFIGURATION_PROPERTIES.getProperty(AkadoAvdthProperties.KEY_JDBC_ACCESS_DRIVER), "", "");

                //System.out.println("Add AVDTH Inspection to Akado ");
                if (!akado.addDataBaseValidator(inspector)) {
                    throw new AkadoException("Error during the AVDTHValidator creation");
                }
                inspector.getAkadoMessages().addMessageListener(new MessageAdapter() {
                    @Override
                    public void messageAdded(AkadoMessage m) {
                        System.out.println(m.getContent());
                    }
                });
                DateTime startProcess = new DateTime();
                inspector.info();
                akado.execute();
                DateTime endProcess = new DateTime();
                int duration = Seconds.secondsBetween(startProcess, endProcess).getSeconds();
                System.out.println("Done in " + duration / 60 + " minute(s) and " + duration % 60 + " seconds !\n");
                System.out.println("There is " + inspector.getAkadoMessages().size() + " messages. See the log for more informations.\n");
                String pathExport = new File(dataBasePath).getParent();
                String dbName = FilenameUtils.removeExtension(new File(dataBasePath).getName());

                String exportName = pathExport + File.separator + dbName + "_akado_result_" + endProcess.getYear() + endProcess.getMonthOfYear() + endProcess.getDayOfMonth() + "_" + endProcess.getHourOfDay() + endProcess.getMinuteOfHour();
                inspector.getAkadoMessages().saveLog(exportName + ".log");
                if (xlsExport) {
                    DateTime startExport = new DateTime();
                    inspector.getResults().exportToXLS(exportName + ".xls");
                    DateTime endExport = new DateTime();
                    duration = Seconds.secondsBetween(startExport, endExport).getSeconds();

                    System.out.println("The results are in the file: \"" + exportName + ".xls\".\n");
                    System.out.println("Export done in " + duration / 60 + " minute(s) and " + duration % 60 + " seconds !\n");
                }
                if (xmlExport) {
                    inspector.getResults().exportToXML(exportName + ".xml");
                    System.out.println("The results are in the file: \"" + exportName + ".xml\".\n");
                }

            } catch (Exception ex) {
                Logger.getLogger(RunCLI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
