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

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * <p>Title: LineaDibujable</p>
 *
 * <p>Description: Clase que permite el dibujo de una linea con unas
 * propiedades como tipo de linea, color, grosor etc</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class LineaDibujable extends GeometriaDibujable {

    /**
     * Puntos que indican el comienzo y el final d la linea
     */
    Point puntoInicial, puntoFinal;

    /**
     * Construye una linea con puntos inicial y final en (0,0), con el contexto
     * grafico por defecto
     */
    public LineaDibujable() {
        super();

        puntoInicial = new Point(0, 0);
        puntoFinal = new Point(0, 0);
    }

    /**
     * Construye una linea con puntos inicial y final indicados por los
     * parametros si no son null, es cuyo caso al punto que fuese null se le
     * asignaria el punto por defecto (0,0)
     *
     * @param origen Point origen de la linea a crear
     * @param puntoFinal Point final de la linea a crear
     */
    public LineaDibujable(Point origen, Point puntoFinal) {
        this(origen, puntoFinal, null);
    }

    /**
     * Construye una linea con puntos inicial y final indicados por los
     * parametros si no son null, es cuyo caso al punto que fuese null se le
     * asignaria el punto por defecto (0,0). Se le asigna un contexto grafico si
     * el parametro es distinto de null, si no se establece el de por defecto.
     *
     * @param origen Point origen de la linea a crear
     * @param puntoFinal Point final de la linea a crear
     * @param cg ContextoGrafico a asignar a la linea.
     */
    public LineaDibujable(Point origen, Point puntoFinal,
            ContextoGrafico cg) {
        //Inicializamos las variables de la clase padre
        super();

        //Asignamos los valores correspondientes
        this.puntoInicial = (origen != null) ? origen : new Point(0, 0);
        this.puntoFinal = (puntoFinal != null) ? puntoFinal : new Point(0, 0);

        //Actualizamos los valores de los limites
        limites.setFrameFromDiagonal(this.puntoInicial, this.puntoFinal);

        //Aplicamos el contexto grafico
        if (cg != null) {
            this.cg = cg;
        }
    }

    /**
     * Implementacion del metodo abstracto de la clase padre. Dibuja la figura
     * en el grafico pasado como parametro.
     *
     * @param g Graphics donde se desea dibujar la figura.
     */
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Variable que nos sirve para reestablecer la forma original posteriormente
        AffineTransform actual = g2.getTransform();

        //Aplicamos la transformacion del objeto si la tiene
        if (!this.transformaciones.isIdentity()) {
            g2.transform(this.transformaciones);
        }

        g2.setStroke(this.cg.getEstiloLinea());

        //Dibujamos la linea
        g2.setColor(this.cg.getColorLinea());



        g2.drawLine(puntoInicial.x, puntoInicial.y, puntoFinal.x, puntoFinal.y);

        //Recuperamos la forma original que tenia antes de la transformacion
        g2.setTransform(actual);
    }

    /**
     * Implementa el metodo abstracto de la clase padre.
     * Establece los nuevos limites de la figura, redimensionado si es necesario
     * la propia figura, se actualizan las variables del propio objeto que sean
     * afectadas por este cambio
     *
     * @param limites Rectangle que engloba la figura
     */
    public void setLimites(Rectangle limites) {
        if (limites != null) {
            //Actualizamos los limites
            this.limites = limites;

            this.puntoInicial = limites.getLocation();
            this.puntoFinal.setLocation(puntoInicial.x + this.limites.width, puntoInicial.y + this.limites.height);
        }
    }

    /**
     * Obtiene el punto inicial de la linea
     *
     * @return Point con el punto inicial de la linea
     */
    public Point getPuntoInicial() {
        return this.puntoInicial;
    }

    /**
     * Obtiene el punto final de la linea
     *
     * @return Point con el punto final de la linea
     */
    public Point getPuntoFinal() {
        return this.puntoFinal;
    }

    /**
     * setPuntoInicial establece el punto inicial de la linea si no es null.
     *
     * @param puntoInicial Point punto inicial de la linea a establecer
     */
    public void setPuntoInicial(Point puntoInicial) {
        if (puntoInicial != null) {
            this.puntoInicial = puntoInicial;

            //Actualizamos los limites
            this.limites.setLocation(puntoInicial);
            this.limites.setSize(this.puntoFinal.x - this.puntoInicial.x,
                    this.puntoFinal.y - this.puntoInicial.y);
        }
    }

    /**
     * Establece el punto final de la linea si no es null.
     *
     * @param puntoFinal Point punto final de la linea a establecer
     */
    public void setPuntoFinal(Point puntoFinal) {
        if (puntoFinal != null) {
            this.puntoFinal = puntoFinal;

            //Actualizamos los limites
            this.limites.setSize(this.puntoFinal.x - this.puntoInicial.x,
                    this.puntoFinal.y - this.puntoInicial.y);
        }
    }
}
