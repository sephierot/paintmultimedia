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

import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.GradientPaint;

/**
 * <p>Title: ContextoGrafico</p>
 *
 * <p>Description: Clase que representa un contexto grafico con todas
 * las opciones de un grafico a la hora de ser dibujado</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class ContextoGrafico {

    /**
     * Color de la linea de la figura
     */
    private Color colorLinea;
    /**
     * Color de relleno de la figura o null si no tiene relleno
     */
    private Color colorRelleno;
    /**
     * Fuente de la figura en caso de que posea texto o null si no tiene
     */
    private Font fuente;
    /**
     * Estilo de linea de la figura, si no tiene se toma el de por defecto
     */
    private BasicStroke estiloLinea;
    /**
     * Degradado de la figura, null si no posee. El relleno posee preferencia
     * sobre el degradado ya que este solo se dibujara si relleno es null.
     */
    private GradientPaint degradado;

    /**
     * Crea un contexto grafico con el color de la linea negro, sin relleno
     * con la fuente "Default", con el estilo de linea por defecto y sin
     * degradado.
     */
    public ContextoGrafico() {
        this(Color.BLACK, null, Font.getFont("Default"), null, null);
    }

    /**
     * Crea un contexto grafico que es copia del que se pasa como argumento.
     * Si el argumento es null se crea un objeto por defecto o si algun elemento
     * es nulo y no esta permitido se inicializa por defecto ese valor.
     *
     * @param cg contexto grafico que se desea copiar
     */
    public ContextoGrafico(ContextoGrafico cg) {
        if (cg != null) {
            //Copiamos los valores de cg
            this.colorLinea = (cg.colorLinea != null) ? cg.colorLinea : Color.BLACK;
            this.fuente = (cg.fuente != null) ? cg.fuente : Font.getFont("Default");
            this.estiloLinea = (cg.estiloLinea != null) ? cg.estiloLinea : new BasicStroke();
            this.colorRelleno = cg.colorRelleno;
            this.degradado = cg.degradado;
        } else {
            this.colorLinea = Color.BLACK;
            this.fuente = Font.getFont("Default");
            this.estiloLinea = new BasicStroke();
            this.colorRelleno = null;
            this.degradado = null;
        }
    }

    /**
     * Crea un contexto grafico con los valores que se pasan como argumento.
     * Si algun valor posee un valor no permitido se inicializa por defecto.
     * @param cLinea color de la linea
     * @param cRelleno color de relleno o null si no se quiere rellenar
     * @param fuente fuente de la figura
     * @param estiloLinea estilo de la linea de la figura
     * @param degradado degradado de la figura o null si no lo posee
     */
    public ContextoGrafico(Color cLinea, Color cRelleno, Font fuente, BasicStroke estiloLinea, GradientPaint degradado) {
        this.colorLinea = (cLinea != null) ? cLinea : Color.BLACK;
        this.fuente = (fuente != null) ? fuente : Font.getFont("Default");
        this.estiloLinea = (estiloLinea != null) ? estiloLinea : new BasicStroke();
        this.colorRelleno = cRelleno;
        this.degradado = degradado;
    }

    /**
     * Devuelve el color de linea del contexto grafico
     * @return Color indicando el color de la linea.
     */
    public Color getColorLinea() {
        return this.colorLinea;
    }

    /**
     * Devuelve el color de relleno del contexto grafico
     * @return Color indicando el color de relleno o null si no posee relleno.
     */
    public Color getColorRelleno() {
        return this.colorRelleno;
    }

    /**
     * Devuelve la fuente del contexto grafico
     *
     * @return fuente del contexto grafico
     */
    public Font getFuente() {
        return this.fuente;
    }

    /**
     * getEstiloLinea
     *
     * @return BasicStroke
     */
    public BasicStroke getEstiloLinea() {
        return this.estiloLinea;
    }

    /**
     * getDegradado
     *
     * @return GradientPaint
     */
    public GradientPaint getDegradado() {
        return this.degradado;
    }

    /**
     * Establece el color de la linea si color no es null
     *
     * @param color Color que se desea establecer
     */
    public void setColorLinea(Color color) {
        if (color != null) {
            this.colorLinea = color;
        }
    }

    /**
     * Establece el color de relleno o null si no se quiere rellenar
     *
     * @param color Color que se desea aplicar al relleno.
     */
    public void setColorRelleno(Color color) {
        this.colorRelleno = color;
    }

    /**
     * Establece la fuente del contexto grafico si es distinto de null
     *
     * @param font Font indicando la fuente del contexto grafico
     */
    public void setFuente(Font font) {
        if (font != null) {
            this.fuente = font;
        }
    }

    /**
     * Establece el estilo de linea si es distinto de null
     *
     * @param estiloLinea BasicStroke indicando el estilo de linea
     */
    public void setEstiloLinea(BasicStroke estiloLinea) {
        if (estiloLinea != null) {
            this.estiloLinea = estiloLinea;
        }
    }

    /**
     * Establece el degradado o null si no se desea degradado
     *
     * @param degradado GradientPaint con el degradado que se desee.
     */
    public void setDegradado(GradientPaint degradado) {
        this.degradado = degradado;
    }
}
