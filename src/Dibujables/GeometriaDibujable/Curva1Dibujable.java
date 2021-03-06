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



package Dibujables.GeometriaDibujable;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.AffineTransform;

/**
 * <p>Title: PaintMultimedia</p>
 *
 * <p>Description: Aplicacion que permite realizar dibujos, mostrar imagenes de
 * diferentes formatos y reproducir sonido y video</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class Curva1Dibujable extends LineaDibujable {

    /**
     * Variable que almacena el punto de control de la curva
     */
    Point ptoControl;

    /**
     * Construye una curva con inicio, fin y punto de control en el punto (0,0)
     */
    public Curva1Dibujable() {
        super();
        ptoControl = new Point();
    }

    /**
     * Construye una curva con el origen y el final indicados en los parametros
     * si no son null, en cuyo caso se establecera el punto por defecto que es
     * (0,0). El punto de control se establece por defecto en el origen de la
     * curva
     *
     * @param origen Point punto de origen de la curva
     * @param puntoFinal Point punto final de la curva
     */
    public Curva1Dibujable(Point origen, Point puntoFinal) {
        this(origen, origen, puntoFinal, null);
    }

    /**
     * Construye una curva con el origen y el final indicados en los parametros
     * si no son null, en cuyo caso se establecera el punto por defecto que es
     * (0,0). El punto de control se establece por defecto en el origen de la
     * curva. Se establece un contexto grafico
     * si el parametro no es null, si no se establece el de por defecto.
     *
     * @param origen Point punto de origen de la curva
     * @param puntoFinal Point punto final de la curva
     * @param cg ContextoGrafico de la curva
     */
    public Curva1Dibujable(Point origen, Point puntoFinal, ContextoGrafico cg) {
        this(origen, origen, puntoFinal, cg);
    }

    /**
     * Construye una curva con el origen y el final indicados en los parametros
     * si no son null, en cuyo caso se establecera el punto por defecto que es
     * (0,0). El punto de control es el indicado por el parametro si no es null
     * en cuyo caso pasaria a ser el origen. Se establece un contexto grafico
     * si el parametro no es null, si no se establece el de por defecto.
     *
     * @param origen Point punto de origen de la curva
     * @param puntoControl Point punto de control de la curva
     * @param puntoFinal Point punto final de la curva
     * @param cg ContextoGrafico de la curva
     */
    public Curva1Dibujable(Point origen, Point puntoControl, Point puntoFinal, ContextoGrafico cg) {
        super(origen, puntoFinal, cg);

        if (this.ptoControl != null) {
            this.ptoControl = puntoControl;
        } else {
            this.ptoControl = new Point(this.puntoInicial);
        }
    }

    /**
     * Implementacion del metodo abstracto de la clase padre. Dibuja la figura
     * en el grafico pasado como parametro.
     *
     * @param g Graphics donde se desea dibujar la figura.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Variable que nos sirve para reestablecer la forma original posteriormente
        AffineTransform actual = g2.getTransform();

        //Aplicamos la transformacion del objeto si la tiene
        if (!this.transformaciones.isIdentity()) {
            g2.transform(this.transformaciones);
        }

        QuadCurve2D quad = new QuadCurve2D.Double(this.puntoInicial.x, this.puntoInicial.y, this.ptoControl.x, this.ptoControl.y, this.puntoFinal.x, this.puntoFinal.y);

        g2.setStroke(this.cg.getEstiloLinea());

        //Dibujamos la linea
        g2.setColor(this.cg.getColorLinea());
        g2.draw(quad);

        //Recuperamos la forma original que tenia antes de la transformacion
        g2.setTransform(actual);
    }

    /**
     * Sobreescribe el metodo del padre para mover la figura
     *
     * @param p Point punto de origen al que se quiere mover la curva
     */
    @Override
    public void mover(Point p) {
        if (p != null) {
            //Movemos el punto de control
            this.ptoControl.translate(p.x - this.puntoInicial.x,
                    p.y - this.puntoInicial.y);

            //Movemos los extremos
            super.mover(p);
        }
    }

    /**
     * Obtiene el punto de control de la curva
     *
     * @return Point punto de control de la curva
     */
    public Point getPuntoControl() {
        return this.ptoControl;
    }

    /**
     * Establece el punto de control de la curva si no es null.
     *
     * @param puntoControl Point nuevo punto de control de la curva
     */
    public void setPuntoControl(Point puntoControl) {
        if (puntoControl != null) {
            this.ptoControl = puntoControl;
        }
    }
}
