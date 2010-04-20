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


package paintmultimedia;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.Dimension;
import Dibujables.Dibujable;
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import Dibujables.GeometriaDibujable.GeometriaDibujable;

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
public class Lienzo extends JPanel {

    private Vector figuras;
    private Dimension tamanoLienzo;

    /**
     *
     */
    public Lienzo() {
        super(true);

        this.figuras = new Vector();
        this.tamanoLienzo = new Dimension(200, 200);
        this.setSize(this.tamanoLienzo);
        this.setPreferredSize(this.tamanoLienzo);
    }

    /**
     *
     * @param anchura
     * @param altura
     */
    public Lienzo(int anchura, int altura) {
        this(anchura, altura, null);
    }

    /**
     *
     * @param v
     */
    public Lienzo(Vector v) {
        //Por defecto creamos un lienzo de 200 por 200
        this(200, 200, v);
    }

    /**
     *
     * @param anchura
     * @param altura
     * @param v
     */
    public Lienzo(int anchura, int altura, Vector v) {
        //Llamamos al constructor del JPanel
        super(true);

        //Creamos un vector vacio si los parametros son incorrectos
        figuras = (v != null) ? v : (new Vector());

        if (anchura > 1 && altura > 1) //Parametros incorrectos
        {
            this.tamanoLienzo = new Dimension(anchura, altura);
        } else {
            this.tamanoLienzo = new Dimension(200, 200);
        }

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setPreferredSize(this.tamanoLienzo);
        this.setSize(this.tamanoLienzo);

        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        //Pintamos el panel
        super.paint(g);

        Dibujable aux;

        for (int i = 0; i < this.figuras.size(); i++) {
            //Dibujamos todos los componentes del lienzo
            aux = (Dibujable) this.figuras.elementAt(i);
            aux.dibujar(g);
        }
    }

    /**
     *
     * @param figura
     * @return
     */
    public boolean addFigura(Dibujable figura) {
        if (figura != null) {
            figuras.addElement(figura);

            //Repintamos el lienzo solo el espacio ocupado por la figura
            if (figura instanceof GeometriaDibujable) {
                this.repaint(((GeometriaDibujable) figura).getLimitesAbsolutos());
            } else {
                this.repaint();
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param posicion
     * @return
     */
    public Dibujable getFigura(int posicion) {
        if (posicion >= 0 && posicion < figuras.size()) {
            return (Dibujable) figuras.elementAt(posicion);
        }

        return null;
    }

    /**
     *
     * @param p
     * @return
     */
    public GeometriaDibujable getFigura(Point2D p) {
        if (p != null) {
            //Recorremos todos las figuras geometricas hasta que encontramos la
            //mas reciente añadida a la que pertenezca el punto p
            for (int i = this.figuras.size() - 1; i >= 0; i--) {
                if (this.figuras.elementAt(i) instanceof GeometriaDibujable) {
                    if (((GeometriaDibujable) this.figuras.elementAt(i)).getLimitesAbsolutos().contains(p)) {
                        return ((GeometriaDibujable) this.figuras.elementAt(i));
                    }
                }
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    public Dibujable getLastFigura() {
        if (figuras.isEmpty()) {
            return null;
        }

        return (Dibujable) figuras.lastElement();
    }

    /**
     *
     * @return
     */
    public Dibujable getFirstFigura() {
        if (figuras.isEmpty()) {
            return null;
        }

        return (Dibujable) figuras.firstElement();
    }

    /**
     *
     * @param figura
     * @return
     */
    public boolean deleteFigura(Dibujable figura) {
        if (figura != null) {
            if (figuras.removeElement(figura)) {
                //Repintamos el lienzo solo el espacio ocupado por la figura
                if (figura instanceof GeometriaDibujable) {
                    this.repaint(((GeometriaDibujable) figura).getLimitesAbsolutos());
                } else {
                    this.repaint();
                }

                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param posicion
     * @return
     */
    public boolean deleteFigura(int posicion) {
        if (posicion >= 0 && posicion < figuras.size()) {
            //Repintamos el lienzo solo el espacio ocupado por la figura
            if (figuras.elementAt(posicion) instanceof GeometriaDibujable) {
                Rectangle aux = ((GeometriaDibujable) figuras.elementAt(posicion)).getLimitesAbsolutos();
                figuras.removeElementAt(posicion);
                this.repaint(aux);
            } else {
                figuras.removeElementAt(posicion);
                this.repaint();
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @param v
     * @return
     */
    public boolean setFiguras(Vector v) {
        if (v != null) {
            figuras = v;
            this.repaint();

            return true;
        }
        return false;
    }

    /**
     *
     */
    public void BorraLienzo() {
        //Vaciamos el lienzo
        figuras.removeAllElements();
        this.repaint();
    }

    /**
     *
     * @return
     */
    public boolean isVacio() {
        return this.figuras.isEmpty();
    }

    /**
     *
     * @return
     */
    public int getNFiguras() {
        return this.figuras.size();
    }
}

