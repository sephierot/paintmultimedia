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
 * <p>Title: RectanguloDibujable</p>
 *
 * <p>Description: Clase que permite el dibujo de un rectangulo con unas
 * propiedades como tipo de linea, color, relleno etc.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class RectanguloDibujable extends GeometriaDibujable {

    /**
     * Variables que indican las esquinas del rectangulo
     */
    protected Point esqSupIzq, esqSupDer, esqInfDer, esqInfIzq;
    /**
     * Variables que indican las dimensiones del rectangulo
     */
    protected int anchura = 0;
    /**
     *
     */
    protected int altura = 0;

    /**
     * Crea un rectangulo dibujable con dimension 0, todos sus puntos en (0,0)
     * y con los parametros por defecto de su padre
     */
    public RectanguloDibujable() {
        //Inicializamos las variables de la clase padre
        super();

        //Situamos el origen del rectangulo por defecto en la posicion (0,0)
        esqSupIzq = new Point(0, 0);
        esqSupDer = new Point(0, 0);
        esqInfDer = new Point(0, 0);
        esqInfIzq = new Point(0, 0);
    }

    /**
     * Crea un rectangulo con un origen y unas dimensiones. Si origen es null
     * entonces se crea en el punto (0,0), si alguna dimension es negativa se
     * inicializara a 0 ese valor.
     *
     * @param origen Point de origen del rectangulo, esquina superior izquierda.
     * @param anchura int representando la anchura del rectangulo
     * @param altura int representando la altura del rectangulo
     */
    public RectanguloDibujable(Point origen, int anchura, int altura) {
        this(origen, anchura, altura, null);
    }

    /**
     * Crea un rectangulo con un origen y unas dimensiones. Si origen es null
     * entonces se crea en el punto (0,0), si alguna dimension es negativa se
     * inicializara a 0 ese valor, inicializa el contexto grafico de la figura
     * al valor pasado como parametro si este es distinto de null, si no se crea
     * un contexto grafico por defecto.
     *
     * @param origen Point de origen del rectangulo, esquina superior izquierda.
     * @param anchura int representando la anchura del rectangulo
     * @param altura int representando la altura del rectangulo
     * @param cg
     */
    public RectanguloDibujable(Point origen, int anchura, int altura,
            ContextoGrafico cg) {
        //Inicializamos las variables de la clase padre
        super();

        //Asignamos los valores correspondientes
        esqSupIzq = (origen != null) ? origen : new Point(0, 0);
        this.anchura = (anchura > 0) ? anchura : 0;
        this.altura = (altura > 0) ? altura : 0;

        //Calculamos los otros tres puntos
        esqSupDer = new Point(esqSupIzq.x + anchura, esqSupIzq.y);
        esqInfDer = new Point(esqSupDer.x, esqSupDer.y + altura);
        esqInfIzq = new Point(esqSupIzq.x, esqInfDer.y);

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
        if (cg.getDegradado() == null) {
            if (this.cg.getColorRelleno() != null) {
                g2.setColor(this.cg.getColorRelleno());
                g2.fillRect(esqSupIzq.x, esqSupIzq.y, anchura, altura);
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

            g2.setPaint(cg.getDegradado());
            g2.fillRect(esqSupIzq.x, esqSupIzq.y, anchura, altura);
        }

        g2.setStroke(this.cg.getEstiloLinea());

        //Dibujamos el rectangulo
        g2.setColor(this.cg.getColorLinea());
        g2.drawRect(esqSupIzq.x, esqSupIzq.y, anchura, altura);

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
            this.esqSupDer.setLocation(esqSupIzq.x + anchura, esqSupIzq.y);
            this.esqInfDer.setLocation(esqSupDer.x, esqSupDer.y + altura);
            this.esqInfIzq.setLocation(esqSupIzq.x, esqInfDer.y);
        }
    }

    /**
     * Obtiene la anchura del rectangulo
     *
     * @return int >= 0 con la anchura del rectangulo
     */
    public int getAnchura() {
        return this.anchura;
    }

    /**
     * Obtiene la altura del rectangulo
     *
     * @return int >=0 con la altura del rectangulo
     */
    public int getAltura() {
        return this.altura;
    }

    /**
     * Devuelve el punto de la esquina superior izquierda
     *
     * @return Point punto de la esquina superior izquierda
     */
    public Point getEsqSupIzq() {
        return this.esqSupIzq;
    }

    /**
     * Devuelve el punto de la esquina superior derecha
     *
     * @return Point punto de la esquina superior derecha
     */
    public Point getEsqSupDer() {
        return this.esqSupDer;
    }

    /**
     * Devuelve el punto de la esquina inferior izquierda
     *
     * @return Point punto de la esquina inferior izquierda
     */
    public Point getEsqInfIzq() {
        return this.esqInfIzq;
    }

    /**
     * Devuelve el punto de la esquina inferior derecha
     *
     * @return Point punto de la esquina inferior derecha
     */
    public Point getEsqInfDer() {
        return this.esqInfDer;
    }
}
