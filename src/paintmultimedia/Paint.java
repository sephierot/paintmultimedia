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
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.imageio.ImageIO;
import Dibujables.*;
import Dibujables.GeometriaDibujable.*;

/**
 * <p>Title: Paint</p>
 *
 * <p>Description: Panel que permite el dibujo con una serie de herramientas
 * especificadas en el propio panel. </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author David Armenteros Escabias
 * @version 1.0
 */
public class Paint extends JPanel implements Serializable {
    //Variables miembro
    //Representa el numero de colores ademas de los 13 colores de la clase Color

    private final int NCOLORESNOBASICOS = 20;
    private final int NCOLORESBASICOS = 13;
    private final int NCOLORESADICIONALES = 6;
    //Variables que manejan el dibujo del poligono.
    private boolean poligonoCreado = false;
    private PoligonoDibujable poligono;
    //Indican si se han creado ya una curva de uno o dos puntos de control y su situacion
    private boolean curvacreada = false;
    private int estadoCurva = 0;
    //Variable que manejan el dibujo de la curva con un punto de control
    private Curva1Dibujable curva1;
    //Variable que manejan el dibujo de la curva con dos puntos de control
    private Curva2Dibujable curva2;
    //Panel que se muestra para obtener el degradado
    DialogoDegradado dialogDegradado;
    //Figura seleccionada durante la ejecucion para ser modificada
    GeometriaDibujable figuraSeleccionada = null;
    String dirImagenes = "/imagenes/";
    //Botones que pueden estar pulsados la barra de dibujo
    //Inicializamos las constantes
    final int LAPIZ = 0, GOMA = 1, TEXTO = 2, PINCEL = 3, POLIGONO = 4;
    final int AUTOFORMA = 5, PUNTO = 6, LINEA = 7, RECTANGULO = 8, RECTANGULOR = 9;
    final int OVALO = 10, CURVA1 = 11, CURVA2 = 12, SELECCION = 13;
    //Indica que boton esta pulsado en cada momento
    private int botonFiguraPulsado = this.LAPIZ;
    //Indica el numero de lineas predefinidas con un cierto formato
    private final int NTIPOS_LINEAS = 7;
    //Array con los distintos tipos de linea para el BasicStroke del contextoGrafico
    private float[][] tiposLineas = {null, {10, 10}, {0, 10}, {0, 10, 10}, {30,
            10}, {20, 5, 2, 5}, {15, 6, 1, 6, 1, 6}
    };
    //Array con los iconos de los tipos de linea
    private ImageIcon[] estilosLineas = new ImageIcon[NTIPOS_LINEAS];
    private Integer[] PosEstiloLineas = new Integer[NTIPOS_LINEAS];
    //Variable que almacena el contexto grafico actual
    /**
     *
     */
    public ContextoGrafico contextoGrafico;
    //Lienzo en el que se pintaran las figuras
    Lienzo lienzo = new Lienzo(500, 400);
    //Variables que componen el diseño grafico
    JPanel PanelDibujo = new JPanel();
    JToolBar barraDibujar = new JToolBar();
    JToggleButton bPunto = new JToggleButton();
    JToggleButton bRectangulo = new JToggleButton();
    JToggleButton bLinea = new JToggleButton();
    JToggleButton bTexto = new JToggleButton();
    JToggleButton bPoligono = new JToggleButton();
    JToggleButton bCurva2 = new JToggleButton();
    JToggleButton bLapiz = new JToggleButton();
    JToggleButton bPincel = new JToggleButton();
    JToggleButton bGoma = new JToggleButton();
    JToggleButton bSeleccion = new JToggleButton();
    JToggleButton bRectanguloR = new JToggleButton();
    JToggleButton bOvalo = new JToggleButton();
    JToolBar barraColores = new JToolBar();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JButton bEditaColor = new JButton();
    GridLayout gridLayout2 = new GridLayout();
    JToggleButton bCurva1 = new JToggleButton();
    JToggleButton bAutoformas = new JToggleButton();
    JPanel jPanel4 = new JPanel();
    Border border1 = BorderFactory.createMatteBorder(6, 6, 6, 6, Color.black);
    BorderLayout borderLayout4 = new BorderLayout();
    JPanel PaneldeBarras = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();
    JPanel panelRelleno = new JPanel();
    JLabel bColorLinea = new JLabel();
    JToolBar barraContextoGrafico = new JToolBar();
    JCheckBox bColorFondo = new JCheckBox();
    JPanel PanelTrazo = new JPanel();
    JPanel PanelFuente = new JPanel();
    JComboBox cEstiloFinalTrazo = new JComboBox();
    JComboBox cEstiloTrazo;
    JComboBox cEstiloUnionTrazo = new JComboBox();
    JSpinner sGrosorTrazo = new JSpinner(new SpinnerNumberModel(1.0, 1.0,
            Double.MAX_VALUE, 0.5));
    JSpinner sTamFuente = new JSpinner(new SpinnerNumberModel(12, 1,
            Integer.MAX_VALUE, 1));
    JSpinner sEscalarY = new JSpinner(new SpinnerNumberModel(1.0, -10.0, 10.0,
            0.5));
    JSpinner sEscalarX = new JSpinner(new SpinnerNumberModel(1.0, -10.0, 10.0,
            0.5));
    JSpinner sRotar = new JSpinner(new SpinnerNumberModel(0.0, -360.0, 360.0,
            1.0));
    JLabel lGrosorTrazo = new JLabel();
    JLabel lEstiloFinalTrazo = new JLabel();
    JLabel lEstiloTrazo = new JLabel();
    JLabel lEstiloUnionTrazo = new JLabel();
    JScrollPane scrollPaneLienzo = new JScrollPane();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    FlowLayout flowLayout7 = new FlowLayout();
    JPanel jPanel3 = new JPanel();
    JCheckBox cDegradado = new JCheckBox();
    FlowLayout flowLayout8 = new FlowLayout();
    JButton bDegradado = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    JComboBox cTipoFuente = new JComboBox();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    JLabel jLabel2 = new JLabel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JLabel jLabel3 = new JLabel();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout9 = new FlowLayout();
    JCheckBox cFuenteNegrita = new JCheckBox();
    JCheckBox cFuenteCursiva = new JCheckBox();
    JPanel jPanel7 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JTextField tFuenteResultado = new JTextField();
    JPanel PanelTransformaciones = new JPanel();
    JButton bRotarI = new JButton();
    BorderLayout borderLayout5 = new BorderLayout();
    FlowLayout flowLayout10 = new FlowLayout();
    JPanel jPanel8 = new JPanel();
    GridLayout gridLayout3 = new GridLayout();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel10 = new JPanel();
    JLabel jLabel4 = new JLabel();
    JButton bRotarD = new JButton();
    JPanel jPanel11 = new JPanel();
    JLabel jLabel5 = new JLabel();
    FlowLayout flowLayout11 = new FlowLayout();
    FlowLayout flowLayout12 = new FlowLayout();
    JPanel jPanel12 = new JPanel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    GridLayout gridLayout4 = new GridLayout();
    FlowLayout flowLayout13 = new FlowLayout();
    //ICONOS DE DIBUJO
    ImageIcon iconoLinea = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Linea.png"));
    ImageIcon iconoRectanguloR = new ImageIcon(Paint.class.getResource(dirImagenes + "RectanguloR.png"));
    ImageIcon iconoPoligono = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Poligono.png"));
    ImageIcon iconoAutoforma = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Autoforma.png"));
    ImageIcon iconoTexto = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Texto.png"));
    ImageIcon iconoPincel = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Pincel.png"));
    ImageIcon iconoPunto = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Punto.png"));
    ImageIcon iconoGoma = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Goma.png"));
    ImageIcon iconoLapiz = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Lapiz.png"));
    ImageIcon iconoRectangulo = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Rectangulo.png"));
    ImageIcon iconoOvalo = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Ovalo.png"));
    ImageIcon iconoAgregar = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Agregar.png"));
    ImageIcon iconoCurva1 = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Curva1.png"));
    ImageIcon iconoCurva2 = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Curva2.png"));
    ImageIcon iconoRotarI = new ImageIcon(Paint.class.getResource(
            dirImagenes + "RotarI.png"));
    ImageIcon iconoRotarD = new ImageIcon(Paint.class.getResource(
            dirImagenes + "RotarD.png"));
    ImageIcon iconoSeleccionar = new ImageIcon(Paint.class.getResource(
            dirImagenes + "Seleccionar.png"));
    //CURSORES
    Cursor cursorPincel, cursorLapiz, cursorGoma;
    Image Lapiz = Toolkit.getDefaultToolkit().getImage(PaintMultimedia.class.getResource(
            dirImagenes + "Lapiz.gif"));
    Image Goma = Toolkit.getDefaultToolkit().getImage(PaintMultimedia.class.getResource(
            dirImagenes + "Goma.gif"));
    Image Pincel = Toolkit.getDefaultToolkit().getImage(PaintMultimedia.class.getResource(
            dirImagenes + "Pincel.gif"));
    //Añadimos una paleta de colores en tres filas
    PaletaColores paletaColor = new PaletaColores(NCOLORESNOBASICOS
            + NCOLORESBASICOS, (short) 3,
            new Dimension(15, 15));
    //Añadimos una paleta adicional
    PaletaColores paletaAdicional = new PaletaColores(NCOLORESADICIONALES,
            (short) 3, new Dimension(15, 15));
    //Renderer para mostrar los estilos de linea
    RendererEstiloLinea renderer = new RendererEstiloLinea();

