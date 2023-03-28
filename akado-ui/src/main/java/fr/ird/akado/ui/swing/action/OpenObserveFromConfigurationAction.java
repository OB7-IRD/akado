package fr.ird.akado.ui.swing.action;

import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

/**
 * To open the ObServe local database through the H2 server mode.
 * <p>
 * Created on 27/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class OpenObserveFromConfigurationAction extends AbstractAction {

    private final AkadoController akadoController;
    private final InfoListeners listeners;

    public OpenObserveFromConfigurationAction(AkadoController vpc, InfoListeners listeners) {
        this.listeners = listeners;
        this.akadoController = vpc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = AkadoAvdthProperties.OBSERVE_JDBC_URL;
//jdbc:h2:file:/home/tchemit/.observe/db/obstuna/obstuna;FILE_LOCK=file;LOG=0;MODE=postgresql;DEFAULT_LOCK_TIMEOUT=100;DB_CLOSE_DELAY=0;LOCK_MODE=3;TRACE_LEVEL_FILE=0;TRACE_LEVEL_SYSTEM_OUT=0;CACHE_SIZE=65536;MVCC=true
        //"jdbc:h2:tcp://127.0.1.1:9093//home/tchemit/.observe/db/obstuna/obstuna";
        String login = AkadoAvdthProperties.OBSERVE_JDBC_LOGIN;
        String password = AkadoAvdthProperties.OBSERVE_JDBC_PASSWORD;

        if (url == null || url.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Jdbc url is empty, please fill the akado configuration file entry: observe_jdbc_url", "Akado error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (login == null || login.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Jdbc login is empty, please fill the akado configuration file entry: observe_jdbc_login", "Akado error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Jdbc password is empty, please fill the akado configuration file entry: observe_jdbc_password", "Akado error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JdbcConfiguration configuration = new JdbcConfigurationBuilder().forDatabase(url, login, password);

        ObserveService service = ObserveService.getService();
        try {
            service.init(configuration);
            service.open();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Can not open database:\n" + ex.getLocalizedMessage(), "Akado error", JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            service.close();
        }
        this.akadoController.setObserveDataBase(configuration);
        this.listeners.fireInfoUpdated();
    }
}
