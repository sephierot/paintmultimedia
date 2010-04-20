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



package Dibujables;

import java.awt.Graphics;

/**
 * <p>Title: PaintMultimedia</p>
 *
 * <p>Description: Interfaz que crea unas pautas para el manejo de cualquier
 * objeto dibujable</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public interface Dibujable {

    /**
     * Permite dibujar el objeto en un grafico dado
     *
     * @param g Graphics
     */
    public abstract void dibujar(Graphics g);

    /**
     * Permite rotar el objeto un determinado numero de grados
     *
     * @param ngrados double
     */
    public abstract void rotar(double ngrados);

    /**
     * Permite escalar el objeto tanto en el eje x como en el y un valor dado
     *
     * @param factorX double
     * @param factorY double
     */
    public abstract void escalar(double factorX, double factorY);

    /**
     * Permite trasladar un objeto a otra posicion con respecto a la actual
     *
     * @param deltaX double
     * @param deltaY double
     */
    public abstract void trasladar(double deltaX, double deltaY);
}
