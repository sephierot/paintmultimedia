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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Dibujables.GeometriaDibujable.ContextoGrafico;
import Dibujables.GeometriaDibujable.RectanguloDibujable;

/**
 * <p>Title: DialogoDegradado</p>
 *
 * <p>Description: Dialogo que permite la eleccion de un degradado
 * para las figuras de la aplicacion </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class DialogoDegradado extends JDialog {

    Paint p;
    //Variable que almacena el objeto GradientPaint que almacena el degradado
    public Color color1 = Color.white, color2 = Color.white;

    static public GradientPaint degradado, degradadoAmpliado;
    private Lienzo lienzo = new Lienzo(150, 150);
    final int cuartoAnchuraLienzo;
    final int cuartoAlturaLienzo;
    JPanel PanelOrientacion = new JPanel();
    JRadioButton opcionIzqDer = new JRadioButton();
    JRadioButton opcionArribaAbajo = new JRadioButton();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JRadioButton opcionDiagAbajo = new JRadioButton();
    JRadioButton opcionDiagArriba = new JRadioButton();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JButton opcionColor2 = new JButton();
    JButton opcionColor1 = new JButton();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel lColor2 = new JLabel();
    JLabel lColor1 = new JLabel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel7 = new JPanel();
    JButton bCancelar = new JButton();
    JButton bAceptar = new JButton();
    BorderLayout borderLayout5 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    GridLayout gridLayout1 = new GridLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JPanel jPanel9 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    JPanel jPanel10 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    JPanel jPanel8 = new JPanel();

    /**
     *
     * @param owner
     * @param title
     * @param modal
     * @param p
     */
    public DialogoDegradado(Frame owner, String title, boolean modal, Paint p) {
        super(owner, title, modal);
        this.p = p;
        this.cuartoAnchuraLienzo = p.lienzo.getWidth() / 4;
        this.cuartoAlturaLienzo = p.lienzo.getHeight() / 4;

        try {
            Inicio();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Inicializacion de componentes.
     *
     * @throws java.lang.Exception
     */
    private void Inicio() throws Exception {
        this.setSize(500, 350);
        opcionIzqDer.setSelected(true);
        opcionIzqDer.setText("De Izquierda a Derecha");
        opcionIzqDer.addActionListener(new Opciones_actionAdapter(this));
        opcionArribaAbajo.setText("De Arriba hacia Abajo");
        opcionArribaAbajo.addActionListener(new Opciones_actionAdapter(this));
        opcionDiagAbajo.setText(
                "Diagonal hacia abajo");
        opcionDiagAbajo.addActionListener(new Opciones_actionAdapter(this));
        opcionDiagArriba.setText("Diagonal hacia Arriba");
        opcionDiagArriba.addActionListener(new Opciones_actionAdapter(this));
        jPanel1.setLayout(flowLayout1);
        jPanel1.setBorder(null);
        jPanel1.setPreferredSize(new Dimension(160, 189));
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel1.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabel1.setText("Opciones de degradado:");
        opcionColor2.setText("2º Color");
        opcionColor2.addActionListener(new DialogoDegradado_opcionColor2_actionAdapter(this));
        opcionColor1.setText("1er Color");
        opcionColor1.addActionListener(new DialogoDegradado_opcionColor1_actionAdapter(this));
        lColor2.setBorder(BorderFactory.createRaisedBevelBorder());
        lColor2.setOpaque(true);
        lColor2.setPreferredSize(new Dimension(50, 50));
        lColor2.setBackground(this.color2);
        lColor1.setBorder(BorderFactory.createRaisedBevelBorder());
        lColor1.setOpaque(true);
        lColor1.setPreferredSize(new Dimension(50, 50));
        lColor1.setBackground(this.color1);
        jPanel3.setLayout(flowLayout4);
        jPanel2.setLayout(flowLayout3);
        jLabel4.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel4.setBorder(BorderFactory.createEtchedBorder());
        jLabel4.setPreferredSize(new Dimension(50, 20));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel4.setText("Resultado del degradado:");
        jPanel6.setLayout(flowLayout2);
        PanelOrientacion.setLayout(borderLayout4);
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new DialogoDegradado_bCancelar_actionAdapter(this));
        bAceptar.setText("Aceptar");
        bAceptar.addActionListener(new DialogoDegradado_bAceptar_actionAdapter(this));
        jPanel5.setLayout(borderLayout5);
        flowLayout1.setHgap(20);
        flowLayout1.setVgap(10);
        jPanel4.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(6);
        jPanel4.setBorder(BorderFactory.createEtchedBorder());
        flowLayout2.setHgap(0);
        flowLayout2.setVgap(10);
        jPanel9.setLayout(borderLayout2);
        jPanel9.setPreferredSize(new Dimension(80, 100));
        borderLayout2.setVgap(5);
        flowLayout3.setHgap(0);
        flowLayout3.setVgap(150);
        jPanel3.setPreferredSize(new Dimension(80, 500));
        jPanel10.setPreferredSize(new Dimension(80, 100));
        jPanel10.setLayout(borderLayout3);
        borderLayout3.setVgap(5);
        flowLayout4.setHgap(0);
        flowLayout4.setVgap(150);
        jPanel2.setPreferredSize(new Dimension(80, 500));
        jPanel7.setBorder(BorderFactory.createEtchedBorder());
        jPanel6.setBorder(null);
        jPanel5.setBorder(null);
        this.setResizable(false);
        PanelOrientacion.setBorder(BorderFactory.createRaisedBevelBorder());
        this.getContentPane().add(PanelOrientacion, java.awt.BorderLayout.CENTER);
        jPanel4.add(opcionIzqDer, 0);
        jPanel4.add(opcionArribaAbajo, 1);
        jPanel4.add(opcionDiagAbajo, 2);
        jPanel4.add(opcionDiagArriba, 3);
        buttonGroup1.add(opcionArribaAbajo);
        buttonGroup1.add(opcionIzqDer);
        buttonGroup1.add(opcionDiagArriba);
        buttonGroup1.add(opcionDiagAbajo);
        jPanel7.add(bAceptar);
        jPanel7.add(bCancelar);
        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);
        jPanel1.add(jLabel1);
        jPanel1.add(jPanel4);
        PanelOrientacion.add(jPanel5, java.awt.BorderLayout.CENTER);
        PanelOrientacion.add(jPanel7, java.awt.BorderLayout.SOUTH);
        PanelOrientacion.add(jPanel1, java.awt.BorderLayout.EAST);
        jPanel5.add(jPanel2, java.awt.BorderLayout.EAST);
        jPanel5.add(jPanel3, java.awt.BorderLayout.WEST);
        jPanel5.add(jLabel4, java.awt.BorderLayout.NORTH);
        jPanel6.add(lienzo);
        jPanel9.add(lColor2, java.awt.BorderLayout.CENTER);
        jPanel9.add(opcionColor2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jPanel9, null);
        jPanel10.add(lColor1, java.awt.BorderLayout.CENTER);
        jPanel10.add(opcionColor1, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jPanel10, null);
        PanelOrientacion.add(jPanel8, java.awt.BorderLayout.WEST);

        DialogoDegradado.degradado = new GradientPaint(lienzo.getLocation(), this.color1, new Point(this.lienzo.getWidth(), 0), this.color2);
        DialogoDegradado.degradadoAmpliado = new GradientPaint(new Point(cuartoAnchuraLienzo, cuartoAlturaLienzo), this.color1, new Point(3 * cuartoAnchuraLienzo, 3 * this.cuartoAlturaLienzo), this.color2);

        //Creamos el rectangulo que se encargara de dibujar el degradado
        lienzo.addFigura(new RectanguloDibujable(lienzo.getLocation(), lienzo.getWidth() - 1, lienzo.getHeight() - 1,
                new ContextoGrafico(Color.BLACK, null, null, null, degradado)));
    }

    /**
     *
     * @param e Evento
     */
    public void opcionColor1_actionPerformed(ActionEvent e) {
        Color elegido = JColorChooser.showDialog(this, "Elija un color", null);

        //Comprobamos si ha elegido un color o a cancelado la opcion.
        if (elegido != null) {
            this.color1 = elegido;
            this.lColor1.setBackground(elegido);
            DialogoDegradado.degradado = new GradientPaint(degradado.getPoint1(), color1,
                    degradado.getPoint2(),
                    degradado.getColor2());

            DialogoDegradado.degradadoAmpliado = new GradientPaint(degradadoAmpliado.getPoint1(), color1,
                    degradadoAmpliado.getPoint2(),
                    degradadoAmpliado.getColor2());

            //Repintamos el resultado
            ((RectanguloDibujable) this.lienzo.getFirstFigura()).getContextoGrafico().setDegradado(DialogoDegradado.degradado);
            lienzo.repaint();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void opcionColor2_actionPerformed(ActionEvent e) {
        Color elegido = JColorChooser.showDialog(this, "Elija un color", null);

        //Comprobamos si ha elegido un color o a cancelado la opcion.
        if (elegido != null) {
            this.color2 = elegido;
            this.lColor2.setBackground(elegido);
            DialogoDegradado.degradado = new GradientPaint(degradado.getPoint1(),
                    degradado.getColor1(),
                    degradado.getPoint2(), color2);

            DialogoDegradado.degradadoAmpliado = new GradientPaint(degradadoAmpliado.getPoint1(),
                    degradadoAmpliado.getColor1(),
                    degradadoAmpliado.getPoint2(), color2);


            //Repintamos el resultado
            ((RectanguloDibujable) this.lienzo.getFirstFigura()).getContextoGrafico().setDegradado(DialogoDegradado.degradado);
            lienzo.repaint();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void Opciones_actionPerformed(ActionEvent e) {
        //Establecemos el degradado para poder mostrarlo
        if (e.getSource().equals(this.opcionIzqDer)) {
            DialogoDegradado.degradado = new GradientPaint(lienzo.getLocation(), color1,
                    new Point(lienzo.getWidth(), 0),
                    color2);

            DialogoDegradado.degradadoAmpliado = new GradientPaint(new Point(cuartoAnchuraLienzo, cuartoAlturaLienzo), color1,
                    new Point(3 * cuartoAnchuraLienzo, cuartoAlturaLienzo),
                    color2);
        } else {
            if (e.getSource().equals(this.opcionArribaAbajo)) {
                DialogoDegradado.degradado = new GradientPaint(lienzo.getLocation(),
                        color1, new Point(0, lienzo.getHeight()), color2);

                DialogoDegradado.degradadoAmpliado = new GradientPaint(new Point(p.getWidth() / 4, p.getHeight() / 4),
                        color1, new Point(cuartoAnchuraLienzo, cuartoAlturaLienzo * 3), color2);
            } else {
                if (e.getSource().equals(this.opcionDiagAbajo)) {
                    DialogoDegradado.degradado = new GradientPaint(lienzo.getLocation(), color1, new Point(lienzo.getWidth(),
                            lienzo.getHeight()), color2);
                    DialogoDegradado.degradadoAmpliado = new GradientPaint(new Point(cuartoAnchuraLienzo, cuartoAlturaLienzo), color1, new Point(cuartoAnchuraLienzo * 3, cuartoAlturaLienzo * 3), color2);
                } else {
                    DialogoDegradado.degradado = new GradientPaint(new Point(lienzo.getWidth(), 0),
                            color1, new Point(0, lienzo.getHeight()), color2);
                    DialogoDegradado.degradadoAmpliado = new GradientPaint(new Point(cuartoAnchuraLienzo, cuartoAlturaLienzo * 3),
                            color1, new Point(cuartoAnchuraLienzo * 3, cuartoAlturaLienzo), color2);
                }
            }
        }

        //Repintamos el resultado
        ((RectanguloDibujable) this.lienzo.getFirstFigura()).getContextoGrafico().
                setDegradado(DialogoDegradado.degradado);
        lienzo.repaint();
    }

    /**
     *
     * @param e Evento
     */
    public void bCancelar_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    /**
     *
     * @param e Evento
     */
    public void bAceptar_actionPerformed(ActionEvent e) {
        //Almacenamos la informacion en el contexto grafico del PaintMultimedia
        p.cDegradado.setSelected(true);

        //Almacenamos el degradado ampliado al lienzo del objeto Paint
        p.contextoGrafico.setDegradado(DialogoDegradado.degradadoAmpliado);

        p.contextoGrafico.setColorRelleno(null);

        if (p.figuraSeleccionada != null) {
            p.figuraSeleccionada.getContextoGrafico().setDegradado(DialogoDegradado.degradadoAmpliado);
            p.figuraSeleccionada.getContextoGrafico().setColorRelleno(null);

            //Repintamos la figura seleccionada
            this.lienzo.repaint();
        }

        p.bColorFondo.setSelected(false);

        this.setVisible(false);
    }
}

class DialogoDegradado_bAceptar_actionAdapter implements ActionListener {

    private DialogoDegradado adaptee;

    DialogoDegradado_bAceptar_actionAdapter(DialogoDegradado adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAceptar_actionPerformed(e);
    }
}

class DialogoDegradado_bCancelar_actionAdapter implements ActionListener {

    private DialogoDegradado adaptee;

    DialogoDegradado_bCancelar_actionAdapter(DialogoDegradado adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCancelar_actionPerformed(e);
    }
}

class Opciones_actionAdapter implements ActionListener {

    private DialogoDegradado adaptee;

    Opciones_actionAdapter(DialogoDegradado adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.Opciones_actionPerformed(e);
    }
}

class DialogoDegradado_opcionColor2_actionAdapter implements ActionListener {

    private DialogoDegradado adaptee;

    DialogoDegradado_opcionColor2_actionAdapter(DialogoDegradado adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.opcionColor2_actionPerformed(e);
    }
}

class DialogoDegradado_opcionColor1_actionAdapter implements ActionListener {

    private DialogoDegradado adaptee;

    DialogoDegradado_opcionColor1_actionAdapter(DialogoDegradado adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.opcionColor1_actionPerformed(e);
    }
}

