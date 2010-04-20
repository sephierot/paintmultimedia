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




package Dibujables;

import java.awt.*;
import java.util.Vector;

/**
 * <p>Title: PaintMultimedia</p>
 *
 * <p>Description: Clase que permite el dibujo libre dibujando con un lapiz
 * una sucesion de pixeles como un color.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class DibujoLapizDibujable implements Dibujable {

    /**
     * Variable que posee le vector de puntos a dibujar indicando el trazo
     */
    protected Vector puntos;
    /**
     * Variable que indica el color del trazo
     */
    protected Color colorLinea;

    /**
     * Construye un objeto dibujo lapiz vacio con color negro en el trazo
     */
    public DibujoLapizDibujable() {
        //Color por defecto el negro
        this(null, Color.BLACK);
    }

    /**
     * Construye un objeto dibujo lapiz con el trazo indicado por el parametro
     * si no es null, si no se contruye un trazo vacio, el color por defecto es
     * el negro.
     *
     * @param v Vector de puntos que definen el trazo que con el que se desea
     * el objeto
     */
    public DibujoLapizDibujable(Vector v) {
        //Color por defecto el negro
        this(v, Color.BLACK);
    }

    /**
     * Construye un objeto dibujo con el trazo indicado por el parametro si no
     * es null, si no se contruye un trazo vacio. El color es asignado al trazo
     * si no es null en cuyo caso se tomara el de por defecto que es el negro.
     *
     * @param puntos Vector de puntos que definen el trazo que con el que se
     * desea el objeto
     * @param colorLinea Color del trazo
     */
    public DibujoLapizDibujable(Vector puntos, Color colorLinea) {
        this.puntos = (puntos != null) ? puntos : new Vector();
        this.puntos = new Vector();
        this.colorLinea = (colorLinea != null) ? colorLinea : Color.BLACK;
    }

    /**
     * Implementacion del metodo de interfaz de la que hereda. Dibuja el vector
     * de puntos en el grafico pasado como parametro.
     *
     * @param g Graphics donde se desea dibujar la figura.
     */
    public void dibujar(Graphics g) {
        //Miramos si hay puntos
        if (!this.puntos.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            Point punto;

            g2.setColor(this.colorLinea);
            g2.setStroke(new BasicStroke(1));

            //Dibujamos todos los puntos
            if (this.puntos.size() == 1) {
                punto = (Point) this.puntos.firstElement();
                g2.drawLine(punto.x, punto.y, punto.x, punto.y);
            } else {
                int[] x = new int[this.puntos.size()];
                int[] y = new int[this.puntos.size()];

                for (int i = 0; i < x.length; i++) {
                    punto = (Point) this.puntos.elementAt(i);
                    x[i] = punto.x;
                    y[i] = punto.y;
                }
                g2.drawPolyline(x, y, x.length);
            }
        }
    }

    /**
     * Obtiene el color del trazo
     *
     * @return Color del trazo
     */
    public Color getColorLinea() {
        return this.colorLinea;
    }

    /**
     * Establece el color del trazo si el parametro no es null
     *
     * @param colorLinea Color del trazo
     */
    public void setColorLinea(Color colorLinea) {
        if (colorLinea != null) {
            this.colorLinea = colorLinea;
        }
    }

    /**
     * Obtiene el numero de puntos por los que se forma el trazo
     *
     * @return Vector con el numero de puntos del trazo
     */
    public Vector getPuntos() {
        return this.puntos;
    }

    /**
     * Establece un nuevo trazo formado por los puntos del parametro si este no
     * es null
     *
     * @param puntos Vector de puntos a establecer
     */
    public void setPuntos(Vector puntos) {
        if (puntos != null) {
            this.puntos = puntos;
        }
    }

    /**
     * Añade un punto nuevo del trazo si el parametro no es nulo
     *
     * @param punto Point a añadir
     */
    public void addPunto(Point punto) {
        if (punto != null) {
            //Comprobamos que no esta para no meter los puntos repetidos y ahorrar espacio
            if (!this.puntos.contains(punto)) {
                this.puntos.addElement(punto);
            }
        }
    }

    /**
     * Borra el trazo por completo manteniendo su color.
     */
    public void BorraPuntos() {
        this.puntos.removeAllElements();
    }

    public void rotar(double ngrados) {
    }

    public void escalar(double factorX, double factorY) {
    }

    public void trasladar(double deltaX, double deltaY) {
    }
}

