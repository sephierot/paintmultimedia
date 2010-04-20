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
 * <p>Title: RectanguloRDibujable</p>
 *
 * <p>Description: Clase que permite el dibujo de un rectangulo con las esquinas
 * redondeadas y con unas propiedades como tipo de linea, color, relleno etc.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class RectanguloRDibujable extends RectanguloDibujable {

    /**
     * Variables que indican las dimensiones del arco del rectangulo
     */
    protected int anchuraArco = 20;
    /**
     *
     */
    protected int alturaArco = 20;

    /**
     * Crea un rectanguloR dibujable con dimension 0, todos sus puntos en (0,0)
     * y con anchura y altura del arco igual a 20.
     */
    public RectanguloRDibujable() {
        super();
    }

    /**
     * Crea un rectanguloR con un origen y unas dimensiones. La anchura del arco
     * se inicializa a 20 por defecto. Si origen es null entonces se crea en el
     * punto (0,0),si alguna dimension es negativa se pondra a 0 ese valor.
     *
     * @param origen Point de origen del rectanguloR, esquina superior izquierda.
     * @param anchura int representando la anchura del rectanguloR
     * @param altura int representando la altura del rectanguloR
     */
    public RectanguloRDibujable(Point origen, int anchura, int altura) {
        super(origen, anchura, altura);

        this.anchuraArco = 20;
        this.alturaArco = 20;
    }

    /**
     * Crea un rectanguloR con un origen y unas dimensiones. La dimensiones del
     * arco se inicializaran a los parametros si son >0, si no se inicializaran
     * a 20 por defecto. Si origen es null entonces se crea en el punto (0,0),
     * si alguna dimension es negativa se pondra a 0 ese valor.
     *
     * @param origen Point de origen del rectanguloR, esquina superior izquierda.
     * @param anchura int representando la anchura del rectanguloR
     * @param altura int representando la altura del rectanguloR
     * @param anchuraArco int representando la anchura del arco de la esquina
     * del rectanguloR.
     * @param alturaArco int representando la altura del arco de la esquina
     * del rectanguloR.
     */
    public RectanguloRDibujable(Point origen, int anchura, int altura, int anchuraArco, int alturaArco) {
        super(origen, anchura, altura);

        this.anchuraArco = (anchuraArco > 0) ? anchuraArco : 20;
        this.alturaArco = (alturaArco > 0) ? alturaArco : 20;
    }

    /**
     * Crea un rectanguloR con un origen y unas dimensiones. La anchura del arco
     * se inicializa a 20 por defecto. Si origen es null entonces se crea en el
     * punto (0,0),si alguna dimension es negativa se pondra a 0 ese valor.
     * Se inicializara el contexto grafico al valor del parametro si no es null.
     *
     * @param origen Point de origen del rectanguloR, esquina superior izquierda.
     * @param anchura int representando la anchura del rectanguloR
     * @param altura int representando la altura del rectanguloR
     * @param cg ContextoGrafico indicando el contexto del rectanguloR
     */
    public RectanguloRDibujable(Point origen, int anchura, int altura,
            ContextoGrafico cg) {
        super(origen, anchura, altura, cg);

        this.anchuraArco = 20;
        this.alturaArco = 20;
    }

    /**
     * Crea un rectanguloR con un origen y unas dimensiones. La dimensiones del
     * arco se inicializaran a los parametros si son >0, si no se inicializaran
     * a 20 por defecto. Si origen es null entonces se crea en el punto (0,0),
     * si alguna dimension es negativa se pondra a 0 ese valor. Se inicializara
     *  el contexto grafico al valor del parametro si no es null.
     *
     * @param origen Point de origen del rectangulo, esquina superior izquierda.
     * @param anchura int representando la anchura del rectangulo
     * @param altura int representando la altura del rectangulo
     * @param anchuraArco int representando la anchura del arco de la esquina
     * del rectangulo.
     * @param alturaArco int representando la altura del arco de la esquina
     * del rectangulo.
     * @param cg ContextoGrafico indicando el contexto del rectangulo
     */
    public RectanguloRDibujable(Point origen, int anchura, int altura, int anchuraArco, int alturaArco, ContextoGrafico cg) {
        super(origen, anchura, altura, cg);

        this.anchuraArco = (anchuraArco > 0) ? anchuraArco : 20;
        this.alturaArco = (alturaArco > 0) ? alturaArco : 20;
    }

    /**
     * Metodo que redefine el dibujado de la clase padre. Dibuja el rectangulo
     * en el grafico pasado como parametro.
     *
     * @param g Graphics donde se desea dibujar el rectangulo.
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
        if (this.cg.getDegradado() == null) {
            if (this.cg.getColorRelleno() != null) {
                //Dibujamos el rectangulo relleno
                g2.setColor(this.cg.getColorRelleno());
                g2.fillRoundRect(esqSupIzq.x, esqSupIzq.y, anchura, altura, anchuraArco,
                        alturaArco);
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
            g2.fillRoundRect(esqSupIzq.x, esqSupIzq.y, anchura, altura, anchuraArco,
                    alturaArco);
        }
        g2.setStroke(this.cg.getEstiloLinea());

        //Dibujamos el rectangulo
        g2.setColor(this.cg.getColorLinea());
        g2.drawRoundRect(esqSupIzq.x, esqSupIzq.y, anchura, altura, anchuraArco, alturaArco);

        //Recuperamos la forma original que tenia antes de la transformacion
        g2.setTransform(actual);
    }

    /**
     * Obtiene la anchura del arco del rectanguloR
     *
     * @return int >= 0 con la anchura del arco del rectangulo
     */
    public int getAnchuraArco() {
        return this.anchuraArco;
    }

    /**
     * Obtiene la altura del arco del rectanguloR
     *
     * @return int >= 0 con la altura del arco del rectangulo
     */
    public int getAlturaArco() {
        return this.alturaArco;
    }

    /**
     * Establece la anchura del arco del rectanguloR si es >=0
     * @param anchuraArco int que representa la anchura del arco
     */
    public void setAnchuraArco(int anchuraArco) {
        if (anchuraArco >= 0) {
            this.anchuraArco = anchuraArco;
        }
    }

    /**
     * Establece la altuta del arco del rectanguloR si es >=0
     * @param alturaArco
     */
    public void setAlturaArco(int alturaArco) {
        if (alturaArco >= 0) {
            this.alturaArco = alturaArco;
        }
    }
}
