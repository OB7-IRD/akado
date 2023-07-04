/*
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
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
package fr.ird.common;

import java.io.PrintStream;
import static java.lang.String.format;
import java.util.List;
import java.util.Map;

/**
 * Classe permettant de générer un tableau formater dans une console.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 22 oct. 2013
 * @since 1.0
 */
public final class PrettyPrinter {

    public static void printMatrix(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("\n\n*******************************************");
            System.out.println("\t\tNo result");
            System.out.println("*******************************************\n\n");
            return;
        }
        final PrettyPrinter printer = new PrettyPrinter(System.out);
        String[][] resultsArray2D = null;
        String[] array;
        int i = 0;
        int pos;
        for (Map<String, Object> result : results) {
            if (i == 0) {//Rempli le tableau avec les entetes
                resultsArray2D = new String[results.size() + 1][result.entrySet().size() + 1];
                pos = 0;
                array = new String[result.entrySet().size() + 1];
                array[pos] = "Nb";
                for (Map.Entry<String, Object> entry : result.entrySet()) {
                    pos += 1;
                    array[pos] = entry.getKey();
                }
                resultsArray2D[i] = array;
            }
            i += 1;
            pos = 0;
            array = new String[result.entrySet().size() + 1];
            array[pos] = "" + i;
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                pos += 1;
                if (entry.getValue() != null) {
                    array[pos] = entry.getValue().toString();
                } else {
                    array[pos] = "";
                }
            }
            resultsArray2D[i] = array;
        }

        printer.print(resultsArray2D);
    }

    private static final char BORDER_KNOT = '+';
    private static final char HORIZONTAL_BORDER = '-';
    private static final char VERTICAL_BORDER = '|';

    private static final String DEFAULT_AS_NULL = "(NULL)";

    private final PrintStream out;
    private final String asNull;

    public PrettyPrinter(PrintStream out) {
        this(out, DEFAULT_AS_NULL);
    }

    public PrettyPrinter(PrintStream out, String asNull) {
        if (out == null) {
            throw new IllegalArgumentException("No print stream provided");
        }
        if (asNull == null) {
            throw new IllegalArgumentException("No NULL-value placeholder provided");
        }
        this.out = out;
        this.asNull = asNull;
    }

    /**
     * Affiche le tableau sur la sortie standard.
     *
     * @param table le tableau à afficher
     */
    public void print(String[][] table) {
        if (table == null) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if (table.length == 0) {
            return;
        }
        final int[] widths = new int[getMaxColumns(table)];
        adjustColumnWidths(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths));
    }

    private void printPreparedTable(String[][] table, int widths[], String horizontalBorder) {
        final int lineLength = horizontalBorder.length();
        out.println(horizontalBorder);
        for (final String[] row : table) {
            if (row != null) {
                out.println(getRow(row, widths, lineLength));
                out.println(horizontalBorder);
            }
        }
    }

    private String getRow(String[] row, int[] widths, int lineLength) {
        final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
        final int maxWidths = widths.length;
        for (int i = 0; i < maxWidths; i++) {
            builder.append(padRight(getCellValue(safeGet(row, i, null)), widths[i])).append(VERTICAL_BORDER);
        }
        return builder.toString();
    }

    private String getHorizontalBorder(int[] widths) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(BORDER_KNOT);
        for (final int w : widths) {
            for (int i = 0; i < w; i++) {
                builder.append(HORIZONTAL_BORDER);
            }
            builder.append(BORDER_KNOT);
        }
        return builder.toString();
    }

    private int getMaxColumns(String[][] rows) {
        int max = 0;
        for (final String[] row : rows) {
            if (row != null && row.length > max) {
                max = row.length;
            }
        }
        return max;
    }

    private void adjustColumnWidths(String[][] rows, int[] widths) {
        for (final String[] row : rows) {
            if (row != null) {
                for (int c = 0; c < widths.length; c++) {
                    final String cv = getCellValue(safeGet(row, c, asNull));
                    final int l = cv.length();
                    if (widths[c] < l) {
                        widths[c] = l;
                    }
                }
            }
        }
    }

    private static String padRight(String s, int n) {
        return format("%1$-" + n + "s", s);
    }

    private static String safeGet(String[] array, int index, String defaultValue) {
        return index < array.length ? array[index] : defaultValue;
    }

    private String getCellValue(Object value) {
        return value == null ? asNull : value.toString();
    }

}
