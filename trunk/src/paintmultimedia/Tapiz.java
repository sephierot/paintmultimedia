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

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
public class Tapiz extends JPanel {

    /**
     *
     */
    public BufferedImage imagenCargada = null;

    /**
     *
     * @param anchura
     * @param altura
     */
    public Tapiz(int anchura, int altura) {
        super();

        this.setSize(anchura, altura);
        this.setPreferredSize(new Dimension(anchura, altura));
    }

    /**
     *
     * @param imagen
     */
    public Tapiz(BufferedImage imagen) {
        super();

        if (imagen != null) {
            this.imagenCargada = imagen;
        }
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        int anchura = this.getWidth(), altura = this.getHeight();

        //Si la imagen es mayor incrementamos el tapiz
        if (anchura < this.imagenCargada.getWidth()) {
            anchura = this.imagenCargada.getWidth();
        }
        if (altura < this.imagenCargada.getHeight()) {
            altura = this.imagenCargada.getHeight();
        }

        this.setPreferredSize(new Dimension(anchura, altura));
        this.setSize(anchura, altura);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.imagenCargada != null) {
            Graphics2D g2 = (Graphics2D) g;

            //Establecemos las preferencias de dibujo
            g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            Point inicio = new Point((this.getWidth() - this.imagenCargada.getWidth()) / 2, (this.getHeight() - this.imagenCargada.getHeight()) / 2);

            g2.drawImage(this.imagenCargada, inicio.x, inicio.y, imagenCargada.getWidth(),
                    imagenCargada.getHeight(), this);

            g2.setColor(Color.BLACK);
            g2.drawRect(inicio.x - 1, inicio.y - 1, imagenCargada.getWidth(),
                    imagenCargada.getHeight());
        }
    }

    /**
     *
     * @return
     */
    public BufferedImage getImagen() {
        return this.imagenCargada;
    }

    /**
     *
     * @param imagen
     */
    public void setImagen(BufferedImage imagen) {
        this.imagenCargada = imagen;

        //Liberamos la memoria no utilizada
        System.gc();
        System.runFinalization();

        int anchura = this.getWidth(), altura = this.getHeight();

        //Si la imagen es mayor incrementamos el tapiz
        if (anchura < this.imagenCargada.getWidth()) {
            anchura = this.imagenCargada.getWidth();
        }
        if (altura < this.imagenCargada.getHeight()) {
            altura = this.imagenCargada.getHeight();
        }

        this.setPreferredSize(new Dimension(anchura, altura));
        this.setSize(anchura, altura);

        this.repaint();
    }
}