    /**
     *
     */
    public Paint() {
        try {
            Inicio();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void Inicio() throws Exception {
        //Establecemos el lienzo dentro del jscrollpane
        this.scrollPaneLienzo.setViewportView(this.lienzo);

        //Inicializamos los valores del contexto grafico
        this.contextoGrafico = new ContextoGrafico(Color.BLACK, null, null,
                new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 10.0f, null, 0f), null);

        this.setLayout(borderLayout1);

        if (this.getParent() instanceof Frame) {
            this.dialogDegradado = new DialogoDegradado((Frame) this.getParent(),
                    "Elija el estilo de degradado", true, this);
        } else {
            this.dialogDegradado = new DialogoDegradado(null,
                    "Elija el estilo de degradado", false, this);
        }

        //Inicializamos los cursores
        this.cursorLapiz = Toolkit.getDefaultToolkit().createCustomCursor(this.Lapiz, new Point(0, 31), null);
        this.cursorGoma = Toolkit.getDefaultToolkit().createCustomCursor(this.Goma, new Point(11, 31), null);
        this.cursorPincel = Toolkit.getDefaultToolkit().createCustomCursor(this.Pincel, new Point(0, 31), null);

        border1 = BorderFactory.createMatteBorder(6, 6, 6, 6, Color.white);

        //Establecemos el layout de la barra
        barraDibujar.setLayout(gridLayout2);
        barraDibujar.setBorder(BorderFactory.createEtchedBorder());
        barraDibujar.setToolTipText(
                "Barra de herramientas de dibujo, arrástrela si lo desea");
        gridLayout2.setColumns(7);
        gridLayout2.setHgap(2);
        gridLayout2.setRows(2);
        gridLayout2.setVgap(2);

        //Creamos los iconos de los estilos de linea
        for (int i = 0; i < this.NTIPOS_LINEAS; i++) {
            this.estilosLineas[i] = new ImageIcon(Paint.class.getResource(
                    dirImagenes + "EstiloLinea" + i + ".png"));
            this.PosEstiloLineas[i] = new Integer(i);
        }

        this.cEstiloTrazo = new JComboBox(this.PosEstiloLineas);

        bPunto.setBorder(null);
        bPunto.setPreferredSize(new Dimension(45, 45));
        bPunto.setToolTipText("Dibuja un Punto");
        bPunto.setIcon(iconoPunto);
        bPunto.addActionListener(new BotonesDibujo_actionAdapter(this));
        bRectangulo.setBorder(null);
        bRectangulo.setPreferredSize(new Dimension(45, 45));
        bRectangulo.setToolTipText("Dibuja un Rectángulo");
        bRectangulo.setIcon(iconoRectangulo);
        bRectangulo.setSelectedIcon(null);
        bRectangulo.addActionListener(new BotonesDibujo_actionAdapter(this));

        bLinea.setBorder(null);
        bLinea.setPreferredSize(new Dimension(45, 45));
        bLinea.setToolTipText("Dibuja una Línea");
        bLinea.setIcon(iconoLinea);
        bLinea.addActionListener(new BotonesDibujo_actionAdapter(this));

        bTexto.setBorder(null);
        bTexto.setPreferredSize(new Dimension(45, 45));
        bTexto.setToolTipText("Inserta Texto");
        bTexto.setIcon(iconoTexto);
        bTexto.addActionListener(new BotonesDibujo_actionAdapter(this));

        bGoma.setBorder(null);
        bGoma.setPreferredSize(new Dimension(45, 45));
        bGoma.setToolTipText("Borrado libre");
        bGoma.setIcon(iconoGoma);
        bGoma.addActionListener(new BotonesDibujo_actionAdapter(this));
        bSeleccion.setBorder(null);
        bSeleccion.setPreferredSize(new Dimension(45, 45));
        bSeleccion.setToolTipText(
                "Haz clic con el boton izq. del ratón y después modifica libremente "
                + "la figura seleccionada");
        bSeleccion.setIcon(iconoSeleccionar);
        bSeleccion.addActionListener(new BotonesDibujo_actionAdapter(this));
        bRectanguloR.setBorder(null);
        bRectanguloR.setPreferredSize(new Dimension(45, 45));
        bRectanguloR.setToolTipText("Dibuja un Rectángulo redondeado");
        bRectanguloR.setIcon(iconoRectanguloR);
        bRectanguloR.addActionListener(new BotonesDibujo_actionAdapter(this));

        bOvalo.setBorder(null);
        bOvalo.setPreferredSize(new Dimension(45, 45));
        bOvalo.setToolTipText("Dibuja un Óvalo");
        bOvalo.setIcon(iconoOvalo);
        bOvalo.addActionListener(new BotonesDibujo_actionAdapter(this));
        bPincel.setBorder(null);
        bPincel.setPreferredSize(new Dimension(45, 45));
        bPincel.setToolTipText("Dibujo Libre");
        bPincel.setIcon(iconoPincel);
        bPincel.addActionListener(new BotonesDibujo_actionAdapter(this));
        bCurva1.setBorder(null);
        bCurva1.setPreferredSize(new Dimension(45, 45));
        bCurva1.setToolTipText("Dibuja una Curva con un punto de control");
        bCurva1.setIcon(iconoCurva1);
        bCurva1.addActionListener(new Paint_bCurva1_actionAdapter(this));
        bAutoformas.setPreferredSize(new Dimension(45, 45));
        bAutoformas.setToolTipText("Dibuja una autoforma");
        bAutoformas.setIcon(iconoAutoforma);
        bAutoformas.addActionListener(new BotonesDibujo_actionAdapter(this));

        bPoligono.setBorder(null);
        bPoligono.setPreferredSize(new Dimension(45, 45));
        bPoligono.setToolTipText("Dibuja un poligono");
        bPoligono.setIcon(iconoPoligono);
        bPoligono.addActionListener(new BotonesDibujo_actionAdapter(this));

        bLapiz.setPreferredSize(new Dimension(50, 50));
        bLapiz.setToolTipText("Dibujo libre con lapiz");
        bLapiz.setInputVerifier(null);
        bLapiz.setIcon(iconoLapiz);
        bLapiz.addActionListener(new BotonesDibujo_actionAdapter(this));
        bCurva2.setBorder(null);
        bCurva2.setPreferredSize(new Dimension(45, 45));
        bCurva2.setToolTipText("Dibuja una curva con dos puntos de control");
        bCurva2.setIcon(iconoCurva2);
        bCurva2.addActionListener(new Paint_bCurva2_actionAdapter(this));

        barraColores.setBorder(BorderFactory.createEtchedBorder());
        barraColores.setToolTipText("Barra de colores, arrástrela si lo desea");
        barraColores.setLayout(flowLayout3);

        bEditaColor.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bEditaColor.setBorder(BorderFactory.createRaisedBevelBorder());
        bEditaColor.setMaximumSize(new Dimension(50, 25));
        bEditaColor.setMinimumSize(new Dimension(50, 25));
        bEditaColor.setPreferredSize(new Dimension(24, 24));
        bEditaColor.setHorizontalTextPosition(SwingConstants.CENTER);
        bEditaColor.setIcon(iconoAgregar);
        bEditaColor.setText("+");
        bEditaColor.addActionListener(new bEditaColor_actionAdapter(this));

        PaneldeBarras.setLayout(borderLayout3);
        flowLayout3.setHgap(7);
        borderLayout3.setHgap(5);
        borderLayout3.setVgap(2);
        PaneldeBarras.setMaximumSize(new Dimension(700, 85));
        PaneldeBarras.setMinimumSize(new Dimension(700, 85));
        PaneldeBarras.setPreferredSize(new Dimension(700, 85));

        paletaAdicional.setMaximumSize(new Dimension(34, 53));
        paletaAdicional.setMinimumSize(new Dimension(34, 53));
        paletaColor.setMaximumSize(new Dimension(205, 53));
        paletaColor.setMinimumSize(new Dimension(205, 53));
        paletaColor.setToolTipText(
                "Elija el color de la linea con el botón izq. del ratón y el relleno "
                + "con el botón der.");
        paletaColor.addMouseListener(new paletasColor_mouseAdapter(this));
        paletaAdicional.addMouseListener(new paletasColor_mouseAdapter(this));

        panelRelleno.setLayout(borderLayout2);
        panelRelleno.setPreferredSize(new Dimension(100, 70));
        borderLayout2.setVgap(5);

        bColorLinea.setBackground(Color.black);
        bColorLinea.setOpaque(true);
        bColorLinea.setPreferredSize(new Dimension(50, 6));
        bColorLinea.setToolTipText("Indica el color de la linea");
        barraContextoGrafico.setOrientation(JToolBar.VERTICAL);
        barraContextoGrafico.setLayout(borderLayout5);
        bColorFondo.setBackground(Color.white);
        bColorFondo.setBorder(border1);
        bColorFondo.setPreferredSize(new Dimension(25, 25));
        bColorFondo.setToolTipText(
                "Seleccione el color de relleno de la figura");
        bColorFondo.setFocusPainted(false);
        bColorFondo.addActionListener(new bColorFondo_actionAdapter(this));

        //Establecemos el renderer
        renderer.setPreferredSize(new Dimension(82, 27));
        this.cEstiloTrazo.setRenderer(renderer);
        cEstiloTrazo.setMaximumRowCount(this.NTIPOS_LINEAS);
        cEstiloTrazo.addActionListener(new cEstiloTrazo_actionAdapter(this));
        cEstiloTrazo.setBorder(BorderFactory.createLoweredBevelBorder());
        cEstiloTrazo.setPreferredSize(new Dimension(100, 25));
        cEstiloTrazo.setToolTipText("Especifique el estilo de linea");
        PanelTrazo.setMaximumSize(new Dimension(120, 190));
        PanelTrazo.setMinimumSize(new Dimension(120, 190));
        PanelTrazo.setPreferredSize(new Dimension(120, 170));
        PanelTrazo.setLayout(flowLayout1);
        PanelFuente.setMaximumSize(new Dimension(120, 200));
        PanelFuente.setMinimumSize(new Dimension(120, 200));

        PanelFuente.setPreferredSize(new Dimension(120, 200));
        PanelFuente.setLayout(flowLayout4);
        cEstiloUnionTrazo.setBorder(BorderFactory.createEtchedBorder());
        cEstiloUnionTrazo.setPreferredSize(new Dimension(100, 20));
        cEstiloUnionTrazo.setToolTipText("Especifique el estilo de unión del trazo");
        cEstiloUnionTrazo.addActionListener(new cEstiloUnionTrazo_actionAdapter(this));

        cEstiloFinalTrazo.setBorder(BorderFactory.createEtchedBorder());
        cEstiloFinalTrazo.setPreferredSize(new Dimension(100, 20));
        cEstiloFinalTrazo.setToolTipText(
                "Especifique el estilo final del trazo");
        cEstiloFinalTrazo.addActionListener(new cEstiloFinalTrazo_actionAdapter(this));

        this.cEstiloUnionTrazo.addItem("Borde matado");
        this.cEstiloUnionTrazo.addItem("Normal");
        this.cEstiloUnionTrazo.addItem("Redondeado");
        this.cEstiloFinalTrazo.addItem("Extendido");
        this.cEstiloFinalTrazo.addItem("Redondeado");
        this.cEstiloFinalTrazo.addItem("Rectangular");

        sGrosorTrazo.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
        sGrosorTrazo.setBorder(BorderFactory.createEtchedBorder());
        sGrosorTrazo.setPreferredSize(new Dimension(55, 20));
        sGrosorTrazo.setToolTipText("Indica el grosor de la linea");
        sGrosorTrazo.addChangeListener(new sGrosorTrazo_changeAdapter(this));

        lGrosorTrazo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lGrosorTrazo.setHorizontalAlignment(SwingConstants.LEFT);
        lGrosorTrazo.setHorizontalTextPosition(SwingConstants.LEFT);
        lGrosorTrazo.setText("Grosor:");

        lEstiloFinalTrazo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lEstiloFinalTrazo.setLabelFor(cEstiloFinalTrazo);
        lEstiloFinalTrazo.setText("Estilo final:");

        lEstiloTrazo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lEstiloTrazo.setLabelFor(cEstiloTrazo);
        lEstiloTrazo.setText("Estilo del trazo:");

        lEstiloUnionTrazo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lEstiloUnionTrazo.setLabelFor(cEstiloUnionTrazo);
        lEstiloUnionTrazo.setText("Estilo unión:");
        scrollPaneLienzo.setAutoscrolls(true);
        scrollPaneLienzo.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPaneLienzo.setToolTipText(
                "Dibuje la figura seleccionada pulsando el boton izq. del ratón y mueva "
                + "la figura dibujada con el botón der.");
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));
        jLabel1.setPreferredSize(new Dimension(40, 13));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setText("Relleno");
        jPanel1.setLayout(flowLayout7);
        flowLayout7.setHgap(0);
        flowLayout7.setVgap(1);

        jPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel1.setPreferredSize(new Dimension(80, 30));
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setHgap(0);
        flowLayout2.setVgap(0);

        jPanel4.setLayout(flowLayout6);
        jPanel4.setPreferredSize(new Dimension(75, 25));
        flowLayout6.setVgap(1);

        jPanel3.setPreferredSize(new Dimension(75, 25));
        jPanel3.setLayout(flowLayout8);
        flowLayout8.setVgap(1);

        cDegradado.setPreferredSize(new Dimension(25, 25));
        cDegradado.setHorizontalAlignment(SwingConstants.CENTER);
        cDegradado.setHorizontalTextPosition(SwingConstants.CENTER);
        cDegradado.addActionListener(new jCheckBox1_actionAdapter(this));

        bDegradado.setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));
        bDegradado.setBorder(null);
        bDegradado.setPreferredSize(new Dimension(40, 20));
        bDegradado.setText("Efecto");
        bDegradado.addActionListener(new bDegradado_actionAdapter(this));
        lienzo.setBorder(BorderFactory.createLineBorder(Color.black));
        lienzo.addHierarchyBoundsListener(new Paint_lienzo_hierarchyBoundsAdapter(this));
        lienzo.addMouseListener(new Paint_lienzo_mouseAdapter(this));
        lienzo.addMouseMotionListener(new Paint_lienzo_mouseMotionAdapter(this));
        sTamFuente.setPreferredSize(new Dimension(55, 20));
        sTamFuente.setToolTipText("Indique el tamaño de la fuente");
        sTamFuente.addChangeListener(new Paint_sTamFuente_changeAdapter(this));
        flowLayout1.setHgap(1);
        flowLayout1.setVgap(0);
        cTipoFuente.setBorder(BorderFactory.createEtchedBorder());
        cTipoFuente.setPreferredSize(new Dimension(100, 22));
        cTipoFuente.setToolTipText("Elija el tipo de fuente");
        cTipoFuente.addActionListener(new Paint_Fuente_actionAdapter(this));
        jLabel2.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel2.setText("Fuente:");
        jPanel5.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel5.setPreferredSize(new Dimension(110, 170));
        jPanel5.setLayout(flowLayout9);
        flowLayout4.setHgap(1);
        flowLayout4.setVgap(0);
        jPanel6.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel6.setPreferredSize(new Dimension(110, 170));
        jPanel6.setLayout(flowLayout5);
        jLabel3.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15));
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel3.setText("Trazo:");
        flowLayout5.setHgap(0);
        flowLayout5.setVgap(4);
        cFuenteNegrita.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        cFuenteNegrita.setToolTipText(
                "Active esta casilla si desea la letra negrita");
        cFuenteNegrita.setText("Negrita");
        cFuenteNegrita.addActionListener(new Paint_cFuenteNegrita_actionAdapter(this));
        cFuenteCursiva.setFont(new java.awt.Font("Tahoma", Font.ITALIC, 11));
        cFuenteCursiva.setToolTipText(
                "Active esta casilla si desea la letra cursiva");
        cFuenteCursiva.setText("Cursiva");
        cFuenteCursiva.addActionListener(new Paint_cFuenteCursiva_actionAdapter(this));
        jPanel7.setLayout(gridLayout1);
        gridLayout1.setHgap(3);
        gridLayout1.setRows(2);
        gridLayout1.setVgap(3);
        jPanel7.setBorder(BorderFactory.createEtchedBorder());
        flowLayout9.setHgap(0);
        flowLayout9.setVgap(10);
        tFuenteResultado.setPreferredSize(new Dimension(100, 25));
        tFuenteResultado.setToolTipText("Previsualización de la fuente");
        tFuenteResultado.setEditable(false);
        tFuenteResultado.setText("AaBbCcDd12");
        tFuenteResultado.setHorizontalAlignment(SwingConstants.CENTER);
        tFuenteResultado.setBackground(Color.WHITE);
        PanelDibujo.setPreferredSize(new Dimension(700, 700));
        PanelTransformaciones.setBorder(null);
        PanelTransformaciones.setMaximumSize(new Dimension(110, 200));
        PanelTransformaciones.setMinimumSize(new Dimension(110, 200));
        PanelTransformaciones.setPreferredSize(new Dimension(110, 170));
        PanelTransformaciones.setLayout(flowLayout10);
        jPanel8.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel8.setMaximumSize(new Dimension(110, 150));
        jPanel8.setMinimumSize(new Dimension(110, 150));
        jPanel8.setPreferredSize(new Dimension(110, 150));
        jPanel8.setLayout(gridLayout3);
        gridLayout3.setColumns(1);
        gridLayout3.setRows(2);
        jLabel4.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel4.setText("Escalar(\u0394 factor)");
        sEscalarX.setPreferredSize(new Dimension(47, 18));
        sEscalarX.setToolTipText("Reescala el eje X de la figura");
        sEscalarX.addChangeListener(new Paint_sEscalar_changeAdapter(this));
        sEscalarY.setPreferredSize(new Dimension(47, 18));
        sEscalarY.setToolTipText("Reescala el eje Y de la figura");
        sEscalarY.addChangeListener(new Paint_sEscalarY_changeAdapter(this));
        bRotarI.setPreferredSize(new Dimension(40, 40));
        bRotarI.setToolTipText("Rota 45º a la izquierda");
        bRotarI.setIcon(iconoRotarI);
        bRotarI.addActionListener(new Paint_bRotarI_actionAdapter(this));
        bRotarD.setPreferredSize(new Dimension(40, 40));
        bRotarD.setToolTipText("Rota 45º a la derecha");
        bRotarD.setIcon(iconoRotarD);
        bRotarD.addActionListener(new Paint_bRotarD_actionAdapter(this));
        jPanel9.setBorder(null);
        jPanel9.setPreferredSize(new Dimension(90, 55));
        jPanel9.setLayout(flowLayout11);
        jPanel11.setMaximumSize(new Dimension(100, 40));
        jPanel11.setMinimumSize(new Dimension(100, 40));
        jPanel11.setPreferredSize(new Dimension(100, 40));
        jPanel11.setLayout(flowLayout12);
        jLabel5.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel5.setText("Rotar(\u0394 grados)");
        sRotar.setPreferredSize(new Dimension(50, 20));
        sRotar.setToolTipText("Permite rotar incrementando un nº de grados");
        sRotar.addChangeListener(new Paint_sRotar_changeAdapter(this));
        flowLayout12.setVgap(0);
        jPanel10.setBorder(null);
        jPanel10.setPreferredSize(new Dimension(90, 50));
        jPanel10.setLayout(flowLayout13);
        jLabel6.setPreferredSize(new Dimension(20, 14));
        jLabel6.setText("EJE X:");
        jLabel7.setPreferredSize(new Dimension(20, 14));
        jLabel7.setText("EJE Y:");
        jPanel12.setLayout(gridLayout4);
        gridLayout4.setColumns(2);
        gridLayout4.setRows(2);
        gridLayout4.setVgap(3);
        flowLayout11.setHgap(3);
        flowLayout11.setVgap(7);
        flowLayout13.setHgap(0);
        flowLayout13.setVgap(6);
        this.addKeyListener(new Paint_this_keyAdapter(this));
        barraDibujar.add(bLapiz, this.LAPIZ);
        barraDibujar.add(bGoma, this.GOMA);
        barraDibujar.add(bTexto, this.TEXTO);
        barraDibujar.add(bPincel, this.PINCEL);
        barraDibujar.add(bPoligono, this.POLIGONO);
        barraDibujar.add(bAutoformas, this.AUTOFORMA);
        barraDibujar.add(bPunto, this.PUNTO);
        barraDibujar.add(bLinea, this.LINEA);
        barraDibujar.add(bRectangulo, this.RECTANGULO);
        barraDibujar.add(bRectanguloR, this.RECTANGULOR);
        barraDibujar.add(bOvalo, this.OVALO);
        barraDibujar.add(bCurva1, this.CURVA1);
        barraDibujar.add(bCurva2, this.CURVA2);
        barraDibujar.add(bSeleccion, this.SELECCION);
        barraColores.add(panelRelleno, 0);
        barraColores.addSeparator(new Dimension(2, 55));
        barraColores.add(paletaColor, 2);
        barraColores.addSeparator(new Dimension(2, 55));
        barraColores.add(paletaAdicional, 4);
        barraColores.add(bEditaColor, 5);
        buttonGroup1.add(bOvalo);
        buttonGroup1.add(bGoma);
        buttonGroup1.add(bLinea);
        buttonGroup1.add(bPunto);
        buttonGroup1.add(bTexto);
        buttonGroup1.add(bRectanguloR);
        buttonGroup1.add(bSeleccion);
        buttonGroup1.add(bPincel);
        buttonGroup1.add(bRectangulo);
        buttonGroup1.add(bCurva1);
        buttonGroup1.add(bAutoformas);
        buttonGroup1.add(bPoligono);
        buttonGroup1.add(bCurva2);
        buttonGroup1.add(bLapiz);
        PanelTrazo.add(jLabel3);
        PanelTrazo.add(jPanel6);
        jPanel6.add(lGrosorTrazo, 0);
        jPanel6.add(sGrosorTrazo, 1);
        jPanel6.add(lEstiloTrazo, 2);
        jPanel6.add(cEstiloTrazo, 3);
        jPanel6.add(lEstiloUnionTrazo, 4);
        jPanel6.add(cEstiloUnionTrazo, 5);
        jPanel6.add(lEstiloFinalTrazo, 6);
        jPanel6.add(cEstiloFinalTrazo, 7);
        PanelTransformaciones.add(jPanel8);
        PanelFuente.add(jLabel2);
        PanelFuente.add(jPanel5);
        jPanel5.add(cTipoFuente, 0);
        jPanel5.add(sTamFuente, 1);
        jPanel5.add(jPanel7, 2);
        jPanel5.add(tFuenteResultado, 3);
        jPanel7.add(cFuenteNegrita, 0);
        jPanel7.add(cFuenteCursiva, 1);
        PanelDibujo.setLayout(borderLayout4);
        PanelDibujo.add(PaneldeBarras, java.awt.BorderLayout.SOUTH);
        PanelDibujo.add(barraContextoGrafico, java.awt.BorderLayout.EAST);
        jPanel4.add(jLabel1);
        jPanel4.add(bColorFondo);
        jPanel1.add(jPanel3);
        jPanel1.add(jPanel4);
        jPanel3.add(bDegradado);
        jPanel3.add(cDegradado);
        panelRelleno.add(jPanel2, java.awt.BorderLayout.NORTH);
        jPanel2.add(bColorLinea);
        panelRelleno.add(jPanel1, java.awt.BorderLayout.CENTER);
        PanelDibujo.add(scrollPaneLienzo, java.awt.BorderLayout.CENTER);
        jPanel8.add(jPanel9);
        jPanel9.add(jPanel11);
        jPanel9.add(jLabel5);
        jPanel9.add(sRotar);
        jPanel8.add(jPanel10);
        jPanel10.add(jLabel4);
        jPanel10.add(jPanel12);
        jPanel12.add(jLabel6);
        jPanel12.add(sEscalarX);
        jPanel12.add(jLabel7);
        jPanel12.add(sEscalarY);

        barraContextoGrafico.add(PanelTransformaciones,
                java.awt.BorderLayout.NORTH, 0);
        barraContextoGrafico.add(PanelTrazo, java.awt.BorderLayout.CENTER, 1);
        barraContextoGrafico.add(PanelFuente, java.awt.BorderLayout.SOUTH, 2);

        jPanel11.add(bRotarD, null);
        jPanel11.add(bRotarI, null);
        this.add(PanelDibujo, java.awt.BorderLayout.CENTER);
        PaneldeBarras.add(barraDibujar, java.awt.BorderLayout.WEST);
        PaneldeBarras.add(barraColores, java.awt.BorderLayout.EAST);
        this.bLapiz.setSelected(true);

        Color[] aux = new Color[this.NCOLORESNOBASICOS + this.NCOLORESBASICOS];

        aux[0] = Color.BLACK;
        aux[1] = Color.WHITE;
        aux[2] = Color.DARK_GRAY;
        aux[3] = Color.GRAY;
        aux[4] = Color.LIGHT_GRAY;
        aux[5] = Color.RED;
        aux[6] = Color.GREEN;
        aux[7] = Color.BLUE;
        aux[8] = Color.CYAN;
        aux[9] = Color.MAGENTA;
        aux[10] = Color.YELLOW;
        aux[11] = Color.ORANGE;
        aux[12] = Color.PINK;

        //Creamos los colores adicionales escogiendolos uniformemente y evitando
        //que se repitan tanto el negro como el blanco (al sumarle 1 y no empezar en 0)
        int trozo = 0xFFFFFF / (this.NCOLORESNOBASICOS + 1);
        for (int i = 0, j = trozo; i < this.NCOLORESNOBASICOS; i++, j += trozo) {
            aux[i + this.NCOLORESBASICOS] = new Color(j);
        }

        //Situamos los colores en tres filas
        if (!paletaColor.setColores(aux, (short) 3)) {
            //Intentamos corregir el error
            Color aux2[] = new Color[this.NCOLORESBASICOS];

            //Copiamos solo los esenciales
            for (int i = 0; i < this.NCOLORESBASICOS; i++) {
                aux2[i] = aux[i];
            }

            if (!paletaColor.setColores(aux2, (short) 1)) {
                //No hay solucion posible terminamos el programa mostrando el error
                JOptionPane.showMessageDialog(this,
                        "ERROR AL CREAR LA PALETA DE COLORES, EL PROGRAMA SE CERRARÁ.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
        }

        //Cargamos las fuentes disponibles en el sistema
        CargarFuentes();
    }

    /**
     *
     */
    public void CargarFuentes() {
        String[] fuentes = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().
                getAvailableFontFamilyNames();

        for (int i = 0; i < fuentes.length; i++) {
            this.cTipoFuente.addItem(fuentes[i]);
        }
    }

    /**
     *
     * @return
     */
    public boolean GuardarArchivo() {
        JFileChooser archivo = new JFileChooser();
        archivo.setDialogTitle("Guardar imagen como ...");
        archivo.setDialogType(JFileChooser.SAVE_DIALOG);

        //Quitamos la opcion de que acepte todos los archivos
        archivo.setAcceptAllFileFilterUsed(false);

        FiltroArchivos jpgs, png;
        boolean error = false;

        //Seleccionamos el tipo de archivos que queremos
        jpgs = new FiltroArchivos(new String[]{"jpg", "jpeg", "jpe"},
                "Archivos de tipo Jpeg");
        png = new FiltroArchivos("png",
                "Tipo de imagen png, Portable network graphics");

        archivo.addChoosableFileFilter(jpgs);
        archivo.addChoosableFileFilter(png);

        //Preguntamos al usuario el destino y nombre del archivo
        int guardar = archivo.showSaveDialog(this);

        if (guardar == JFileChooser.APPROVE_OPTION) {
            File destino = archivo.getSelectedFile();

            //Creamos un BufferedImage con el tamaño del lienzo
            BufferedImage imagenLienzo = new BufferedImage(this.lienzo.getWidth(),
                    this.lienzo.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = imagenLienzo.createGraphics();

            //Copiamos el valor en el graphics del lienzo
            this.lienzo.paint(g2);

            //Liberamos memoria
            g2.dispose();

            //Comprobamos el tipo elegido
            if (archivo.getFileFilter().equals(png)) {
                //Guardamos en formato png
                File destinoPNG = new File(destino.getPath() + ".png");
                if (!destinoPNG.exists()) {
                    try {
                        ImageIO.write(imagenLienzo, "png", destinoPNG);
                        return true;
                    } catch (IOException ex1) {
                        System.out.println(ex1.getMessage());
                        error = true;
                    }
                } else {
                    //Preguntamos si sobreescribir el archivo
                    int eleccion = JOptionPane.showConfirmDialog(this,
                            "CUIDADO: el archivo ya existe, ¿desea sobreescribirlo?",
                            "SOBREESCRIBIR", JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (eleccion == JOptionPane.YES_OPTION) {
                        //Sobreescribimos el archivo
                        try {
                            ImageIO.write(imagenLienzo, "png", destinoPNG);
                            return true;
                        } catch (IOException ex1) {
                            System.out.println(ex1.getMessage());
                            error = true;
                        }
                    }
                }
            } else {
                //Guardamos en formato jpg
                File destinoJPEG = new File(destino.getPath()
                        + ".jpg");
                if (!destinoJPEG.exists()) {
                    //Guardamos la nueva imagen
                    try {
                        FileOutputStream out = new FileOutputStream(
                                destinoJPEG);
                        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

                        encoder.encode(imagenLienzo);
                        out.close();
                        return true;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        error = true;
                    }
                } else {
                    //Preguntamos si sobreescribir el archivo
                    int eleccion = JOptionPane.showConfirmDialog(this,
                            "CUIDADO: el archivo ya existe, ¿desea sobreescribirlo?",
                            "SOBREESCRIBIR", JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    //Sobreescribimos el archivo
                    if (eleccion == JOptionPane.YES_OPTION) {
                        try {
                            FileOutputStream out = new FileOutputStream(
                                    destinoJPEG);

                            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

                            encoder.encode(imagenLienzo);
                            out.close();
                            return true;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            error = true;
                        }
                    }
                }
            }
        }

        //Si ha habido un error mostramos el mensaje
        if (error) {
            //No se puede escribir el archivo
            JOptionPane.showMessageDialog(this,
                    "No se puede escribir el archivo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    /**
     *
     * @return
     */
    public boolean NuevoDibujo() {
        boolean borrar = false;

        //Comprobamos si el archivo esta modificado o no para pedir confirmacion
        if (!this.lienzo.isVacio()) {
            //Mostramos el dialogo de confirmacion
            int opcion = JOptionPane.showConfirmDialog(this,
                    "Los cambios NO han sido guardados, ¿Desea guardarlos ahora?",
                    "ATENCIÓN",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            //Guardamos el fichero si ha aceptado
            if (opcion == JOptionPane.NO_OPTION) {
                borrar = true;
            } else if (opcion == JOptionPane.YES_OPTION) {
                //Si ha guardado el archivo borramos el lienzo
                if (this.GuardarArchivo()) {
                    borrar = true;
                } else {
                    borrar = false;
                }
            }
        } else {
            borrar = true;
        }

        if (borrar) {
            //Borramos el lienzo
            this.lienzo.BorraLienzo();

            //Establecemos por defecto otra vez todos las variables que afecten al dibujo
            poligonoCreado = false;
            curvacreada = false;
            estadoCurva = 0;
            this.figuraSeleccionada = null;

            return true;
        }

        return false;
    }

    /**
     *
     * @param e Evento
     */
    public void bEditaColor_actionPerformed(ActionEvent e) {
        Color elegido = JColorChooser.showDialog(this, "Elija un color", null);

        //Comprobamos si ha elegido un color o a cancelado la opcion.
        if (elegido != null) {
            this.AgregaColorAdicional(elegido);
            this.bColorLinea.setBackground(elegido);
            this.contextoGrafico.setColorLinea(elegido);
            if (this.figuraSeleccionada != null) {
                this.figuraSeleccionada.getContextoGrafico().setColorLinea(
                        elegido);
            }
        }
    }

    /**
     *
     * @param color
     */
    public void AgregaColorAdicional(Color color) {
        //Insertamos el color elegido en los recientes
        //Miramos si la paleta contiene casillas o no
        if (paletaAdicional.getNColores() != 0) {
            //Corremos todos los colores una posicion y situamos el nuevo en la primera
            for (int i = paletaAdicional.getNColores(); i > 1; i--) {
                //Guardamos el valor de la posicion anterior en la actual
                paletaAdicional.setColor(i, paletaAdicional.getColor(i - 1));
            }
            //Escribimos el nuevo color en la primera posicion
            paletaAdicional.setColor(1, color);
        }
    }

    /**
     *
     * @param e Evento
     */
    public void lienzo_mouseDragged(MouseEvent e) {
        if (!this.lienzo.isVacio()) {
            //Obtenemos la ultima figura que puede ser geometrica o no.
            Dibujable figura = this.lienzo.getLastFigura();

            //Caso en el que esta pulsando el BOTON IZQUIERDO DEL RATON
            if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
                //Se trata de un elemento dibujable simplemente
                if (figura instanceof DibujoLapizDibujable) { //Caso del lapiz o de la goma
                    //Añadimos el nuevo punto a la lista del lapiz
                    ((DibujoLapizDibujable) figura).addPunto(e.getPoint());
                } else {
                    /*Caso en el que es una geometria Dibujable
                    obtenemos sus limites para cambiar de tamaño la figura
                    y simular el redimensionado */

                    //Para que no pinte si esta en seleccion y no se ha seleccionado ninguna figura
                    if (figuraSeleccionada != null || this.botonFiguraPulsado != this.SELECCION) {
                        //Si hay figura seleccionada es la que tenemos que modificar
                        if (figuraSeleccionada != null) {
                            figura = this.figuraSeleccionada;
                        }

                        Rectangle limites = ((GeometriaDibujable) figura).getLimites(),
                                nuevoslimites;

                        nuevoslimites = new Rectangle(limites.getLocation(),
                                new Dimension(e.getX()
                                - limites.x, e.getY() - limites.y));

                        //Caso especial en el que se deben llamar a otras funciones para el poligono
                        if (figura instanceof PoligonoDibujable) {
                            limites = ((PoligonoDibujable) figura).getLimitesLinea(this.poligono.getNumeroLineas() - 1);

                            nuevoslimites = new Rectangle(limites.getLocation(),
                                    new Dimension(e.getX()
                                    - limites.x,
                                    e.getY() - limites.y));

                            //Establecemos los nuevos limites de la figura geometrica
                            ((PoligonoDibujable) figura).setLimitesLinea(nuevoslimites,
                                    this.poligono.getNumeroLineas()
                                    - 1);
                        } else { //Fin del if en el cual se gestionan los poligonosDibujables
                            //Caso especial en el que se trata de una curva2Dibujable
                            if (figura instanceof Curva2Dibujable) {
                                //Establecemos los nuevos limites de la figura geometrica
                                if (estadoCurva == 0) {
                                    this.curva2.setLimites(nuevoslimites);
                                } else {
                                    if (estadoCurva == 1) {
                                        this.curva2.setPuntoControl(e.getPoint());
                                    } else {
                                        this.curva2.setPuntoControl2(e.getPoint());
                                    }
                                }
                            } //Fin del if en el que se tratan las curvas2Dibujables
                            else {
                                if (figura instanceof Curva1Dibujable) {
                                    if (estadoCurva == 0) {
                                        this.curva1.setLimites(nuevoslimites);
                                    } else {
                                        this.curva1.setPuntoControl(e.getPoint());
                                    }
                                } else {
                                    //Caso basico en el que se trata de una geometriaDibujable
                                    //sin ninguna funcion especial
                                    ((GeometriaDibujable) figura).setLimites(
                                            nuevoslimites);
                                }

                            } //Fin del else en el que NO se tratan las curvas2Dibujables

                        } //Fin del else en el que NO es un poligono Dibujable
                    }
                }
            } else { //Fin del if que gestiona si se pulsa el boton1
                if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) == MouseEvent.BUTTON3_MASK) {
                    //Caso en el que se esta pulsando el BOTON DERECHO DEL RATON
                    //Movemos la figura si no es un objeto goma o lapiz
                    if (figura instanceof GeometriaDibujable) {
                        //Si hay figura seleccionada es la que tenemos que modificar
                        if (figuraSeleccionada != null) {
                            figura = this.figuraSeleccionada;
                        }

                        ((GeometriaDibujable) figura).mover(e.getPoint());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    }
                }
            } //Fin del else que gestiona si NO se pulsa el boton1

            //Como hemos modificado una figura indirectamente en el lienzo lo repintamos
            this.lienzo.repaint();
        } //Fin del if si hay figuras en el lienzo

        paintmultimedia.PaintMultimedia.lPosCursor.setText("(X=" + e.getX()
                + ",Y=" + e.getY() + ")");
    } //Fin de la funcion mouseDragged

    /**
     *
     * @param e Evento
     */
    public void lienzo_mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            //Detectamos que figura esta marcada para dibujar
            switch (this.botonFiguraPulsado) {
                case LAPIZ:
                    DibujoLapizDibujable lapiz = new DibujoLapizDibujable(null,
                            this.contextoGrafico.getColorLinea());
                    this.lienzo.addFigura(lapiz);

                    //Agregamos el primer punto donde se pica por primera vez tambien
                    lapiz.addPunto(e.getPoint());
                    break;

                case GOMA:
                    DibujoGomaDibujable goma = new DibujoGomaDibujable(null,
                            this.contextoGrafico.getEstiloLinea());
                    this.lienzo.addFigura(goma);

                    //Agregamos el primer punto donde se pica por primera vez tambien
                    goma.addPunto(e.getPoint());
                    break;

                case TEXTO:
                    String texto = JOptionPane.showInputDialog(this,
                            "Introduzca el texto que desee introducir:",
                            "Introducir Texto",
                            JOptionPane.QUESTION_MESSAGE);
                    if (texto != null) {
                        TextoDibujable textoD = new TextoDibujable(texto,
                                e.getPoint(),
                                new ContextoGrafico(this.contextoGrafico));
                        this.lienzo.addFigura(textoD);
                    }
                    break;

                case PINCEL:
                    DibujoPincelDibujable pincel = new DibujoPincelDibujable(null,
                            this.contextoGrafico.getColorLinea(),
                            this.contextoGrafico.getEstiloLinea());
                    this.lienzo.addFigura(pincel);

                    //Agregamos el primer punto donde se pica por primera vez tambien
                    pincel.addPunto(e.getPoint());
                    break;

                case POLIGONO:
                    if (!this.poligonoCreado) {
                        this.poligono = new PoligonoDibujable(null, null, 0,
                                new ContextoGrafico(this.contextoGrafico));
                        this.lienzo.addFigura(this.poligono);
                        this.poligonoCreado = true;
                    }
                    this.poligono.addPunto(e.getPoint());
                    break;

                case PUNTO:
                    PuntoDibujable punto = new PuntoDibujable(e.getPoint(),
                            new ContextoGrafico(this.contextoGrafico));
                    this.lienzo.addFigura(punto);
                    break;

                case LINEA:
                    LineaDibujable linea = new LineaDibujable(e.getPoint(),
                            e.getPoint(), new ContextoGrafico(this.contextoGrafico));
                    this.lienzo.addFigura(linea);
                    break;

                case RECTANGULO:
                    RectanguloDibujable r = new RectanguloDibujable(e.getPoint(), 0,
                            0,
                            new ContextoGrafico(this.contextoGrafico));
                    this.lienzo.addFigura(r);
                    break;

                case RECTANGULOR:
                    RectanguloRDibujable rr = new RectanguloRDibujable(e.getPoint(),
                            0,
                            0, new ContextoGrafico(this.contextoGrafico));
                    this.lienzo.addFigura(rr);
                    break;

                case OVALO:
                    OvaloDibujable el = new OvaloDibujable(e.getPoint(), 0, 0,
                            new ContextoGrafico(this.contextoGrafico));
                    this.lienzo.addFigura(el);
                    break;

                case CURVA1:
                    if (!this.curvacreada) {
                        this.curva1 = new Curva1Dibujable(e.getPoint(), e.getPoint(),
                                new ContextoGrafico(this.contextoGrafico));
                        this.lienzo.addFigura(this.curva1);
                        this.curvacreada = true;
                        this.estadoCurva = 0;
                    } else {
                        this.curva1.setPuntoControl(e.getPoint());
                        this.curvacreada = false;
                    }
                    break;

                case CURVA2:
                    if (!this.curvacreada) {
                        this.curva2 = new Curva2Dibujable(e.getPoint(), e.getPoint(),
                                new ContextoGrafico(this.contextoGrafico));

                        this.lienzo.addFigura(this.curva2);
                        this.curvacreada = true;
                        this.estadoCurva = 0;
                    } else {
                        if (this.estadoCurva == 1) {
                            this.curva2.setPuntoControl(e.getPoint());
                        } else {
                            this.curva2.setPuntoControl2(e.getPoint());
                            this.curvacreada = false;
                        }
                    }
                    break;

                case SELECCION:
                    //Rescatamos el foco por si desea eliminar una figura seleccionada
                    this.requestFocus();
                    this.figuraSeleccionada = this.lienzo.getFigura(e.getPoint());

                    //Cambiamos de color medio segundo para que sepa que figura ha seleccionado
                    if (figuraSeleccionada != null) {
                        ContextoGrafico aux = figuraSeleccionada.getContextoGrafico();

                        figuraSeleccionada.setContextoGrafico(new ContextoGrafico(
                                Color.RED, Color.WHITE, aux.getFuente(),
                                new BasicStroke(figuraSeleccionada.getContextoGrafico().
                                getEstiloLinea().getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                10f, this.tiposLineas[1], 0f), null));

                        //Repintamos solo la figura seleccionada por eficiencia
                        lienzo.paintImmediately(figuraSeleccionada.getLimitesAbsolutos());

                        try {
                            Thread.sleep(250);
                        } catch (Exception ex) {
                        }

                        //Reestablecemos su contexto grafico
                        figuraSeleccionada.setContextoGrafico(aux);

                        //Repintamos solo la figura seleccionada por eficiencia
                        lienzo.paintImmediately(figuraSeleccionada.getLimitesAbsolutos());
                    }
                    break;

                default:
                    break;
            }
        } else {
            if ((e.getButton() == MouseEvent.BUTTON3) && (!this.lienzo.isVacio())) {
                Dibujable geo = lienzo.getLastFigura();
                //Movemos la figura si se trata de una figura geometrica
                if (this.figuraSeleccionada != null) {
                    geo = this.figuraSeleccionada;
                }

                if (geo instanceof GeometriaDibujable) {
                    ((GeometriaDibujable) geo).mover(e.getPoint());
                }
                this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                lienzo.repaint();
            }
        }
    }

    /**
     * lienzo_mouseReleased
     *
     * @param e Evento de raton
     */
    public void lienzo_mouseReleased(MouseEvent e) {
        if (this.botonFiguraPulsado == this.CURVA1
                || this.botonFiguraPulsado == this.CURVA2) {
            this.estadoCurva++;
        }

        switch (botonFiguraPulsado) {
            case LAPIZ:
                this.setCursor(cursorLapiz);
                break;

            case GOMA:
                this.setCursor(cursorGoma);
                break;
            case TEXTO:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                break;
            case PINCEL:
                this.setCursor(cursorPincel);
                break;
            case SELECCION:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                break;
            default:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    //Para actualizar el valor de la variable que indica que boton esta pulsado
    /**
     *
     * @param actionEvent
     */
    public void BotonesDibujo_actionPerformed(ActionEvent actionEvent) {
        //Actualizamos la variable que indica que boton esta pulsado
        this.botonFiguraPulsado = this.barraDibujar.getComponentIndex((Component) actionEvent.getSource());

        if (this.botonFiguraPulsado != this.POLIGONO) {
            this.poligonoCreado = false;
        }

        //Borramos la seleccion si se ha pulsado cualquier otra cosa
        if (this.botonFiguraPulsado != this.SELECCION) {
            this.figuraSeleccionada = null;
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bColorFondo_actionPerformed(ActionEvent e) {
        //Indicamos si se rellena o no la figura
        if (this.bColorFondo.isSelected()) {
            this.contextoGrafico.setColorRelleno(this.bColorFondo.getBackground());
            this.cDegradado.setSelected(false);
            this.contextoGrafico.setDegradado(null);

            //Si hubiera figura seleccionada cambiamos sus atributos
            if (this.figuraSeleccionada != null) {
                this.figuraSeleccionada.getContextoGrafico().setColorRelleno(this.bColorFondo.getBackground());
                this.figuraSeleccionada.getContextoGrafico().setDegradado(null);
                //Repintamos la figura seleccionada
                this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
            }
        } else {
            this.contextoGrafico.setColorRelleno(null);
            if (this.figuraSeleccionada != null) {
                this.figuraSeleccionada.getContextoGrafico().setColorRelleno(null);
                //Repintamos la figura seleccionada
                this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
            }
        }
    }

    /**
     *
     * @param e Evento
     */
    public void sGrosorTrazo_stateChanged(ChangeEvent e) {
        Double grosor = (Double) this.sGrosorTrazo.getValue();

        BasicStroke s = new BasicStroke(grosor.floatValue(),
                contextoGrafico.getEstiloLinea().
                getEndCap(),
                contextoGrafico.getEstiloLinea().
                getLineJoin(),
                contextoGrafico.getEstiloLinea().
                getMiterLimit(),
                contextoGrafico.getEstiloLinea().
                getDashArray(),
                contextoGrafico.getEstiloLinea().
                getDashPhase());

        contextoGrafico.setEstiloLinea(s);

        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.getContextoGrafico().setEstiloLinea(s);
            //Repintamos la figura seleccionada
            this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
        }
    }

    /**
     *
     * @param e Evento
     */
    public void cEstiloTrazo_actionPerformed(ActionEvent e) {
        //Cambiar el estilo de linea del contexto
        float[] estilo = this.tiposLineas[this.cEstiloTrazo.getSelectedIndex()];
        BasicStroke el = new BasicStroke(contextoGrafico.getEstiloLinea().
                getLineWidth(),
                contextoGrafico.getEstiloLinea().
                getEndCap(),
                contextoGrafico.getEstiloLinea().
                getLineJoin(),
                contextoGrafico.getEstiloLinea().
                getMiterLimit(),
                estilo,
                contextoGrafico.getEstiloLinea().
                getDashPhase());
        //Actualizamos el estilo de linea
        this.contextoGrafico.setEstiloLinea(el);
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.getContextoGrafico().setEstiloLinea(el);

            //Repintamos la figura seleccionada
            this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
        }
    }

    /**
     *
     * @param e Evento
     */
    public void cEstiloFinalTrazo_actionPerformed(ActionEvent e) {
        //Cambiar el estilo de linea del contexto
        int estiloFinal;

        switch (this.cEstiloFinalTrazo.getSelectedIndex()) {
            case 0: //Caso en el que el final es CAP_BUTT
                estiloFinal = BasicStroke.CAP_BUTT;
                break;
            case 1: //Caso en el que el final es CAP_ROUND
                estiloFinal = BasicStroke.CAP_ROUND;
                break;
            case 2: //Caso en el que el final es CAP_SQUARE
                estiloFinal = BasicStroke.CAP_SQUARE;
                break;

            default:
                estiloFinal = BasicStroke.CAP_BUTT;
                break;
        }

        BasicStroke el = new BasicStroke(contextoGrafico.getEstiloLinea().
                getLineWidth(),
                estiloFinal,
                contextoGrafico.getEstiloLinea().
                getLineJoin(),
                contextoGrafico.getEstiloLinea().
                getMiterLimit(),
                contextoGrafico.getEstiloLinea().
                getDashArray(),
                contextoGrafico.getEstiloLinea().
                getDashPhase());

        //Actualizamos el estilo de linea
        this.contextoGrafico.setEstiloLinea(el);
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.getContextoGrafico().setEstiloLinea(el);

            //Repintamos la figura seleccionada
            this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
        }
    }

    /**
     * <p>Title: PaintMultimedia</p>
     *
     * <p>Description: Renderer que permite el dibujado dentro de un comboBox del estilo
     * de linea</p>
     *
     * <p>Copyright: Copyright (c) 2006</p>
     *
     * <p>Company: </p>
     *
     * @author David Armenteros Escabias
     * @version 1.0
     */
    class RendererEstiloLinea extends JLabel implements ListCellRenderer {

        public RendererEstiloLinea() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
            setBorder(BorderFactory.createEtchedBorder());
        }

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected,
                boolean cellHasFocus) {
            int selectedIndex = 0;
            if (value != null) {
                selectedIndex = ((Integer) value).intValue();

                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
            }
            //Establecemos el icono
            if (selectedIndex < estilosLineas.length) {
                ImageIcon icono = estilosLineas[selectedIndex];
                setIcon(icono);
            }
            return this;
        }
    }

    /**
     *
     * @param e Evento
     */
    public void cEstiloUnionTrazo_actionPerformed(ActionEvent e) {
        //Cambiar el estilo de union de linea del contexto
        int estiloUnion;

        switch (this.cEstiloUnionTrazo.getSelectedIndex()) {
            case 0: //Caso en el que la union es JOIN_BEVEL
                estiloUnion = BasicStroke.JOIN_BEVEL;
                break;
            case 1: //Caso en el que la union es JOIN_MITER
                estiloUnion = BasicStroke.JOIN_MITER;
                break;
            case 2: //Caso en el que la union es JOIN_ROUND
                estiloUnion = BasicStroke.JOIN_ROUND;
                break;

            default:
                estiloUnion = BasicStroke.JOIN_BEVEL;
                break;
        }

        BasicStroke el = new BasicStroke(contextoGrafico.getEstiloLinea().
                getLineWidth(),
                contextoGrafico.getEstiloLinea().
                getEndCap(),
                estiloUnion,
                contextoGrafico.getEstiloLinea().
                getMiterLimit(),
                contextoGrafico.getEstiloLinea().
                getDashArray(),
                contextoGrafico.getEstiloLinea().
                getDashPhase());

        //Actualizamos el estilo de linea
        this.contextoGrafico.setEstiloLinea(el);
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.getContextoGrafico().setEstiloLinea(el);

            //Repintamos la figura seleccionada
            this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
        }
    }

    /**
     *
     * @param e Evento
     */
    public void paletasColor_mousePressed(MouseEvent e) {
        //Obtenemos la paleta de colores de origen
        Component paleta = (Component) e.getSource(), aux;

        //Obtenemos el elemento de origen dentro de la paleta
        aux = paleta.getComponentAt(e.getPoint());

        if (e.getButton() == MouseEvent.BUTTON1) {
            //Establecemos el color de la linea si no es ella misma
            if (aux != null && aux != paleta) {
                //Establecemos el color en la linea que representa el color de las lineas
                this.bColorLinea.setBackground(aux.getBackground());
                //Establecemos tambien el color al contexto grafico y la figura
                this.contextoGrafico.setColorLinea(aux.getBackground());
                if (this.figuraSeleccionada != null) {
                    this.figuraSeleccionada.getContextoGrafico().setColorLinea(
                            aux.getBackground());

                    //Repintamos la figura seleccionada
                    this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
                }
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            //Establecemos el color de relleno
            //Establecemos el color de relleno si no es la ella misma
            if (aux != null && aux != paleta) {
                this.bColorFondo.setBackground(aux.getBackground());
                //Establecemos tambien el color al contexto grafico
                this.contextoGrafico.setColorRelleno(aux.getBackground());
                if (this.figuraSeleccionada != null) {
                    this.figuraSeleccionada.getContextoGrafico().
                            setColorRelleno(aux.getBackground());

                    //Repintamos la figura seleccionada
                    this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
                }
                //Activamos el chexbox si esta desactivado
                if (!this.bColorFondo.isSelected()) {
                    this.bColorFondo.doClick();
                }
            }
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bDegradado_actionPerformed(ActionEvent e) {
        //Lanzamos el dialogo para la seleccion de degradado
        Dimension dlgSize = dialogDegradado.getPreferredSize();
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        this.dialogDegradado.setLocation((frmSize.width - dlgSize.width) / 2
                + loc.x,
                (frmSize.height - dlgSize.height) / 2
                + loc.y);

        this.dialogDegradado.setVisible(true);
    }

    /**
     *
     * @param e Evento
     */
    public void cDegradado_actionPerformed(ActionEvent e) {
        if (this.cDegradado.isSelected()) {
            //Asignamos el degradado que estuviera guardado en el dialogoDegradado
            this.contextoGrafico.setDegradado(DialogoDegradado.degradadoAmpliado);
            this.bColorFondo.setSelected(false);
            this.contextoGrafico.setColorRelleno(null);

            if (this.figuraSeleccionada != null) {
                this.figuraSeleccionada.getContextoGrafico().setColorRelleno(null);
                this.figuraSeleccionada.getContextoGrafico().setDegradado(DialogoDegradado.degradadoAmpliado);

                //Repintamos la figura seleccionada
                this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
            }
        } else {
            this.contextoGrafico.setDegradado(null);
            if (this.figuraSeleccionada != null) {
                this.figuraSeleccionada.getContextoGrafico().setDegradado(null);

                //Repintamos la figura seleccionada
                this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
            }
        }
    }

    /**
     *
     * @param e Evento
     */
    public void Fuente_actionPerformed(ActionEvent e) {
        int estilo = Font.PLAIN;
        if (this.cFuenteNegrita.isSelected()) {
            estilo = estilo | Font.BOLD;
        }
        if (this.cFuenteCursiva.isSelected()) {
            estilo = estilo | Font.ITALIC;
        }

        //Actualizamos el valor del font del contexto grafico
        this.contextoGrafico.setFuente(new Font((String) this.cTipoFuente.getSelectedItem(), estilo,
                ((Integer) this.sTamFuente.getValue()).intValue()));

        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.getContextoGrafico().setFuente(this.contextoGrafico.getFuente());

            //Repintamos la figura seleccionada
            this.lienzo.repaint(this.figuraSeleccionada.getLimitesAbsolutos());
        }
        //Actulizamos la fuente del ejemplo
        this.tFuenteResultado.setFont(this.contextoGrafico.getFuente());
    }

    /**
     *
     * @return
     */
    public Lienzo getLienzo() {
        return lienzo;
    }

    /**
     *
     * @param e
     */
    public void this_keyTyped(KeyEvent e) {
        //Borramos el elemento seleccionado si se pulsa SUPR.
        if (e.getKeyChar() == KeyEvent.VK_DELETE) {
            if (this.figuraSeleccionada != null) {
                this.lienzo.deleteFigura((Dibujable) this.figuraSeleccionada);
            }
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bRotarD_actionPerformed(ActionEvent e) {
        //Si tenemos una figura seleccionada la rotamos, si no rotamos la ultima
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.rotar(45.0);
            this.lienzo.repaint();
        } else if (!this.lienzo.isVacio()) {
            this.lienzo.getLastFigura().rotar(45.0);
            this.lienzo.repaint();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bRotarI_actionPerformed(ActionEvent e) {
        //Si tenemos una figura seleccionada la rotamos, si no rotamos la ultima
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.rotar(-45.0);
            this.lienzo.repaint();
        } else if (!this.lienzo.isVacio()) {
            this.lienzo.getLastFigura().rotar(-45.0);
            this.lienzo.repaint();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void sEscalar_stateChanged(ChangeEvent e) {
        Double relacionx = (Double) this.sEscalarX.getValue();
        Double relaciony = (Double) this.sEscalarY.getValue();

        //Si tenemos una figura seleccionada la escalamos, si no escamos la ultima
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.escalar(relacionx, relaciony);
            this.lienzo.repaint();
        } else if (!this.lienzo.isVacio()) {
            this.lienzo.getLastFigura().escalar(relacionx, relaciony);
            this.lienzo.repaint();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void sRotar_stateChanged(ChangeEvent e) {
        Double grados = (Double) this.sRotar.getValue();

        //Si tenemos una figura seleccionada la rotamos, si no rotamos la ultima
        if (this.figuraSeleccionada != null) {
            this.figuraSeleccionada.rotar(grados);
            this.lienzo.repaint();
        } else if (!this.lienzo.isVacio()) {
            this.lienzo.getLastFigura().rotar(grados);
            this.lienzo.repaint();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void lienzo_mouseEntered(MouseEvent e) {
        switch (botonFiguraPulsado) {
            case LAPIZ:
                this.setCursor(cursorLapiz);
                break;

            case GOMA:
                this.setCursor(cursorGoma);
                break;

            case TEXTO:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                break;

            case PINCEL:
                this.setCursor(cursorPincel);
                break;

            case SELECCION:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                break;

            default:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    /**
     *
     * @param e Evento
     */
    public void lienzo_mouseExited(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     *
     * @param e Evento
     */
    public void lienzo_mouseMoved(MouseEvent e) {
        paintmultimedia.PaintMultimedia.lPosCursor.setText("(X=" + e.getX()
                + ",Y=" + e.getY() + ")");
    }

    /**
     *
     * @param e Evento
     */
    public void lienzo_ancestorResized(HierarchyEvent e) {
        this.lienzo.setPreferredSize(this.lienzo.getSize());
    }

    //PARA MOSTRAR LA PALETA DE COLORES AMPLIADA
    class bEditaColor_actionAdapter implements ActionListener {

        private Paint adaptee;

        bEditaColor_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bEditaColor_actionPerformed(e);
        }
    }

    class jCheckBox1_actionAdapter implements ActionListener {

        private Paint adaptee;

        jCheckBox1_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.cDegradado_actionPerformed(e);
        }
    }

    class bDegradado_actionAdapter implements ActionListener {

        private Paint adaptee;

        bDegradado_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bDegradado_actionPerformed(e);
        }
    }

    class paletasColor_mouseAdapter extends MouseAdapter {

        private Paint adaptee;

        paletasColor_mouseAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            adaptee.paletasColor_mousePressed(e);
        }
    }

    class cEstiloFinalTrazo_actionAdapter implements
            ActionListener {

        private Paint adaptee;

        cEstiloFinalTrazo_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.cEstiloFinalTrazo_actionPerformed(e);
        }
    }

    class cEstiloUnionTrazo_actionAdapter implements
            ActionListener {

        private Paint adaptee;

        cEstiloUnionTrazo_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.cEstiloUnionTrazo_actionPerformed(e);
        }
    }

    class cEstiloTrazo_actionAdapter implements ActionListener {

        private Paint adaptee;

        cEstiloTrazo_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.cEstiloTrazo_actionPerformed(e);
        }
    }

    class sGrosorTrazo_changeAdapter implements ChangeListener {

        private Paint adaptee;

        sGrosorTrazo_changeAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.sGrosorTrazo_stateChanged(e);
        }
    }

    class bColorFondo_actionAdapter implements ActionListener {

        private Paint adaptee;

        bColorFondo_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bColorFondo_actionPerformed(e);
        }
    }

    class BotonesDibujo_actionAdapter implements ActionListener {

        private Paint adaptee;

        BotonesDibujo_actionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent actionEvent) {
            adaptee.BotonesDibujo_actionPerformed(actionEvent);
        }
    }

    class lienzo_mouseMotionAdapter extends MouseMotionAdapter {

        private Paint adaptee;

        lienzo_mouseMotionAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            adaptee.lienzo_mouseDragged(e);
        }
    }

    class lienzo_mouseAdapter extends MouseAdapter {

        private Paint adaptee;

        lienzo_mouseAdapter(Paint adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            adaptee.lienzo_mousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            adaptee.lienzo_mouseReleased(e);
        }
    }
}

class Paint_lienzo_mouseAdapter extends MouseAdapter {

    private Paint adaptee;

    Paint_lienzo_mouseAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        adaptee.lienzo_mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        adaptee.lienzo_mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        adaptee.lienzo_mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        adaptee.lienzo_mouseExited(e);
    }
}

