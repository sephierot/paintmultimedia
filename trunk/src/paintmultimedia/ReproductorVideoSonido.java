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

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Color;

import javax.media.Player;
import javax.media.ControllerEvent;
import javax.media.Manager;
import javax.media.RealizeCompleteEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerListener;
import javax.media.rtp.RTPControl;
import javax.media.control.FrameRateControl;
import javax.media.CachingControl;
import javax.media.Control;
import javax.media.control.BitRateControl;
import javax.media.rtp.ReceptionStats;
import javax.media.NoPlayerException;
import javax.media.StopEvent;
import javax.media.Time;
import javax.media.StartEvent;
import javax.media.EndOfMediaEvent;
import javax.media.CachingControlEvent;

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
public class ReproductorVideoSonido extends JPanel implements
        ControllerListener {

    //Opciones de reproduccion//
    private boolean autoRepetir;
    private boolean mostrarTextoEstado;
    private boolean autoReproducir;
    //FIN Opciones de reproduccisn//
    //Componentes y controles
    private Component componenteControl = null;
    private Component componenteVisual = null;
    private Component componenteBarraProgreso = null;
    private Control[] controles = null;
    private BitRateControl controlTasaBits = null;
    private FrameRateControl controlTasaCuadros = null;
    private RTPControl controlRTP = null;
    private CachingControl controlCache = null;
    //FIN Componentes y controles
    //Longitud de la barra de progreso de descarga
    private long longBarraProgreso;
    //Progreso de la barra de progreso de descarga
    private long progBarraProgreso;
    //Estadisticas de recepcion
    private ReceptionStats estadisticasRTP = null;
    //Incorporamos un temporizador para mostrar informacion extra en la UI cada
    //cierto intervalo de tiempo, le asociamos una "tareaPeriodica"
    private Timer temporizador = null;
    private int intervalo;
    private ActionListener tareaPeriodica;
    //FIN Temporizador
    Player player = null;
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel PanelEstadisticas = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JLabel cuadros = new JLabel();
    JLabel lcuadros = new JLabel();
    JLabel bitrate = new JLabel();
    JLabel lBitrate = new JLabel();
    JPanel jPanel4 = new JPanel();
    JLabel paquetesPerdidos = new JLabel();
    JLabel lPaquetesPerdidos = new JLabel();
    JPanel jPanel5 = new JPanel();
    JLabel estado = new JLabel();
    JLabel lEstado = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();

    /**
     *
     */
    public ReproductorVideoSonido() {
        //Llmamos al constructor ampliado con las opciones por defecto
        this(true, true, true);
    }

    /**
     *
     * @param autoRepetir
     * @param autoReproducir
     * @param mostrarTextoEstado
     */
    public ReproductorVideoSonido(boolean autoRepetir,
            boolean autoReproducir,
            boolean mostrarTextoEstado) {
        super();

        this.autoRepetir = autoRepetir;
        this.autoReproducir = autoReproducir;
        this.mostrarTextoEstado = mostrarTextoEstado;

        //Implementa la tarea perisdica del temporizador
        tareaPeriodica = new TareaPeriodica_actionAdapter(this);

        //Establecemos el intervalo de refresco en la UI de la informacisn extra
        this.intervalo = 1000; //milisegundos
        //Creamos el objeto temporizador
        this.temporizador = new Timer(intervalo, this.tareaPeriodica);

        try {
            Inicio();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Inicio() throws Exception {
        this.setLayout(borderLayout1);
        PanelEstadisticas.setBorder(BorderFactory.createEtchedBorder());
        PanelEstadisticas.setPreferredSize(new Dimension(150, 109));
        PanelEstadisticas.setToolTipText("Muestra la informacion del video");
        PanelEstadisticas.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(4);
        gridLayout1.setVgap(3);
        cuadros.setPreferredSize(new Dimension(40, 14));
        cuadros.setHorizontalAlignment(SwingConstants.RIGHT);
        cuadros.setText("0 fps");
        lPaquetesPerdidos.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lPaquetesPerdidos.setText("Paq.Perdidos:");
        lEstado.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lEstado.setPreferredSize(new Dimension(45, 14));
        lEstado.setText("Estado:");
        jPanel2.setLayout(flowLayout1);
        jPanel3.setLayout(flowLayout2);
        jPanel4.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel5.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        lcuadros.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lcuadros.setPreferredSize(new Dimension(65, 14));
        lcuadros.setText("Cuadros/s:");
        lBitrate.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        lBitrate.setPreferredSize(new Dimension(45, 14));
        lBitrate.setText("Bitrate:");
        bitrate.setPreferredSize(new Dimension(60, 14));
        bitrate.setHorizontalAlignment(SwingConstants.RIGHT);
        bitrate.setText("0 kbps");
        paquetesPerdidos.setHorizontalAlignment(SwingConstants.RIGHT);
        paquetesPerdidos.setText("0 paquetes");
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        estado.setFont(new java.awt.Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        estado.setForeground(Color.red);
        estado.setPreferredSize(new Dimension(100, 10));
        estado.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(PanelEstadisticas, java.awt.BorderLayout.EAST);
        PanelEstadisticas.add(jPanel3, 0);
        PanelEstadisticas.add(jPanel2, 1);
        PanelEstadisticas.add(jPanel4, 2);
        PanelEstadisticas.add(jPanel5, 3);
        jPanel4.add(lPaquetesPerdidos, 0);
        jPanel4.add(paquetesPerdidos, 1);
        jPanel5.add(lEstado, 0);
        jPanel5.add(estado, 1);
        jPanel3.add(lcuadros, 0);
        jPanel3.add(cuadros, 1);
        jPanel2.add(lBitrate, 0);
        jPanel2.add(bitrate, 1);
    }

    /**
     * Reproduce el contenido multimedia abierto
     */
    public void reproducir() {
        if (player != null) {
            player.start();
        }
    }

    /**
     * Detiene la reproduccisn del contenido multimedia
     */
    public void detener() {
        if (player != null) {
            player.stop();
        }
    }

    /**
     * Establece las opciones de la reproduccisn
     * @param autoRepetir Indica si se comenzara automaticamente la reproduccisn
     *   desde el principio cuando se alcance el final del medio.
     * @param autoReproducir Indica si se iniciara automaticamente la reproduccisn
     *   del medio abierto
     * @param mostrarTextoEstado Indica si se procesara y mostrara la informacisn
     *   adicional del medio en la GUI
     */
    public void setOpciones(boolean autoRepetir, boolean autoReproducir,
            boolean mostrarTextoEstado) {
        this.autoRepetir = autoRepetir;
        this.autoReproducir = autoReproducir;
        this.mostrarTextoEstado = mostrarTextoEstado;
    }

    /**
     * Necesario para implementar la interfaz ControllerListener. Encargado de
     * aqadir los componentes: control, visual, barra de progreso, etcitera... a
     * la interfaz grafica (propiedad UI)
     * @param event El evento que ha provocado la llamada al mitodo
     */
    public synchronized void controllerUpdate(ControllerEvent event) {
        //Comprobamos que el reproductor esta "vivo"
        if (player == null) {
            return;
        }
        //Qui evento se ha producido?
        //Si el reproductor ha entrado en estado realizado correctamente
        if (event instanceof RealizeCompleteEvent) {
            componenteControl = player.getControlPanelComponent();
            componenteVisual = player.getVisualComponent();

            //Se aqade el componente de visualizacisn y el de control a la UI
            if (componenteVisual != null) {
                this.add(componenteVisual, java.awt.BorderLayout.CENTER);
            }
            if (componenteControl != null) {
                this.add(componenteControl, java.awt.BorderLayout.SOUTH);
            }
            //Obtenemos los controles disponibles para el reproductor
            controles = player.getControls();
            //Extraemos de entre ellos los controles que proporcionan informacisn
            //acerca de la reproduccisn que se visualizara en la UI
            for (int i = 0; i < controles.length; i++) {
                if (controles[i] instanceof BitRateControl) {
                    controlTasaBits = (BitRateControl) controles[i];
                }
                if (controles[i] instanceof FrameRateControl) {
                    controlTasaCuadros = (FrameRateControl) controles[i];
                }
                if (controles[i] instanceof RTPControl) {
                    controlRTP = (RTPControl) controles[i];
                }
            }

            this.validate();

            if (autoReproducir) {
                player.start();
            }
        } //Si se ha alcanzado el final del contenido que se esta reproduciendo
        else if (event instanceof EndOfMediaEvent) {
            //"Rebobinamos" el contenido multimedia
            player.setMediaTime(new Time(0));
            player.stop();
            this.estado.setText("Detenido");
            //Si asm se ha establecido, se comienza de nuevo la reproduccisn
            if (autoRepetir) {
                player.start();
            }
        } //Si se estan descargando los datos del contenido multimedia en una cachi
        else if (event instanceof CachingControlEvent) {
            CachingControlEvent e = (CachingControlEvent) event;
            controlCache = e.getCachingControl();
            //Extraemos (como componente) la barra de progreso de la descarga
            if (componenteBarraProgreso == null) {
                componenteBarraProgreso = controlCache.getProgressBarComponent();
                progBarraProgreso = e.getContentProgress();
                longBarraProgreso = controlCache.getContentLength();
                if (componenteBarraProgreso != null) {
                    //Se añade la barra de progreso a la UI
                    this.add(componenteBarraProgreso,
                            java.awt.BorderLayout.NORTH);
                    this.validate();
                }
            }

            //Se elimina la barra cuando concluye la descarga
            if (componenteBarraProgreso != null) {
                if (progBarraProgreso == longBarraProgreso) {
                    this.remove(componenteBarraProgreso);
                    componenteBarraProgreso = null;
                    this.validate();
                }
            }
        } //Si se produce un error indeterminado en la reproduccisn
        else if (event instanceof ControllerErrorEvent) {
            player = null;
            System.err.println(((ControllerErrorEvent) event).getMessage());
            JOptionPane.showMessageDialog(this,
                    ((ControllerErrorEvent) event).getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            this.estado.setText(((ControllerErrorEvent) event).getMessage());
        } //Si se inicia la reproduccisn del contenido multimedia
        else if (event instanceof StartEvent) {
            //Iniciamos el temporizador
            temporizador.start();
            this.estado.setText("Reproduciendo");
        } //Si se detiene la reproduccisn del contenido multimedia
        else if (event instanceof StopEvent) {
            //Detenemos el temporizador
            temporizador.stop();
            this.estado.setText("Detenido");
        }
    }

    void CerrarReproduccion() {
        //Paramos el reproductor y liberamos los recursos
        if (player != null) {
            player.close();
        }
        if (componenteVisual != null) {
            this.remove(componenteVisual);
        }
        if (componenteControl != null) {
            this.remove(componenteControl);
        }
        System.gc();
        System.runFinalization();

        this.estado.setText(" ");
        this.validate();
    }

    /**
     *
     * @return
     */
    public boolean AbrirReproduccion() {
        JFileChooser selecc_fichero = new JFileChooser();
        selecc_fichero.setDialogType(JFileChooser.OPEN_DIALOG);

        int valor = selecc_fichero.showOpenDialog(this);

        //Obtenemos el path del archivo
        if (valor == JFileChooser.APPROVE_OPTION) {
            //Cerramos la reproduccion si hubiera alguna ya abierta
            this.CerrarReproduccion();

            //Abrimos el nuevo medio
            try {
                //Se crea el reproductor JMF para el fichero
                if (player != null) {
                    player.stop();
                    player.deallocate();
                }
                player = Manager.createPlayer(selecc_fichero.getSelectedFile().
                        toURL());
                player.addControllerListener(this);
                player.realize();
                this.validate();
            } //Se tratan las posibles excepciones
            catch (IOException e) {
                System.err.println("Excepcion de Lectura/Escritura");
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Excepcion de Lectura/Escritura",
                        JOptionPane.ERROR_MESSAGE);
                this.estado.setText("Excepcion de Lectura/Escritura");
            } catch (NoPlayerException e) {
                System.err.println("No existe reproductor");
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "No existe reproductor",
                        JOptionPane.ERROR_MESSAGE);
                this.estado.setText("No existe reproductor");
            }

            return true;
        }

        return false;
    }

    private void TareaPeriodica() {
        if (this.mostrarTextoEstado) {
            if (this.controlTasaCuadros != null) {
                Float tasaCuadros = new Float(this.controlTasaCuadros.getFrameRate());
                this.cuadros.setText(tasaCuadros.toString() + " fps");
            }
            if (this.controlTasaBits != null) {
                Integer tasaBits = new Integer(this.controlTasaBits.getBitRate()
                        / 1000);
                this.bitrate.setText(tasaBits.toString() + " kbps");
            }
            if (this.controlRTP != null) {
                this.estadisticasRTP = controlRTP.getReceptionStats();
                if (this.estadisticasRTP != null) {
                    Integer npaquetesPerdidos = new Integer(this.estadisticasRTP.getPDUlost());
                    this.paquetesPerdidos.setText(npaquetesPerdidos.toString()
                            + " paquetes perdidos");
                }
            }
        } else {
            this.estado.setText("Reproduciendo");
        }
    }

    class TareaPeriodica_actionAdapter implements ActionListener {

        private ReproductorVideoSonido adaptee;

        TareaPeriodica_actionAdapter(ReproductorVideoSonido adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.TareaPeriodica();
        }
    }
}
