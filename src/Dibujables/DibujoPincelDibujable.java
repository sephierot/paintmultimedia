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
 * <p>Title: DibujoPincelDibujable</p>
 *
 * <p>Description: Clase que permite el dibujo libre simulando un pincel con
 * un color de linea, un estilo y un grosor en la linea</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class DibujoPincelDibujable extends DibujoLapizDibujable {

    /**
     * Variable que almacena el estilo del trazo
     */
    protected BasicStroke estilo;

    /**
     * Construye un objeto dibujo pincel vacio con color negro en el trazo
     * y un estilo de linea por defecto
     */
    public DibujoPincelDibujable() {
        this(null, Color.BLACK, null);
    }

    /**
     * Construye un objeto dibujo pincel con el trazo indicado por el parametro
     * si no es null, si no se contruye un trazo vacio, el color por defecto es
     * el negro asi como el estilo de linea
     *
     * @param v Vector de puntos que definen el trazo que con el que se desea
     * el objeto
     */
    public DibujoPincelDibujable(Vector v) {
        this(v, Color.BLACK, null);
    }

    /**
     * Construye un objeto dibujo pincel con el trazo indicado por el parametro
     * si no es null, si no se contruye un trazo vacio. El color es asignado al
     * trazo si no es null en cuyo caso se tomara el de por defecto que es el
     * negro. Se le establecera un estilo de linea por defecto.
     *
     * @param puntos Vector de puntos que definen el trazo que con el que se
     * desea el objeto
     * @param colorLinea Color del trazo
     */
    public DibujoPincelDibujable(Vector puntos, Color colorLinea) {
        this(puntos, colorLinea, null);
    }

    /**
     * Construye un objeto dibujo pincel con el trazo indicado por el parametro
     * si no es null, si no se contruye un trazo vacio. El color es asignado al
     * trazo si no es null en cuyo caso se tomara el de por defecto que es el
     * negro. Se le establecera el estilo de linea indicado en el parametro si
     * no es null, si no se establecera el de por defecto.
     *
     * @param puntos Vector de puntos que definen el trazo que con el que se
     * desea el objeto
     * @param colorLinea Color del trazo
     * @param estilo estilo de linea del trazo
     */
    public DibujoPincelDibujable(Vector puntos, Color colorLinea, BasicStroke estilo) {
        super(puntos, colorLinea);

        this.estilo = (estilo != null) ? estilo : new BasicStroke();
    }

    /**
     * Sobreescribe el metodo de la clase padre. Dibuja la el vector de puntos
     * en el grafico pasado como parametro.
     *
     * @param g Graphics donde se desea dibujar la figura.
     */
    @Override
    public void dibujar(Graphics g) {
        //Miramos si hay puntos
        if (!this.puntos.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            Point punto;

            g2.setColor(this.colorLinea);
            g2.setStroke(this.estilo);

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
     * Obtiene el estilo de linea del trazo
     *
     * @return BasicStroke indicando el estilo de linea
     */
    public BasicStroke getEstilo() {
        return this.estilo;
    }

    /**
     * Establece el estilo de linea del trazo si el parametro no es null
     *
     * @param estilo BasicStroke indica el estilo del trazo
     */
    public void setEstilo(BasicStroke estilo) {
        if (estilo != null) {
            this.estilo = estilo;
        }
    }
}