class Paint_lienzo_mouseMotionAdapter extends MouseMotionAdapter {

    private Paint adaptee;

    Paint_lienzo_mouseMotionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        adaptee.lienzo_mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        adaptee.lienzo_mouseMoved(e);
    }
}

class Paint_lienzo_hierarchyBoundsAdapter extends HierarchyBoundsAdapter {

    private Paint adaptee;

    Paint_lienzo_hierarchyBoundsAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void ancestorResized(HierarchyEvent e) {
        adaptee.lienzo_ancestorResized(e);
    }
}

class lienzo_mouseMotionAdapter extends MouseMotionAdapter {

    private Paint adaptee;

    lienzo_mouseMotionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        adaptee.lienzo_mouseDragged(e);
    }
}

class Paint_sEscalarY_changeAdapter implements ChangeListener {

    private Paint adaptee;

    Paint_sEscalarY_changeAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sEscalar_stateChanged(e);
    }
}

class Paint_sRotar_changeAdapter implements ChangeListener {

    private Paint adaptee;

    Paint_sRotar_changeAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sRotar_stateChanged(e);
    }
}

class Paint_sEscalar_changeAdapter implements ChangeListener {

    private Paint adaptee;

    Paint_sEscalar_changeAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sEscalar_stateChanged(e);
    }
}

