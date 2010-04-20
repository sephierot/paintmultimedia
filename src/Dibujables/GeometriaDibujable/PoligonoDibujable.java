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
import java.util.Vector;
import java.util.Iterator;
import java.awt.geom.AffineTransform;

/**
 * <p>Title: PoligonoDibujable</p>
 *
 * <p>Description: Clase que permite dibujar poligonos de n lados con un
 * determinado color, relleno, estilo de la linea etc.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class PoligonoDibujable extends GeometriaDibujable {

    /**
     * Variable que contiene el vector de lineas por las que esta formado el
     * poligono
     */
    Vector poligono;

    /**
     * Crea un poligono de 0 lado con los parametros por defecto del padre
     */
    public PoligonoDibujable() {
        //Inicializamos las variables de la clase padre
        super();

        //Creamos un poligono de 0 lados
        poligono = new Vector();
    }

    /**
     * Crea un poligono con n puntos y n-1 lados, con las coordenadas de los
     * puntos que indican los dos arrays como parametros. El numero de elementos
     * de los arrays debe ser igual y igual al parametro npuntos, si no no se
     * establecera el poligono y tendra 0 lados
     *
     * @param x int[] array con las coordenadas x de los puntos del poligono
     * @param y int[] array con las coordenadas y de los puntos del poligono
     * @param npuntos int numero de puntos del poligono
     */
    public PoligonoDibujable(int x[], int y[], int npuntos) {
        this(x, y, npuntos, null);
    }

    /**
     * Crea un poligono con n puntos y n-1 lados, con las coordenadas de los
     * puntos que indican los dos arrays como parametros. El numero de elementos
     * de los arrays debe ser igual y igual al parametro npuntos, si no no se
     * establecera el poligono y tendra 0 lados.
     * El contexto grafico del poligono se aplicara solo si no es null.
     *
     * @param x int[] array con las coordenadas x de los puntos del poligono
     * @param y int[] array con las coordenadas y de los puntos del poligono
     * @param npuntos int numero de puntos del poligono
     * @param cg ContextoGrafico del poligono.
     */
    public PoligonoDibujable(int x[], int y[], int npuntos,
            ContextoGrafico cg) {
        //Inicializamos las variables de la clase padre
        super();

        //Creamos un poligono de 0 lados
        poligono = new Vector();

        //Comprobamos que los parametros son correctos
        if ((x != null) && (y != null) && (x.length == y.length)
                && (x.length == npuntos)) {
            for (int i = 0; i < npuntos - 1; i++) {
                //Creamos lineas desde un punto al siguuiente sin cerrar el poligono
                LineaDibujable l = new LineaDibujable(new Point(x[i], y[i]),
                        new Point(x[i + 1], y[i + 1]), cg);
                poligono.addElement(l);
            }

            //Actualizamos los valores de los limites del poligono
            this.limites = new Polygon(x, y, npuntos).getBounds();
        }

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

        if (!poligono.isEmpty()) {
            //Creamos el poligono a dibujar
            int[] x, y;
            Point paux;

            x = new int[poligono.size() + 1];
            y = new int[poligono.size() + 1];

            for (int i = 0; i < poligono.size(); i++) {
                //Almacenamos el punto de origen de la linea iesima
                paux = ((LineaDibujable) poligono.elementAt(i)).getPuntoInicial();
                x[i] = paux.x;
                y[i] = paux.y;

                //Almacenamos el punto final de la linea iesima
                paux = ((LineaDibujable) poligono.elementAt(i)).getPuntoFinal();
                x[i + 1] = paux.x;
                y[i + 1] = paux.y;
            }

            //Aplicamos los atributos
            if (this.cg.getDegradado() == null) {
                if (this.cg.getColorRelleno() != null) {
                    g2.setColor(this.cg.getColorRelleno());
                    g2.fillPolygon(x, y, x.length);
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
                g2.fillPolygon(x, y, x.length);
            }

            g2.setStroke(this.cg.getEstiloLinea());

            //Dibujamos el poligono
            g2.setColor(this.cg.getColorLinea());
            g2.drawPolygon(x, y, x.length);

            //Recuperamos la forma original que tenia antes de la transformacion
            g2.setTransform(actual);
        }
    }

    /**
     * Sobreescribe el metodo de la clase padre ya que no posee implementada
     * la funcion setLimites con la cual se creaba este metodo en la clase padre
     *
     * @param p Point
     */
    @Override
    public void mover(Point p) {
        if (p != null) {
            Iterator i = this.poligono.iterator();
            Point origen = p;
            LineaDibujable aux;

            while (i.hasNext()) {
                //Movemos al punto deseado la linea
                aux = ((LineaDibujable) i.next());
                aux.mover(p);
                //Actualizamos el punto a mover como el final de la actual linea
                p = aux.getPuntoFinal();
            }
        }
    }

    /**
     * Establece los limites de la linea que esta en la posicion indicada
     * dentro del poligono, uniendola de nuevo a sus otros lados.
     *
     * @param limites Rectangle nuevos limites para la linea con la posicion
     * indicada
     * @param posicion int posicion de la linea dentro del poligono
     */
    public void setLimitesLinea(Rectangle limites, int posicion) {
        if ((limites != null) && (posicion >= 0)
                && (posicion < this.poligono.size())) {
            //Actualizamos los limites de la linea especificada
            ((LineaDibujable) this.poligono.elementAt(posicion)).setLimites(
                    limites);

            //Actualizamos el punto de origen de la siguiente linea si la hay
            if (posicion < (this.poligono.size() - 1)) {
                ((LineaDibujable) poligono.elementAt(posicion + 1)).setPuntoInicial(((LineaDibujable) this.poligono.elementAt(posicion)).getPuntoFinal());
            }
        }
    }

    /**
     * Obtiene los limites de la linea que esta en la posicion indicada
     * dentro del poligono.
     *
     * @param posicion int posicion de la linea dentro del poligono
     * @return
     */
    public Rectangle getLimitesLinea(int posicion) {
        if ((posicion >= 0) && (this.poligono.size() > posicion)) {
            return ((LineaDibujable) this.poligono.elementAt(posicion)).getLimites();
        } else {
            return null;
        }
    }

    /**
     * Añade un punto nuevo al poligono que se une al ultimo punto de este y a
     * su vez al primer punto
     *
     * @param punto Point punto nuevo a añadir
     */
    public void addPunto(Point punto) {
        if (punto != null) {
            //Si el vector esta vacio creamos la primera linea como si fuera un punto
            if (this.poligono.isEmpty()) {
                this.poligono.addElement(new LineaDibujable(punto, punto,
                        this.cg));
            } else { //Si no se añade una nueva linea con origen en el ultimo punto
                this.poligono.add(new LineaDibujable(((LineaDibujable) this.poligono.lastElement()).getPuntoFinal(), punto, this.cg));
            }
        }
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
    }

    /**
     * Obtiene el numero de lineas del poligono
     *
     * @return int >=0 indicando el numero de lineas
     */
    public int getNumeroLineas() {
        return this.poligono.size();
    }
}
