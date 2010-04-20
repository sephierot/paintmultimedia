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

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: PaintMultimedia</p>
 *
 * <p>Description: Representa a una paleta de colores de filas x columnas
 *  colores situada en un panel en el que se situan estos</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class PaletaColores extends JPanel {

    private int nColores = 0;    //Indica el numero de colores de la paleta
    private short nFilas = 1;    //Numero de filas en el que se quieren distribuir
    private short nColumnas = 0; //Numero de columnas en el que se quieren distribuir
    private Color[] colores = null; //Colores de la paleta
    private JLabel[] etiqColores = null;   //Etiquetas con los colores de la paleta
    private Dimension tamEtiquetas = new Dimension(15, 15); //Tamaño de las etiquetas
    private GridLayout glayout = new GridLayout(nFilas, nColumnas, 4, 4);

    /**
     *
     */
    public PaletaColores() {
        super();

        //Establecemos el layout
        this.setLayout(this.glayout);
    }

    /**
     *
     * @param colores
     * @param tamano
     */
    public PaletaColores(int colores, Dimension tamano) {
        //Por defecto creamos una paleta cuadrada filas == columnas
        this(colores, (short) Math.ceil(Math.sqrt(colores)), tamano);
    }

    /**
     *
     * @param colores
     * @param filas
     * @param tamano
     */
    public PaletaColores(int colores, short filas, Dimension tamano) {
        super();

        //Establecemos las filas, las columnas y los colores
        if (colores > 0) {
            this.nColores = colores; //Colores debe ser positivo

            //Comprobamos que el numero de filas en el que se reparten los
            //colores es positivo y como mucho un color por fila.
            if (filas > 0 && filas <= colores) {
                this.nFilas = filas;
            } else {
                this.nFilas = 1;
            }

            //Calculamos el numero de columnas a partir del de filas
            this.nColumnas = (short) Math.ceil((double) this.nColores / this.nFilas);

            //Establecemos el tamaño de las etiquetas
            this.tamEtiquetas = tamano;

            //Reservamos memoria para los colores y para las etiquetas
            this.colores = new Color[this.nColores];
            this.etiqColores = new JLabel[this.nColores];

            //Inicializamos los colores por defecto a blanco.
            for (int i = 0; i < this.nColores; i++) {
                this.colores[i] = new Color(1.0f, 1.0f, 1.0f);
                this.etiqColores[i] = new JLabel();

                //Escribimos las propiedades de las etiquetas de color
                this.etiqColores[i].setPreferredSize(this.tamEtiquetas);
                this.etiqColores[i].setOpaque(true);
                this.etiqColores[i].setBorder(BorderFactory.createLoweredBevelBorder());
                this.etiqColores[i].setBackground(this.colores[i]);

                //Añadimos las etiquetas al panel
                this.add(this.etiqColores[i], i);
            }
            //Establecemos las propiedades del layout
            this.glayout.setColumns(this.nColumnas);
            this.glayout.setRows(this.nFilas);
        }
        //Establecemos el layout
        this.setLayout(this.glayout);
    }

    /**
     *
     * @return
     */
    public short getColumnas() {
        return this.nColumnas;
    }

    /**
     *
     * @return
     */
    public short getFilas() {
        return this.nFilas;
    }

    /**
     *
     * @return
     */
    public int getNColores() {
        return this.nColores;
    }

    /**
     *
     * @return
     */
    public Color[] getColores() {
        return this.colores;
    }

    /**
     *
     * @return
     */
    public Dimension getTamano() {
        return this.tamEtiquetas;
    }

    /**
     *
     * @param fila
     * @param columna
     * @return
     */
    public Color getColor(short fila, short columna) {
        //Comprobamos que la posicion es correcta en la paleta
        if ((fila > 0) && (columna > 0) && (fila <= this.nFilas) && (columna <= this.nColumnas)) {
            //Devolvemos el color del elemento
            return this.etiqColores[this.nColumnas * (fila - 1) + (columna - 1)].getBackground();
        } else {
            return null;
        }
    }

    /**
     *
     * @param posicioncolor
     * @return
     */
    public Color getColor(int posicioncolor) {
        //Comprobamos que la posicion es correcta en la paleta
        if (posicioncolor > 0 && posicioncolor <= this.nColores) {
            //Devolvemos el color del elemento que esta en la posicioncolor
            return this.etiqColores[posicioncolor - 1].getBackground();
        } else {
            return null;
        }
    }

    /**
     *
     * @param posicioncolor
     * @param color
     * @return
     */
    public boolean setColor(int posicioncolor, Color color) {
        //Comprobamos que la posicion es correcta en la paleta
        if (posicioncolor > 0 && posicioncolor <= this.nColores) {
            //Almacenamos el color del elemento que esta en la posicioncolor
            this.etiqColores[posicioncolor - 1].setBackground(color);

            return true;
        }
        return false;
    }

    /**
     *
     * @param colores
     * @param filas
     * @return
     */
    public boolean setColores(Color[] colores, short filas) {
        //Comprobamos que colores contenga algun elemento
        if (colores == null) {
            return false;
        }

        //Comprobamos que el numero de filas sea positivo y no mayor que el numuero de colores nuevos
        if ((filas <= 0) || (filas > colores.length)) {
            return false;
        }

        this.nColores = colores.length;

        //Reservamos memoria para las etiquetas nuevas
        this.etiqColores = new JLabel[this.nColores];

        //Vaciamos el panel de las antiguas etiquetas y metemos las nuevas
        this.removeAll();

        //Copiamos los valores de los nuevos colores
        for (int i = 0; i < this.nColores; i++) {
            this.colores[i] = colores[i];

            this.etiqColores[i] = new JLabel();

            //Escribimos las propiedades de las etiquetas de color
            this.etiqColores[i].setPreferredSize(this.tamEtiquetas);
            this.etiqColores[i].setOpaque(true);
            this.etiqColores[i].setBorder(BorderFactory.createLoweredBevelBorder());
            this.etiqColores[i].setBackground(this.colores[i]);

            //Añadimos las etiquetas al panel
            this.add(this.etiqColores[i], i);
        }

        this.nFilas = filas;

        //Calculamos el numero de columnas a partir del de filas
        this.nColumnas = (short) Math.ceil((double) this.nColores
                / this.nFilas);

        //Establecemos las nuevas propiedades del layout
        this.glayout.setColumns(this.nColumnas);
        this.glayout.setRows(this.nFilas);

        return true;
    }

    /**
     *
     * @param filas
     * @return
     */
    public boolean setFilas(short filas) {
        //Comprobamos que el numero de filas sea positivo y no mayor que el numero de colores nuevos
        if ((filas <= 0) || (filas > colores.length)) {
            return false;
        }

        this.nFilas = filas;

        //Actualizamos el numero de columnas a partir del de filas
        this.nColumnas = (short) Math.ceil((double) this.nColores
                / this.nFilas);

        //Actualizamos las propiedades del layout
        this.glayout.setColumns(this.nColumnas);
        this.glayout.setRows(this.nFilas);

        return true;
    }

    /**
     *
     * @param tamano
     * @return
     */
    public boolean setTamanoColor(Dimension tamano) {
        if (tamano != null) {
            this.tamEtiquetas = tamano;
            return true;
        }
        return false;
    }

    /**
     *
     * @param color
     * @param fila
     * @param columna
     * @return
     */
    public boolean setColor(Color color, short fila, short columna) {
        //Comprobamos que el color no sea nulo
        if (color == null) {
            return false;
        }

        //Comprobamos que la posicion es correcta en la paleta
        if ((fila > 0) && (columna > 0) && (fila <= this.nFilas) && (columna <= this.nColumnas)) {
            //Modificamos el array de colores y la etiqueta correspondiente
            this.colores[this.nColumnas * (fila - 1) + (columna - 1)] = color;
            this.etiqColores[this.nColumnas * (fila - 1) + (columna - 1)].setBackground(this.colores[this.nColumnas * (fila - 1) + (columna - 1)]);

            return true;
        }
        return false;
    }

    /**
     *
     * @param tamano
     * @return
     */
    public boolean setTamColores(Dimension tamano) {
        if (tamano == null) {
            return false;
        }

        for (int i = 0; i < this.etiqColores.length; i++) {
            this.etiqColores[i].setSize(tamano);
        }

        return true;
    }
}
