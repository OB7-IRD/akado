/*
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.core;

import fr.ird.akado.core.common.AkadoMessages;
import fr.ird.akado.core.selector.AbstractSelectionCriteria;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe mettant en scène les différents composants nécessaires à la validation
 * des bases.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 */
public class AkadoCore {

    private List<DataBaseInspector> dataBaseInspectors;
    
    private final AkadoMessages akadoMessages;

    public AkadoCore() {
        dataBaseInspectors = new ArrayList<>();
        akadoMessages = new AkadoMessages();

    }


    public List<DataBaseInspector> getDataBaseInspectors() {
        return dataBaseInspectors;
    }

    public void setDataBaseInspectors(List<DataBaseInspector> dataBaseInspectors) {
        this.dataBaseInspectors = dataBaseInspectors;
    }

    public boolean addDataBaseValidator(DataBaseInspector dataBaseInspector) {
        return this.dataBaseInspectors.add(dataBaseInspector);
    }

    public void execute() throws Exception {
        for (DataBaseInspector dataBaseInspector : getDataBaseInspectors()) {
            dataBaseInspector.validate();
        }
    }

}
