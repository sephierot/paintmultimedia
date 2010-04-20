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
 * <p>Title: PuntoDibujable</p>
 *
 * <p>Description: Clase que permite el dibujo de un punto con unas
 * propiedades como color, grosor etc.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class PuntoDibujable extends OvaloDibujable {

    /**
     * Crea un punto dibujable con la esqSupIzq del cuadrado que lo engloba
     * en (0,0) y con los parametros por defecto de su padre.
     */
    public PuntoDibujable() {
        super();
    }

    /**
     * Crea un punto dibujable con la esqSupIzq del cuadrado que lo engloba
     * indicada en el parametro si es distinto de null, si no en el punto (0,0)
     *  y con los parametros por defecto de su padre.
     *
     * @param esqSupIzq Point indica la esqSupIzq del cuadrado que lo engloba
     */
    public PuntoDibujable(Point esqSupIzq) {
        this(esqSupIzq, null);
    }

    /**
     * Crea un punto dibujable con la esqSupIzq del cuadrado que lo engloba
     * indicada en el parametro si es distinto de null, si no en el punto (0,0)
     * y con un contexto grafico indicado por el parametro si no es null si no
     * se toma el contexto grafico por defecto.
     *
     * @param esqSupIzq Point indica la esqSupIzq del cuadrado que lo engloba
     * @param cg contexto grafico del punto
     */
    public PuntoDibujable(Point esqSupIzq,
            ContextoGrafico cg) {
        //Inicializamos las variables de la clase padre comprobando si cg es nulo o no
        super(esqSupIzq,
                (cg != null) ? (int) cg.getEstiloLinea().getLineWidth() : 1,
                (cg != null) ? (int) cg.getEstiloLinea().getLineWidth() : 1,
                cg);
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

        //Aplicamos los atributos
        g2.setColor(this.cg.getColorLinea());
        g2.fillOval(esqSupIzq.x, esqSupIzq.y, anchura, anchura);

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
    @Override
    public void setLimites(Rectangle limites) {
        if (limites != null) {
            //Actualizamos los limites
            this.limites.setLocation(limites.getLocation().x + limites.width,
                    limites.getLocation().y + limites.height);

            //Modificamos el punto de origen del la esqSupIzq del rectangulo que
            //lo contiene
            this.esqSupIzq.x = (int) (limites.getCenterX()
                    - (Math.abs(limites.width) / 2.0));
            this.esqSupIzq.y = (int) (limites.getCenterY()
                    - (Math.abs(limites.height) / 2.0));
        }
    }

    /**
     * Devuelve el punto de la esqSupIzq del cuadrado que lo contine
     *
     * @return Point punto de la esqSupIzq del cuadrado que lo contine
     */
    public Point getPunto() {
        return this.esqSupIzq;
    }
}
