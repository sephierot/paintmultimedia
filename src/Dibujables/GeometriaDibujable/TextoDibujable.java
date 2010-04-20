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

import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

/**
 * <p>Title: TextoDibujable</p>
 *
 * <p>Description: Clase que representa un trozo de texto con unas caracteristicas
 * como fuente, color, relleno etc</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class TextoDibujable extends GeometriaDibujable {

    String texto = new String();
    TextLayout Tl;
    Point origen;

    /**
     *
     */
    public TextoDibujable() {
        super();

        this.Tl = new TextLayout(texto,
                this.cg.getFuente(),
                new FontRenderContext(null, false, false));

        origen = new Point();

        Rectangle2D bounds = this.Tl.getBounds();
        limites = new Rectangle((int) (bounds.getX() + this.origen.getX()),
                (int) (bounds.getY() + this.origen.getY()),
                (int) bounds.getWidth() + 2,
                (int) bounds.getHeight() + 2);
    }

    /**
     *
     * @param texto
     */
    public TextoDibujable(String texto) {
        this(texto, null, null);
    }

    /**
     *
     * @param texto
     * @param origen
     */
    public TextoDibujable(String texto, Point origen) {
        this(texto, origen, null);
    }

    /**
     *
     * @param texto
     * @param origen
     * @param cg
     */
    public TextoDibujable(String texto, Point origen, ContextoGrafico cg) {
        super();

        if (texto != null) {
            this.texto = texto;
        }

        //Aplicamos el contexto grafico
        if (cg != null) {
            this.cg = cg;
        }

        this.Tl = new TextLayout(texto,
                this.cg.getFuente(), new FontRenderContext(null, false, false));

        this.origen = (origen != null) ? origen : new Point();
        //limites = this.Tl.getBounds().getBounds();

        Rectangle2D bounds = this.Tl.getBounds();
        limites = new Rectangle((int) (bounds.getX() + this.origen.getX()),
                (int) (bounds.getY() + this.origen.getY()),
                (int) bounds.getWidth() + 2,
                (int) bounds.getHeight() + 2);
    }

    /**
     * dibujar
     *
     * @param g Graphics
     */
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Dibujamos el texto
        if (texto.length() > 0) {
            //Variable que nos sirve para reestablecer la forma original posteriormente
            AffineTransform actual = g2.getTransform();

            AffineTransform textAt = new AffineTransform();
            textAt.translate(this.origen.x, this.origen.y);

            //Aplicamos la transformacion del objeto si la tiene
            if (!this.transformaciones.isIdentity()) {
                g2.transform(this.transformaciones);
            }

            if (cg.getDegradado() == null) {
                if (this.cg.getColorRelleno() != null) {
                    g2.setColor(this.cg.getColorRelleno());
                    g2.fill(this.Tl.getOutline(textAt));
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

                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

                g2.setPaint(cg.getDegradado());
                g2.fill(this.Tl.getOutline(textAt));
            }

            g2.setStroke(this.cg.getEstiloLinea());

            //Dibujamos el texto
            g2.setColor(this.cg.getColorLinea());
            g2.draw(this.Tl.getOutline(textAt));

            //Recuperamos la forma original que tenia antes de la transformacion
            g2.setTransform(actual);
        }
    }

    /**
     * setLimites
     *
     * @param limites Rectangle
     */
    public void setLimites(Rectangle limites) {
        if (limites != null) {
            this.origen = limites.getLocation();
        }
    }
}
