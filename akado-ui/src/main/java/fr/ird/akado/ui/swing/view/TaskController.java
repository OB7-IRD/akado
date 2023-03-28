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
package fr.ird.akado.ui.swing.view;

import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.swing.DatabaseType;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.lang.Objects2;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

/**
 * The task controller handles all request coming from the {@link TaskView}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 3 juin 2014
 * @see TaskView
 * @since 2.0
 */
public class TaskController {

    private final InfoListeners listeners;
    private final TaskView taskView;
    private final DatabaseType databaseType;
    private final File file;
    private final Path baseDirectory;
    private final JdbcConfiguration jdbcConfiguration;

    public InfoListeners getListeners() {
        return listeners;
    }

    public TaskController(DatabaseType databaseType, File file, InfoListeners listeners) {
        this.databaseType = Objects.requireNonNull(databaseType);
        this.file = Objects.requireNonNull(file);
        switch (databaseType) {
            case AVDTH:
                this.baseDirectory = file.toPath().getParent();
                this.jdbcConfiguration = new JdbcConfigurationBuilder().forDatabase(AkadoAvdthProperties.PROTOCOL_JDBC_ACCESS + file, "", "", Objects2.forName(AkadoAvdthProperties.JDBC_ACCESS_DRIVER));
                break;
            case OBSERVE:
                this.baseDirectory = ObserveService.createH2DatabaseFromBackupPath(file.toPath());
                this.jdbcConfiguration = ObserveService.createH2DatabaseFromBackup(baseDirectory, file.toPath());
                break;
            default:
                throw new IllegalStateException("Can't manage database type: " + databaseType);
        }
        System.out.println("TaskController " + listeners);
        this.listeners = listeners;
        this.taskView = new TaskView(this);
    }

    public TaskController(DatabaseType databaseType, JdbcConfiguration jdbcConfiguration, InfoListeners listeners) {
        this.databaseType = Objects.requireNonNull(databaseType);
        this.baseDirectory = Path.of(AAProperties.STANDARD_DIRECTORY);
        this.file = null;
        this.jdbcConfiguration = jdbcConfiguration;
        System.out.println("TaskController " + listeners);
        this.listeners = listeners;
        this.taskView = new TaskView(this);
    }

    /**
     * @return the type of database
     */
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public Path getBaseDirectory() {
        return baseDirectory;
    }

    public String getDatabaseLabel() {
        if (file == null) {
            return String.format("[%s] %s", getDatabaseType(), "from configuration");
        }
        return String.format("[%s] %s", getDatabaseType(), file.getName());
    }

    /**
     * Getter on the task view.
     *
     * @return the task view
     */
    public TaskView getTaskView() {
        return taskView;
    }
}
