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
 * <p>Title: OvaloDibujable</p>
 *
 * <p>Description: Clase que permite el dibujo de un ovalo con unas
 * propiedades como tipo de linea, color, relleno etc.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class OvaloDibujable extends GeometriaDibujable {

    /**
     * Variable que indica la esquina superior izquierda del rectangulo que
     * circunscribe al ovalo
     */
    protected Point esqSupIzq;
    /**
     * Variables que indican las dimensiones del ovalo
     */
    protected int anchura = 0;
    /**
     *
     */
    protected int altura = 0;

    /**
     * Construye un ovalo con la esquina superior izquierda del rectangulo que
     * circunscribe al ovalo en el punto (0,0), y con dimensiones iguales a 0.
     */
    public OvaloDibujable() {
        super();

        esqSupIzq = new Point(0, 0);
    }

    /**
     * Construye un ovalo con la esquina superior izquierda del rectangulo que
     * circunscribe al ovalo en el punto indicado por el parametro si no es null
     * ,si no en el punto por defecto (0,0), y con dimensiones iguales a los
     * parametros si son mayores que 0, si no con dimensiones iguales a 0.
     *
     * @param esqSupIzq Point esquina superior izquierda del rectangulo que
     * circunscribe al ovalo.
     * @param anchura int anchura del ovalo
     * @param altura int altura del ovalo
     */
    public OvaloDibujable(Point esqSupIzq, int anchura, int altura) {
        this(esqSupIzq, anchura, altura, null);
    }

    /**
     * Construye un ovalo con la esquina superior izquierda del rectangulo que
     * circunscribe al ovalo en el punto indicado por el parametro si no es null
     * ,si no en el punto por defecto (0,0), y con dimensiones iguales a los
     * parametros si son mayores que 0, si no con dimensiones iguales a 0.
     * Se le aplica el contexto grafico pasado en el parametro si no es null,
     * si no se le aplica el de por defecto.
     *
     * @param esqSupIzq Point esquina superior izquierda del rectangulo que
     * circunscribe al ovalo.
     * @param anchura int anchura del ovalo
     * @param altura int altura del ovalo
     * @param cg ContextoGrafico contexto grafico a aplicar al ovalo
     */
    public OvaloDibujable(Point esqSupIzq, int anchura, int altura,
            ContextoGrafico cg) {
        //Inicializamos las variables de la clase padre
        super();

        //Asignamos los valores correspondientes
        this.esqSupIzq = (esqSupIzq != null) ? esqSupIzq : new Point(0, 0);
        this.anchura = (anchura > 0) ? anchura : 0;
        this.altura = (altura > 0) ? altura : 0;

        //Actualizamos los valores de los limites
        limites.setLocation(this.esqSupIzq);
        limites.setSize(this.anchura, this.altura);

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

        //Aplicamos los atributos
        if (this.cg.getDegradado() == null) {
            if (this.cg.getColorRelleno() != null) {
                g2.setColor(this.cg.getColorRelleno());
                g2.fillOval(esqSupIzq.x, esqSupIzq.y, anchura, altura);
            }
        } else {
            //Establecemos los rendering para que no sea tan lento el proceso
            g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_SPEED);

            g2.setPaint(this.cg.getDegradado());
            g2.fillOval(esqSupIzq.x, esqSupIzq.y, anchura, altura);
        }

        g2.setStroke(this.cg.getEstiloLinea());

        //Dibujamos el rectangulo
        g2.setColor(this.cg.getColorLinea());
        g2.drawOval(esqSupIzq.x, esqSupIzq.y, anchura, altura);

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

            //Establecemos la nueva altura y la nueva anchura
            anchura = Math.abs(limites.width);
            altura = Math.abs(limites.height);

            //Modificamos los puntos
            this.esqSupIzq.x = (int) (limites.getCenterX()
                    - (Math.abs(limites.width) / 2.0));
            this.esqSupIzq.y = (int) (limites.getCenterY()
                    - (Math.abs(limites.height) / 2.0));
        }
    }

    /**
     * Obtiene la anchura >=0 del ovalo
     *
     * @return int con la anchura del ovalo
     */
    public int getAnchura() {
        return this.anchura;
    }

    /**
     * Obtiene la altura >=0 del ovalo
     *
     * @return int con la altura del ovalo
     */
    public int getAltura() {
        return this.altura;
    }
}
