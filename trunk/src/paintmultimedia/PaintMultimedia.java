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
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Font;

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
public class PaintMultimedia extends JFrame {

    String dirImagenes = "/imagenes/";
    JPanel contentPane;
    Paint PanelDibujo = new Paint();
    EditorImagen PanelImagen = new EditorImagen();
    ReproductorVideoSonido PanelVideoSonido = new ReproductorVideoSonido(true, true, true);
    JMenuBar menu = new JMenuBar();
    JMenu menuArchivo = new JMenu();
    JMenuItem mSalir = new JMenuItem();
    JMenuItem mCerrar = new JMenuItem();
    JMenu menuAyuda = new JMenu();
    JMenuItem mAcercaDe = new JMenuItem();
    JToolBar barraArchivo = new JToolBar();
    JButton bAbrir = new JButton();
    JButton bCerrar = new JButton();
    JButton bAyuda = new JButton();
    JLabel barraEstado = new JLabel();
    JButton bNuevo = new JButton();
    JButton bGuardar = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel PanelOperaciones = new JPanel();
    //ICONO DE LA APLICACION
    ImageIcon iconoAplicacion = new ImageIcon(PaintMultimedia.class.getResource(dirImagenes + "Aplicacion.png"));
    //ICONOS PARA LOS BOTONES
    ImageIcon iconoAbrir = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Abrir.png"));
    ImageIcon iconoNuevo = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Nuevo.png"));
    ImageIcon iconoAyuda = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Ayuda.png"));
    ImageIcon iconoGuardar = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Guardar.png"));
    ImageIcon iconoCerrar = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Cerrar.png"));
    //ICONOS PARA EL TIPO DE APLICACION
    ImageIcon iconoDibujo = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Dibujo.png"));
    ImageIcon iconoImagen = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Imagen.png"));
    ImageIcon iconoVideoSonido = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "VideoSonido.png"));
    //ICONOS PARA LA PARTE DE REPRODUCION
    ImageIcon iconoPlay = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Play.png"));
    ImageIcon iconoPause = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Pause.png"));
    ImageIcon iconoAvanzar = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Avanzar.png"));
    ImageIcon iconoAtrasar = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Atrasar.png"));
    ImageIcon iconoStop = new ImageIcon(PaintMultimedia.class.getResource(
            dirImagenes + "Stop.png"));
    JMenu menuHerramientas = new JMenu();
    JMenuItem mEstadisticas = new JMenuItem();
    JMenu menuModo = new JMenu();
    JMenuItem mVideoSonido = new JMenuItem();
    JMenuItem mDibujo = new JMenuItem();
    JMenuItem mImagen = new JMenuItem();
    JMenuItem mAbrir = new JMenuItem();
    JMenuItem mGuardar = new JMenuItem();
    JMenuItem mNuevo = new JMenuItem();
    ButtonGroup buttonGroup2 = new ButtonGroup();
    CardLayout cardLayout1 = new CardLayout();
    JPanel PanelTipoAplicacion = new JPanel();
    JToggleButton bDibujo = new JToggleButton();
    JToggleButton bImagen = new JToggleButton();
    JToggleButton bVideoSonido = new JToggleButton();
    JLabel jLabel1 = new JLabel();
    FlowLayout flowLayout2 = new FlowLayout();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    static JLabel lPosCursor = new JLabel();
    JPanel jPanel3 = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();

    /**
     *
     */
    public PaintMultimedia() {
        try {
            Inicio();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Inicializacion de componentes.
     *
     * @throws java.lang.Exception
     */
    private void Inicio() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(974, 792));
        setTitle("PaintMultimedia v1.0");
        this.setVisible(true);
        this.addWindowListener(new PaintMultimedia_this_windowAdapter(this));
        iconoAplicacion.setImage(iconoAplicacion.getImage().getScaledInstance(
                16, 16, Image.SCALE_SMOOTH));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Establecemos el icono de la aplicacion
        this.setIconImage(iconoAplicacion.getImage());
        barraEstado.setBorder(BorderFactory.createLoweredBevelBorder());
        barraEstado.setText("  Paint Multimedia, Copyright (c) 2006 GPL");
        mSalir.addActionListener(new PaintMultimedia_mSalir_ActionAdapter(this));
        mAcercaDe.addActionListener(new PaintMultimedia_mAcercaDe_ActionAdapter(this));
        barraArchivo.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(4);
        PanelOperaciones.setLayout(cardLayout1);
        borderLayout1.setHgap(10);
        borderLayout1.setVgap(5);
        contentPane.setPreferredSize(new Dimension(710, 575));

        //MEMUS DE LA APLICACION
        menuArchivo.setText("Archivo");
        menuModo.setText("Modo multimedia");
        menuHerramientas.setText("Herramientas");
        menuAyuda.setText("Ayuda");
        mSalir.setText("Salir");
        mCerrar.setText("Cerrar");
        mAcercaDe.setText("Acerca de PaintMultimedia");
        mDibujo.setText("Dibujo");
        mEstadisticas.setText("Ocultar Estadisticas");
        mEstadisticas.addActionListener(new PaintMultimedia_mEstadisticas_actionAdapter(this));
        mDibujo.addActionListener(new PaintMultimedia_mDibujo_actionAdapter(this));
        mVideoSonido.setText("Video y sonido");
        mVideoSonido.addActionListener(new PaintMultimedia_mVideoSonido_actionAdapter(this));
        mImagen.setText("Imagen");
        mImagen.addActionListener(new PaintMultimedia_mImagen_actionAdapter(this));
        mAbrir.setText("Abrir Fichero");
        mAbrir.addActionListener(new PaintMultimedia_mAbrir_actionAdapter(this));
        mGuardar.setText("Guardar imagen");
        mGuardar.addActionListener(new PaintMultimedia_mGuardar_actionAdapter(this));
        mNuevo.setText("Nueva imagen");
        mNuevo.addActionListener(new PaintMultimedia_mNuevo_actionAdapter(this));
        bAbrir.setPreferredSize(new Dimension(34, 34));

        //ESTABLECEMOS LOS ICONOS
        bAbrir.setIcon(iconoAbrir);
        bAbrir.addActionListener(new PaintMultimedia_bAbrir_actionAdapter(this));
        bAyuda.setPreferredSize(new Dimension(43, 43));
        bAyuda.setIcon(iconoAyuda);
        bAyuda.addActionListener(new PaintMultimedia_bAyuda_actionAdapter(this));
        bCerrar.setPreferredSize(new Dimension(34, 34));
        bCerrar.setIcon(iconoCerrar);
        bCerrar.addActionListener(new PaintMultimedia_bCerrar_actionAdapter(this));
        bGuardar.setIcon(iconoGuardar);
        bGuardar.addActionListener(new PaintMultimedia_bGuardar_actionAdapter(this));
        bNuevo.setIcon(iconoNuevo);
        bGuardar.setPreferredSize(new Dimension(43, 43));
        bGuardar.setToolTipText("Guarda el dibujo");
        bNuevo.setPreferredSize(new Dimension(43, 43));
        bNuevo.setToolTipText("Empieza nuevo dibujo");
        bNuevo.addActionListener(new PaintMultimedia_bNuevo_actionAdapter(this));
        barraArchivo.setBorder(BorderFactory.createEtchedBorder());
        barraArchivo.setFloatable(false);
        PanelTipoAplicacion.setBorder(BorderFactory.createRaisedBevelBorder());
        PanelTipoAplicacion.setPreferredSize(new Dimension(200, 50));
        PanelTipoAplicacion.setLayout(flowLayout2);
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel1.setPreferredSize(new Dimension(120, 14));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel1.setText("Tipo de Aplicación:");
        bVideoSonido.setBorder(null);
        bVideoSonido.setMaximumSize(new Dimension(42, 42));
        bVideoSonido.setMinimumSize(new Dimension(42, 42));
        bVideoSonido.setPreferredSize(new Dimension(43, 43));
        bVideoSonido.setBorderPainted(false);
        bVideoSonido.setFocusPainted(false);
        bVideoSonido.setIcon(iconoVideoSonido);
        bVideoSonido.setMargin(new Insets(0, 0, 0, 0));
        bVideoSonido.addActionListener(new PaintMultimedia_bVideoSonido_actionAdapter(this));
        bImagen.setBorder(null);
        bImagen.setMaximumSize(new Dimension(40, 40));
        bImagen.setMinimumSize(new Dimension(40, 40));
        bImagen.setPreferredSize(new Dimension(43, 43));
        bImagen.setBorderPainted(false);
        bImagen.setFocusPainted(false);
        bImagen.setIcon(iconoImagen);
        bImagen.setMargin(new Insets(0, 0, 0, 0));
        bImagen.addActionListener(new PaintMultimedia_bImagen_actionAdapter(this));
        bDibujo.setBorder(null);
        bDibujo.setMaximumSize(new Dimension(40, 40));
        bDibujo.setMinimumSize(new Dimension(40, 40));
        bDibujo.setPreferredSize(new Dimension(43, 43));
        bDibujo.setBorderPainted(false);
        bDibujo.setFocusPainted(false);
        bDibujo.setIcon(iconoDibujo);
        bDibujo.setMargin(new Insets(0, 0, 0, 0));
        bDibujo.addActionListener(new TipoAplicacion_actionAdapter(this));
        flowLayout2.setHgap(10);
        flowLayout2.setVgap(0);
        PanelDibujo.addComponentListener(new PaintMultimedia_PanelDibujo_componentAdapter(this));
        PanelImagen.addComponentListener(new PaintMultimedia_PanelImagen_componentAdapter(this));
        mCerrar.addActionListener(new PaintMultimedia_mCerrar_actionAdapter(this));
        jPanel2.setPreferredSize(new Dimension(192, 20));
        jPanel2.setLayout(borderLayout2);
        lPosCursor.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lPosCursor.setBorder(BorderFactory.createLoweredBevelBorder());
        lPosCursor.setPreferredSize(new Dimension(100, 18));
        lPosCursor.setHorizontalAlignment(SwingConstants.CENTER);
        lPosCursor.setHorizontalTextPosition(SwingConstants.CENTER);
        lPosCursor.setText("(X=0 ,Y=0)");
        jPanel3.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.RIGHT);
        flowLayout3.setVgap(0);
        jPanel3.setPreferredSize(new Dimension(400, 50));
        menu.add(menuArchivo, 0);
        menu.add(menuModo, 1);
        menu.add(menuHerramientas, 2);
        menu.add(menuAyuda, 3);
        menuArchivo.add(mNuevo, 0);
        menuArchivo.add(mAbrir, 1);
        menuArchivo.addSeparator();
        menuArchivo.add(mCerrar, 3);
        menuArchivo.add(mGuardar, 4);
        menuArchivo.addSeparator();
        menuArchivo.add(mSalir, 6);
        menuModo.add(mDibujo, 0);
        menuAyuda.add(mAcercaDe, 0);
        menuModo.addSeparator();
        menuModo.add(mImagen, 2);
        menuModo.addSeparator();
        menuModo.add(mVideoSonido, 4);
        menuHerramientas.add(mEstadisticas, 0);
        setJMenuBar(menu);
        bAbrir.setToolTipText("Abrir Archivo");
        bCerrar.setToolTipText("Cierra el archivo");
        bAyuda.setToolTipText("Ayuda");
        barraArchivo.add(bNuevo);
        barraArchivo.add(bAbrir);
        barraArchivo.add(bCerrar);
        barraArchivo.add(bGuardar);
        barraArchivo.add(bAyuda);
        barraArchivo.add(jPanel3);
        jPanel3.add(jLabel1);
        jPanel3.add(PanelTipoAplicacion);
        contentPane.add(PanelOperaciones, java.awt.BorderLayout.CENTER);
        bDibujo.setName("bDibujo");
        bImagen.setName("bImagen");
        bVideoSonido.setName("bVideoSonido");
        PanelOperaciones.add(PanelDibujo, bDibujo.getName(), 0);
        PanelOperaciones.add(PanelImagen, bImagen.getName(), 1);
        PanelOperaciones.add(PanelVideoSonido, bVideoSonido.getName(), 2);
        buttonGroup2.add(bDibujo);
        buttonGroup2.add(bImagen);
        buttonGroup2.add(bVideoSonido);
        PanelTipoAplicacion.add(bDibujo, null);
        PanelTipoAplicacion.add(bImagen, null);
        PanelTipoAplicacion.add(bVideoSonido, null);
        contentPane.add(jPanel2, java.awt.BorderLayout.SOUTH);
        contentPane.add(barraArchivo, java.awt.BorderLayout.NORTH);
        jPanel2.add(barraEstado, java.awt.BorderLayout.CENTER);
        jPanel2.add(lPosCursor, java.awt.BorderLayout.EAST);
        this.bDibujo.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void bNuevo_actionPerformed(ActionEvent e) {
        if (this.bDibujo.isSelected()) {
            this.PanelDibujo.NuevoDibujo();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bAbrir_actionPerformed(ActionEvent e) {
        if (this.bImagen.isSelected()) {
            this.PanelImagen.CargarImagen();
        } else if (this.bVideoSonido.isSelected()) {
            this.PanelVideoSonido.AbrirReproduccion();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bGuardar_actionPerformed(ActionEvent e) {
        if (this.bDibujo.isSelected()) {
            this.PanelDibujo.GuardarArchivo();
        } else {
            this.PanelImagen.GuardarImagen();
        }
    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void mSalir_actionPerformed(ActionEvent actionEvent) {
        this.this_windowClosing(null);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
    void mAcercaDe_actionPerformed(ActionEvent actionEvent) {
        this.bAyuda.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void TipoAplicacion_actionPerformed(ActionEvent e) {
        //Mostramos el tipo de aplicacion deseado
        this.cardLayout1.show(this.PanelOperaciones, ((JToggleButton) e.getSource()).getName());

        //Desactivamos los botones correspondientes a cada tipo de aplicacion
        if ((JToggleButton) e.getSource() == this.bDibujo) {
            this.mAbrir.setVisible(false);
            this.mNuevo.setVisible(true);
            this.mCerrar.setVisible(false);
            this.mGuardar.setVisible(true);
            this.bCerrar.setVisible(false);
            this.bAbrir.setVisible(false);
            this.bNuevo.setVisible(true);
            this.bGuardar.setVisible(true);
            this.menuHerramientas.setVisible(false);
            PaintMultimedia.lPosCursor.setVisible(true);
        } else {
            if ((JToggleButton) e.getSource() == this.bImagen) {
                this.mAbrir.setVisible(true);
                this.mNuevo.setVisible(false);
                this.mCerrar.setVisible(true);
                this.mGuardar.setVisible(true);
                this.bCerrar.setVisible(true);
                this.bAbrir.setVisible(true);
                this.bNuevo.setVisible(false);
                this.bGuardar.setVisible(true);
                this.menuHerramientas.setVisible(false);
                PaintMultimedia.lPosCursor.setVisible(false);
            } else {
                if ((JToggleButton) e.getSource() == this.bVideoSonido) {
                    this.mAbrir.setVisible(true);
                    this.mNuevo.setVisible(false);
                    this.mCerrar.setVisible(true);
                    this.mGuardar.setVisible(false);
                    this.bCerrar.setVisible(true);
                    this.bAbrir.setVisible(true);
                    this.bNuevo.setVisible(false);
                    this.bGuardar.setVisible(false);
                    this.menuHerramientas.setVisible(true);
                    PaintMultimedia.lPosCursor.setVisible(false);
                }
            }
        }
    }

    /**
     *
     * @param e Evento
     */
    public void PanelDibujo_componentHidden(ComponentEvent e) {
        //Preguntamos si cerrar o no
        if (!this.PanelDibujo.NuevoDibujo()) {
            //Si no ha cerrado el panel dejamos el panel de dibujo
            this.bDibujo.doClick();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void PanelImagen_componentHidden(ComponentEvent e) {
        //Preguntamos si cerrar o no
        if (!this.PanelImagen.CerrarImagen()) {
            //Si no ha cerrado el panel dejamos el panel de dibujo
            this.bImagen.doClick();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void mAbrir_actionPerformed(ActionEvent e) {
        this.bAbrir.doClick();
    }

    class PaintMultimedia_bAbrir_actionAdapter implements ActionListener {

        private PaintMultimedia adaptee;

        PaintMultimedia_bAbrir_actionAdapter(PaintMultimedia adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bAbrir_actionPerformed(e);
        }
    }

    class PaintMultimedia_bNuevo_actionAdapter implements ActionListener {

        private PaintMultimedia adaptee;

        PaintMultimedia_bNuevo_actionAdapter(PaintMultimedia adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bNuevo_actionPerformed(e);
        }
    }

//PARA SALIRSE DE LA APLICACION
    class PaintMultimedia_mSalir_ActionAdapter implements ActionListener {

        PaintMultimedia adaptee;

        PaintMultimedia_mSalir_ActionAdapter(PaintMultimedia adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent actionEvent) {
            adaptee.mSalir_actionPerformed(actionEvent);
        }
    }

    class PaintMultimedia_mAcercaDe_ActionAdapter implements ActionListener {

        PaintMultimedia adaptee;

        PaintMultimedia_mAcercaDe_ActionAdapter(PaintMultimedia adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent actionEvent) {
            adaptee.mAcercaDe_actionPerformed(actionEvent);
        }
    }

    /**
     *
     * @param e Evento
     */
    public void mGuardar_actionPerformed(ActionEvent e) {
        this.bGuardar.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void mVideoSonido_actionPerformed(ActionEvent e) {
        this.bVideoSonido.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void mDibujo_actionPerformed(ActionEvent e) {
        this.bDibujo.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void mNuevo_actionPerformed(ActionEvent e) {
        this.bNuevo.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void mImagen_actionPerformed(ActionEvent e) {
        this.bImagen.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void mCerrar_actionPerformed(ActionEvent e) {
        this.bCerrar.doClick();
    }

    /**
     *
     * @param e Evento
     */
    public void bAyuda_actionPerformed(ActionEvent e) {
        PaintMultimedia_AboutBox dlg = new PaintMultimedia_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);
    }

    /**
     *
     * @param e Evento
     */
    public void mEstadisticas_actionPerformed(ActionEvent e) {
        if (this.bVideoSonido.isSelected()) {
            if (this.PanelVideoSonido.PanelEstadisticas.isVisible()) {
                this.PanelVideoSonido.PanelEstadisticas.setVisible(false);
                this.mEstadisticas.setText("Mostrar Estadisticas");
            } else {
                this.PanelVideoSonido.PanelEstadisticas.setVisible(true);
                this.mEstadisticas.setText("Ocultar Estadisticas");
            }
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bCerrar_actionPerformed(ActionEvent e) {
        if (this.bImagen.isSelected()) {
            this.PanelImagen.CerrarImagen();
        } else if (this.bVideoSonido.isSelected()) {
            this.PanelVideoSonido.CerrarReproduccion();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void this_windowClosing(WindowEvent e) {
        if (this.bDibujo.isSelected()) {
            if (this.PanelDibujo.NuevoDibujo()) {
                System.exit(0);
            }
        } else {
            if (this.bImagen.isSelected()) {
                if (this.PanelImagen.CerrarImagen()) {
                    System.exit(0);
                }
            } else {
                this.PanelVideoSonido.CerrarReproduccion();
                System.exit(0);
            }
        }
    }
}

class PaintMultimedia_this_windowAdapter extends WindowAdapter {

    private PaintMultimedia adaptee;

    PaintMultimedia_this_windowAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }
}

class PaintMultimedia_mEstadisticas_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mEstadisticas_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mEstadisticas_actionPerformed(e);
    }
}

class PaintMultimedia_bAyuda_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_bAyuda_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAyuda_actionPerformed(e);
    }
}

class PaintMultimedia_mCerrar_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mCerrar_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mCerrar_actionPerformed(e);
    }
}

class PaintMultimedia_mNuevo_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mNuevo_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mNuevo_actionPerformed(e);
    }
}

class PaintMultimedia_mDibujo_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mDibujo_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mDibujo_actionPerformed(e);
    }
}

class PaintMultimedia_mImagen_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mImagen_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mImagen_actionPerformed(e);
    }
}

class PaintMultimedia_mVideoSonido_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mVideoSonido_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mVideoSonido_actionPerformed(e);
    }
}

class PaintMultimedia_mGuardar_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mGuardar_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mGuardar_actionPerformed(e);
    }
}

class PaintMultimedia_mAbrir_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_mAbrir_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.mAbrir_actionPerformed(e);
    }
}

class PaintMultimedia_PanelDibujo_componentAdapter extends ComponentAdapter {

    private PaintMultimedia adaptee;

    PaintMultimedia_PanelDibujo_componentAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        adaptee.PanelDibujo_componentHidden(e);
    }
}

class PaintMultimedia_bVideoSonido_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_bVideoSonido_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.TipoAplicacion_actionPerformed(e);
    }
}

class PaintMultimedia_bImagen_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_bImagen_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.TipoAplicacion_actionPerformed(e);
    }
}

class PaintMultimedia_PanelImagen_componentAdapter extends ComponentAdapter {

    private PaintMultimedia adaptee;

    PaintMultimedia_PanelImagen_componentAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        adaptee.PanelImagen_componentHidden(e);
    }
}

class TipoAplicacion_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    TipoAplicacion_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.TipoAplicacion_actionPerformed(e);
    }
}

class PaintMultimedia_bGuardar_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_bGuardar_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bGuardar_actionPerformed(e);
    }
}

class PaintMultimedia_bCerrar_actionAdapter implements ActionListener {

    private PaintMultimedia adaptee;

    PaintMultimedia_bCerrar_actionAdapter(PaintMultimedia adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCerrar_actionPerformed(e);
    }
}
