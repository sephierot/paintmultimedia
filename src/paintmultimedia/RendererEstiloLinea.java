/*
    Copyright(c) 2006 David Armenteros Escabias
    
    This file is part of PaintMultimedia.

    PaintMultimedia is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


package paintmultimedia;

import java.awt.Component;

import javax.swing.*;

/**
 * <p>Title: PaintMultimedia</p>
 *
 * <p>Description: Renderer que permite el dibujado dentro de un comboBox del estilo
 * de linea</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class RendererEstiloLinea extends JLabel implements ListCellRenderer {

    private ImageIcon[] estilosLineas;

    /**
     *
     */
    public RendererEstiloLinea() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected,
            boolean cellHasFocus) {
        int selectedIndex = ((Integer) value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Establecemos el icono

        if (selectedIndex < estilosLineas.length) {
            ImageIcon icono = estilosLineas[selectedIndex];
            setIcon(icono);
        }

        return this;
    }
}