class Paint_this_keyAdapter extends KeyAdapter {

    private Paint adaptee;

    Paint_this_keyAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        adaptee.this_keyTyped(e);
    }
}

class Paint_bRotarD_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_bRotarD_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bRotarD_actionPerformed(e);
    }
}

class Paint_bRotarI_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_bRotarI_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bRotarI_actionPerformed(e);
    }
}

class Paint_sTamFuente_changeAdapter implements ChangeListener {

    private Paint adaptee;

    Paint_sTamFuente_changeAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.Fuente_actionPerformed(new ActionEvent((Object) adaptee.sTamFuente, ActionEvent.ACTION_PERFORMED, ""));
    }
}

class Paint_cFuenteCursiva_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_cFuenteCursiva_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.Fuente_actionPerformed(e);
    }
}

class Paint_cFuenteNegrita_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_cFuenteNegrita_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.Fuente_actionPerformed(e);
    }
}

class Paint_Fuente_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_Fuente_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.Fuente_actionPerformed(e);
    }
}

class Paint_bCurva1_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_bCurva1_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.BotonesDibujo_actionPerformed(e);
    }
}

class Paint_bCurva2_actionAdapter implements ActionListener {

    private Paint adaptee;

    Paint_bCurva2_actionAdapter(Paint adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.BotonesDibujo_actionPerformed(e);
    }
}
