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

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import Dibujables.Dibujable;

/**
 * <p>Title: GeometriaDibujable</p>
 *
 * <p>Description: Clase abstracta que representa a cualquier figura geometrica
 * capaz de ser dibujada con unos atributos, junto con unas operaciones basicas
 *  sobre ella.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public abstract class GeometriaDibujable implements Dibujable {

    /**
     * Variable que contiene el contexto grafico de la figura
     */
    protected ContextoGrafico cg;
    /**
     * Variable que contiene los limites de la figura
     */
    protected Rectangle limites;
    /**
     * Variable que indica las transformaciones acumuladas en la figura
     */
    protected AffineTransform transformaciones;

    /**
     * Contructor protegido que permite inicializar las variables miembro de la
     * clase por sus hijos.
     */
    protected GeometriaDibujable() {
        cg = new ContextoGrafico();
        limites = new Rectangle();
        transformaciones = new AffineTransform();
    }

    /**
     * Metodo abstracto que permite a sus hijos establecer su limites.
     * Establece los nuevos limites de la figura, redimensionado si es necesario
     * la propia figura, se actualizan las variables del propio objeto que sean
     * afectadas por este cambio
     *
     * @param limites Rectangle que engloba la figura
     */
    public abstract void setLimites(Rectangle limites);

    /**
     * metodo abstracto que permite a sus hijos el poder ser dibujados
     * Dibuja la figura en el grafico pasado como parametro.
     *
     * @param g Graphics donde se desea dibujar la figura.
     */
    public abstract void dibujar(Graphics g);

    /**
     * metodo que mueve la figura modificando sus variables internas.
     * Necesita la implementacion correcta por parte de los hijos del metodo
     * abstracto setLimites.
     *
     * @param p Point al que se desea mover la figura si no es null.
     */
    public void mover(Point p) {
        if (p != null) {
            //Creamos unos nuevos limites con origen en p, que sera donde se mueva
            //la figura geometrica a traves de su funcion setLimites
            Rectangle r = new Rectangle(limites);
            r.setLocation(p);

            this.setLimites(r);
        }
    }

    /**
     * Permite rotar la figura pero aplicando una transformacion sin alterar
     * las variables internas de las clases hijas.
     *
     * @param ngrados double numero de grados que se rotara la figura
     */
    public void rotar(double ngrados) {
        //Convertimos los grados a radianes y rotamos
        transformaciones.rotate(Math.toRadians(ngrados), limites.getCenterX(), limites.getCenterY());
    }

    /**
     * Permite el escalado de la figura pero aplicando una transformacion sin
     * alterar las variables internas de las clases hijas.
     *
     * @param factorX double factor de escala para el eje X.
     * @param factorY double factor de escala para el eje Y.
     */
    public void escalar(double factorX, double factorY) {
        //Escalamos la figura geometrica
        transformaciones.scale(factorX, factorY);
    }

    /**
     * Permite trasladar la figura a partir de su posicion actual pero
     * aplicando una transformacion sin alterar las variables internas de las
     * clases hijas.
     *
     * @param deltaX double incremento en el eje x de la posicion actual.
     * @param deltaY double incremento en el eje y de la posicion actual.
     */
    public void trasladar(double deltaX, double deltaY) {
        //Transladamos la figura geometrica
        transformaciones.translate(deltaX, deltaY);
    }

    /**
     * Obtiene los limites de la figura, el rectangulo puede poseer anchura o
     * altura negativa por lo que se debe de tener cuidado porque Rectangle lo
     * considera vacio.
     *
     * @return Rectangle con los limites de la figura
     */
    public Rectangle getLimites() {
        return this.limites;
    }

    /**
     * Obtiene los limites de la figura, transformando los limites de esta de
     * forma que el rectangle posea anchura y altura positiva.
     * ATENCION: Los limtites devueltos incluyen el grosor de linea, esto quiere
     * decir que no representan los limites naturales de la figura como getLimites.
     * Este metodo es util si se desea repintar la figura ya que se repinta por
     * completo aunque el grosor sea grande.
     *
     * @return Rectangle con los limites de la figura pero en positivo
     */
    public Rectangle getLimitesAbsolutos() {
        //Creamos un Rectangle que corresponde a los limites de la figura
        //pero con la altura y la anchura positiva para futuros usos.
        Rectangle limAbs = new Rectangle((int) (limites.getCenterX()
                - (Math.abs(limites.width) / 2.0)
                - cg.getEstiloLinea().getLineWidth()),
                (int) (limites.getCenterY()
                - (Math.abs(limites.height) / 2.0)
                - cg.getEstiloLinea().getLineWidth()),
                (int) (Math.abs(limites.width)
                + 2 * cg.getEstiloLinea().getLineWidth()),
                (int) (Math.abs(limites.height)
                + 2 * cg.getEstiloLinea().getLineWidth()));
        return limAbs;
    }

    /**
     * Establece el contexto grafico de la figura si no es null
     *
     * @param cg ContextoGrafico a asignar a la figura
     */
    public void setContextoGrafico(ContextoGrafico cg) {
        if (cg != null) {
            this.cg = cg;
        }
    }

    /**
     * Obtiene el contexto grafico de la figura
     *
     * @return ContextoGrafico de la figura
     */
    public ContextoGrafico getContextoGrafico() {
        return this.cg;
    }

    /**
     * Borra cualquier transformacion en la figura devolviendola a su estado
     * indicado por sus variables internas como posicion, tamaño etc.
     */
    public void delTransformaciones() {
        this.transformaciones.setToIdentity();
    }
}
