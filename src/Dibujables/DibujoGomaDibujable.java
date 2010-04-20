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

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Vector;

/**
 * <p>Title: PaintMultimedia</p>
 *
 * <p>Description: Clase que simula el borrado de una goma dibujando en blanco
 * de forma libre con un cierto grosor y forma de la goma</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class DibujoGomaDibujable extends DibujoPincelDibujable {

    /**
     * Contruye un objeto goma al que se le asigna un grosor por defecto de 1
     */
    public DibujoGomaDibujable() {
        this(null, null);
    }

    /**
     * Contruye un objeto goma al que se le asigna un grosor por defecto de 1
     * y se le establece un trazo de borrado indicado por el parametro si no
     * es null, si no se crea un trazo vacio
     *
     * @param v Vector con el trazo de borrado
     */
    public DibujoGomaDibujable(Vector v) {
        this(v, null);
    }

    /**
     * Contruye un objeto goma al que se le asigna un grosor indicado por el
     * parametro si no es null, si no se establece el de por defecto que es 1.
     * Se le establece un trazo de borrado indicado por el parametro si no
     * es null, si no se crea un trazo vacio
     *
     * @param puntos Vector con el trazo de borrado
     * @param estilo grosor de borrado del trazo
     */
    public DibujoGomaDibujable(Vector puntos,
            BasicStroke estilo) {
        super(puntos, Color.WHITE, (estilo != null) ? new BasicStroke(estilo.getLineWidth(), estilo.getEndCap(),
                estilo.getLineJoin(), estilo.getMiterLimit(), null,
                0) : new BasicStroke(2));
    }

    /**
     * Establece el estilo si el parametro no es null
     *
     * @param estilo BasicStroke
     */
    @Override
    public void setEstilo(BasicStroke estilo) {
        if (estilo != null) {
            this.estilo = new BasicStroke(estilo.getLineWidth(), estilo.getEndCap(),
                    estilo.getLineJoin(), estilo.getMiterLimit(), null,
                    0);
        }
    }

    /**
     * No realiza ninguna accion porque el borrado siempre es blanco
     *
     * @param colorLinea Color
     */
    @Override
    public void setColorLinea(Color colorLinea) {
        //Lo dejamos vacio para que no altere el color de la linea
    }
}
