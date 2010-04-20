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

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Graphics2D;
import java.io.*;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.*;
import java.awt.Insets;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.Kernel;
import java.awt.image.ConvolveOp;
import java.awt.image.RescaleOp;
import java.util.Vector;
import java.awt.FlowLayout;

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
public class EditorImagen extends JPanel {

    JPanel PanelImagen = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JScrollPane jSPTapiz = new JScrollPane();
    Tapiz PanelTapiz = new Tapiz(200, 200);
    JPanel PanelFiltrosImagen = new JPanel();
    JSlider sBrillo = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
    JPanel PanelBrillo = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    JLabel jLabel2 = new JLabel();
    BorderLayout borderLayout6 = new BorderLayout();
    BorderLayout borderLayout1 = new BorderLayout();
    //Constantes que definen los tipos de transformaciones en la imagen
    final int SUAVIZADO = 0, PASOBAJO = 1, MEDIA = 2, ROBERTS_H = 3, ROBERTS_V =
            4;
    final int PREWITT_H = 5, PREWITT_V = 6, SOBEL_H = 7, SOBEL_V = 8,
            FREICHEN_H = 9;
    final int FREICHEN_V = 10, LAPLACIANO = 11, SHARPENBAJO = 12, SHARPENALTO =
            13;
    final float[][] TRANSFORMACIONES = {{1f / 16f, 2f / 16f, 1f / 16f,
            2f / 16f, 4f / 16f, 2f / 16f, //SUAVIZADO
            1f / 16f, 2f / 16f, 1f / 16f},
        {0f, 1f / 10f, 0f,
            1f / 10f, 6f / 10f, 1f / 10f, //PASOBAJO
            0f, 1f / 10f, 0f},
        {1f / 9f, 1f / 9f, 1f / 9f,
            1f / 9f, 1f / 9f, 1f / 9f, //MEDIA
            1f / 9f, 1f / 9f, 1f / 9f},
        {0f, 0f, -1f,
            0f, 1f, 0f, //ROBERTS_HORIZONTAL
            0f, 0f, 0f},
        {-1f, 0f, 0f,
            0f, 1f, 0f, //ROBERTS_VERTICAL
            0f, 0f, 0f},
        {1f, 0f, -1f,
            1f, 0f, -1f, //PREWITT_HORIZONTAL
            1f, 0f, -1f},
        {-1f, -1f, -1f,
            0f, 0f, 0f, //PREWITT_VERTICAL
            1f, 1f, 1f},
        {1f, 0f, -1f,
            2f, 0f, -2f, //SOBEL_HORIZONTAL
            1f, 0f, -1f},
        {-1f, -2f, -1f,
            0f, 0f, 0f, //SOBEL_VERTICAL
            1f, 2f, 1f},
        {1f, 0f, -1f,
            (float) Math.sqrt(2f), 0f,
            (float) -Math.sqrt(2f), //FREICHEN_HORIZONTAL
            1f, 0f, -1f},
        {-1f, (float) -Math.sqrt(2f), -1f,
            0f, 0f, 0f, //FREICHEN_VERTICAL
            1f, (float) Math.sqrt(2f), 1f},
        {-1f, -1f, -1f,
            -1f, 8f, -1f, //LAPLACIANO
            -1f, -1f, -1f},
        {1f, -2f, 1f,
            -2f, 5f, -2f, //SHARPENBAJO
            1f, -2f, 1f},
        {0f, -1f, 0f,
            -1f, 5f, -1f, //SHARPENALTO
            0f, -1f, 0f}
    };
    
    //Variable que indica si se ha modificado o no la imagen
    boolean modificado = false;
    //variable que indica si se abierto alguna imagen
    boolean imagenAbierta = false;
    //Vector de transformaciones a la imagen
    Vector transformaciones = new Vector();
    BufferedImage imagenSinModificar = null, imagenTransformada = null;
    BufferedImage iconoAplicacion;
    JButton bReestablecer = new JButton();
    JPanel jPanel1 = new JPanel();
    JButton bpasobajo = new JButton();
    JButton bRoberts = new JButton();
    JButton bSuavizado = new JButton();
    JPanel PanelSuavizados = new JPanel();
    JButton bmedia = new JButton();
    JLabel jLabel3 = new JLabel();
    JPanel PanelFronteras = new JPanel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel jLabel4 = new JLabel();
    JButton bPrewitt = new JButton();
    JButton bSobel = new JButton();
    JButton bLaplaciano = new JButton();
    JButton bFreiChen = new JButton();
    JPanel PanelRealce = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();
    JButton bSharpenAlto = new JButton();
    JButton bSharpenBajo = new JButton();
    JLabel jLabel5 = new JLabel();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout4 = new FlowLayout();

    public EditorImagen() {
        super();
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
        this.sBrillo.setMajorTickSpacing(15);
        this.sBrillo.setMinorTickSpacing(5);
        this.sBrillo.setPaintTicks(true);
        sBrillo.setBorder(BorderFactory.createRaisedBevelBorder());
        this.sBrillo.setPaintLabels(true);

        //Creamos las etiquetas para el jslider
        Dictionary etiq = new Hashtable();

        etiq.put(new Integer(-255),
                new JLabel("+ Oscuridad"));

        for (int i = -225; i <= 225; i += 15) {
            etiq.put(new Integer(i), new JLabel(Integer.toString(i)));
        }

        etiq.put(new Integer(255),
                new JLabel("+ Claridad"));

        this.sBrillo.setLabelTable(etiq);

        this.jSPTapiz.setViewportView(this.PanelTapiz);
        this.iconoAplicacion = ImageIO.read(PaintMultimedia.class.getResource(
                "/imagenes/PaintMultimedia.png"));

        this.PanelTapiz.setImagen(this.iconoAplicacion);

        //Aplicamos los mnemonicos
        this.bSuavizado.setMnemonic(this.SUAVIZADO);
        this.bpasobajo.setMnemonic(this.PASOBAJO);
        this.bmedia.setMnemonic(this.MEDIA);
        this.bRoberts.setMnemonic(this.ROBERTS_H);
        this.bPrewitt.setMnemonic(this.PREWITT_H);
        this.bSobel.setMnemonic(this.SOBEL_H);
        this.bFreiChen.setMnemonic(this.FREICHEN_H);
        this.bLaplaciano.setMnemonic(this.LAPLACIANO);
        this.bSharpenBajo.setMnemonic(this.SHARPENBAJO);
        this.bSharpenAlto.setMnemonic(this.SHARPENALTO);

        jLabel2.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel2.setPreferredSize(new Dimension(123, 15));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel2.setText("  Brillo de la imagen:");
        borderLayout4.setHgap(5);
        borderLayout6.setHgap(6);
        borderLayout6.setVgap(5);
        PanelImagen.setBorder(null);
        PanelImagen.setPreferredSize(new Dimension(700, 700));
        PanelImagen.setLayout(borderLayout2);

        sBrillo.setPaintTicks(true);
        sBrillo.setToolTipText("Modifica el brillo de la imagen");
        sBrillo.addChangeListener(new EditorImagen_sBrillo_changeAdapter(this));
        PanelBrillo.setLayout(borderLayout4);

        PanelFiltrosImagen.setLayout(borderLayout6);
        PanelTapiz.setBackground(new Color(238, 238, 238));
        PanelTapiz.setBorder(BorderFactory.createLineBorder(Color.black));
        jSPTapiz.setBorder(BorderFactory.createEtchedBorder());
        borderLayout2.setHgap(15);
        borderLayout2.setVgap(5);
        this.setLayout(borderLayout1);
        bReestablecer.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bReestablecer.setPreferredSize(new Dimension(90, 23));
        bReestablecer.setToolTipText("Reestablece la imagen original");
        bReestablecer.setHorizontalTextPosition(SwingConstants.CENTER);
        bReestablecer.setMargin(new Insets(2, 5, 2, 5));
        bReestablecer.setText("Reestablecer");
        bReestablecer.addActionListener(new EditorImagen_bReestablecer_actionAdapter(this));
        jPanel1.setLayout(flowLayout4);
        jPanel1.setPreferredSize(new Dimension(95, 23));
        this.setBorder(null);
        bpasobajo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bpasobajo.setPreferredSize(new Dimension(120, 25));
        bpasobajo.setText("Filtro Paso Bajo");
        bpasobajo.addActionListener(new EditorImagen_bpasobajo_actionAdapter(this));
        bRoberts.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bRoberts.setPreferredSize(new Dimension(110, 25));

        bRoberts.setText("Filtro Roberts");
        bRoberts.addActionListener(new EditorImagen_bRoberts_actionAdapter(this));
        bSuavizado.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bSuavizado.setPreferredSize(new Dimension(120, 25));

        bSuavizado.setText("Suavizar");
        bSuavizado.addActionListener(new Transformaciones_actionAdapter(this));
        bmedia.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bmedia.setPreferredSize(new Dimension(120, 25));

        bmedia.setText("Media");
        bmedia.addActionListener(new EditorImagen_bmedia_actionAdapter(this));
        PanelSuavizados.setBorder(null);
        PanelSuavizados.setInputVerifier(null);
        PanelSuavizados.setLayout(flowLayout1);
        jLabel3.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel3.setBorder(null);
        jLabel3.setText("Filtros Difuminado:");
        PanelFronteras.setBorder(BorderFactory.createEtchedBorder());
        PanelFronteras.setLayout(flowLayout2);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jTabbedPane1.setForeground(new Color(205, 44, 22));
        jTabbedPane1.setBorder(null);
        jTabbedPane1.setPreferredSize(new Dimension(150, 80));
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(15);
        flowLayout1.setVgap(12);
        jLabel4.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel4.setText("Fronteras:");
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(10);
        flowLayout2.setVgap(12);
        bPrewitt.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bPrewitt.setPreferredSize(new Dimension(110, 25));
        bPrewitt.setText("Filtro Prewitt");
        bPrewitt.addActionListener(new EditorImagen_bPrewitt_actionAdapter(this));
        bSobel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bSobel.setPreferredSize(new Dimension(110, 25));
        bSobel.setText("Filtro Sobel");
        bSobel.addActionListener(new EditorImagen_bSobel_actionAdapter(this));
        bLaplaciano.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bLaplaciano.setPreferredSize(new Dimension(110, 25));
        bLaplaciano.setMargin(new Insets(2, 5, 2, 5));
        bLaplaciano.setText("Filtro Laplaciano");
        bLaplaciano.addActionListener(new EditorImagen_bLaplaciano_actionAdapter(this));
        bFreiChen.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bFreiChen.setPreferredSize(new Dimension(110, 25));
        bFreiChen.setMargin(new Insets(2, 10, 2, 10));
        bFreiChen.setText("Filtro Frei-Chen");
        bFreiChen.addActionListener(new EditorImagen_bFreiChen_actionAdapter(this));
        borderLayout1.setVgap(10);
        PanelRealce.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setHgap(15);
        flowLayout3.setVgap(12);
        bSharpenAlto.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bSharpenAlto.setPreferredSize(new Dimension(105, 25));
        bSharpenAlto.setText("Sharpen alto");
        bSharpenAlto.addActionListener(new EditorImagen_bSharpenAlto_actionAdapter(this));
        bSharpenBajo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        bSharpenBajo.setPreferredSize(new Dimension(107, 25));
        bSharpenBajo.setText("Sharpen Bajo");
        bSharpenBajo.addActionListener(new EditorImagen_bSharpenBajo_actionAdapter(this));
        jLabel5.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel5.setText("Filtros de Realce de Imagen:");
        PanelBrillo.setPreferredSize(new Dimension(328, 55));
        flowLayout4.setHgap(3);
        flowLayout4.setVgap(15);
        PanelImagen.add(jSPTapiz, java.awt.BorderLayout.CENTER);
        this.add(PanelImagen, java.awt.BorderLayout.CENTER);
        PanelBrillo.add(jLabel2, java.awt.BorderLayout.WEST);
        PanelBrillo.add(sBrillo, java.awt.BorderLayout.CENTER);
        jPanel1.add(bReestablecer, null);
        PanelFiltrosImagen.add(jPanel1, java.awt.BorderLayout.EAST);
        PanelSuavizados.add(jLabel3);
        PanelSuavizados.add(bpasobajo);
        PanelSuavizados.add(bmedia);
        PanelSuavizados.add(bSuavizado);
        PanelFronteras.add(jLabel4);
        PanelFronteras.add(bRoberts);
        PanelFronteras.add(bPrewitt);
        PanelFronteras.add(bSobel);
        PanelFronteras.add(bFreiChen);
        PanelFronteras.add(bLaplaciano);
        jTabbedPane1.add(PanelSuavizados, "PanelSuavizados", 0);
        jTabbedPane1.add(PanelFronteras, "Detección de Fronteras", 1);
        jTabbedPane1.add(PanelRealce, "Realce de imagen", 2);
        PanelRealce.add(jLabel5);
        PanelRealce.add(bSharpenBajo);
        PanelRealce.add(bSharpenAlto);
        PanelImagen.add(PanelFiltrosImagen, java.awt.BorderLayout.SOUTH);
        PanelFiltrosImagen.add(PanelBrillo, java.awt.BorderLayout.CENTER);
        PanelFiltrosImagen.add(jTabbedPane1, java.awt.BorderLayout.NORTH);
        PanelFiltrosImagen.add(jPanel2, java.awt.BorderLayout.SOUTH);
    }

    /**
     *
     * @return Si ha tenido exito
     */
    public boolean CargarImagen() {
        boolean error = false;

        JFileChooser selecc_fichero = new JFileChooser();
        FiltroArchivos jpgs, gif, png, bmp, todas_imagenes;

        selecc_fichero.setDialogType(JFileChooser.OPEN_DIALOG);

        //Quitamos la opcion de que acepte todos los archivos
        selecc_fichero.setAcceptAllFileFilterUsed(false);

        //Seleccionamos el tipo de archivos que queremos
        jpgs = new FiltroArchivos(new String[]{"jpg", "jpeg", "jpe"},
                "Archivos de tipo Jpeg");
        png = new FiltroArchivos("png",
                "Tipo de imagen png, Portable network graphics");
        gif = new FiltroArchivos("gif",
                "Tipo de imagen gif, Formato de intercambio de gráficos");

        bmp = new FiltroArchivos("bmp", "Imagen mapa de bits");

        todas_imagenes = new FiltroArchivos(new String[]{"jpg", "jpeg", "jpe",
                    "png", "gif"},
                "Todos los formatos de imagen validos");
        //Aplicamos los filtros al filechooser
        selecc_fichero.addChoosableFileFilter(jpgs);
        selecc_fichero.addChoosableFileFilter(gif);
        selecc_fichero.addChoosableFileFilter(png);
        selecc_fichero.addChoosableFileFilter(bmp);
        selecc_fichero.addChoosableFileFilter(todas_imagenes);

        int valor = selecc_fichero.showOpenDialog(this);

        //Obtenemos el path del archivo
        if (valor == JFileChooser.APPROVE_OPTION) {
            //Cerramos la imagen si hubiera alguna ya abierta
            if (this.CerrarImagen()) {
                BufferedImage imagenLeida = null;

                //Abrimos la imagen del disco
                try {
                    imagenLeida = ImageIO.read(selecc_fichero.getSelectedFile());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    error = true;
                }

                //Comprobamos si se ha leido correctamente la imagen
                if (imagenLeida != null) {
                    try {
                        MediaTracker tracker = new MediaTracker(this);
                        tracker.addImage(imagenLeida, 0);
                        tracker.waitForID(0);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        error = true;
                    }

                    //Copiamos en la imagen de la clase la imagen leida transformandola a RGB o a ARGB si posee alpha
                    if (imagenLeida.getColorModel().hasAlpha()) {
                        this.imagenSinModificar = new BufferedImage(imagenLeida.getWidth(), imagenLeida.getHeight(),
                                BufferedImage.TYPE_INT_ARGB);
                    } else {
                        this.imagenSinModificar = new BufferedImage(imagenLeida.getWidth(), imagenLeida.getHeight(),
                                BufferedImage.TYPE_INT_RGB);
                    }

                    //Hacemos otra copia para almacenar la imagen que se modificara
                    this.imagenTransformada = new BufferedImage(this.imagenSinModificar.getWidth(),
                            this.imagenSinModificar.getHeight(),
                            this.imagenSinModificar.getType());

                    //Copiamos tanto en la imagen modificada como en la sin modificar
                    Graphics2D grImagenSinM = this.imagenSinModificar.createGraphics();
                    grImagenSinM.drawImage(imagenLeida, 0, 0, this.PanelTapiz);

                    Graphics2D grImagenM = this.imagenTransformada.createGraphics();
                    grImagenM.drawImage(imagenLeida, 0, 0, this.PanelTapiz);

                    /* byte chlut[] = new byte[256];
                    for (int j = 0; j < 200; j++)
                    chlut[j] = (byte) (256 - j);
                    ByteLookupTable blut = new ByteLookupTable(0, chlut);
                    LookupOp lop = new LookupOp(blut, null);
                    lop.filter(this.imagenSinModificar, this.imagenModificada);
                     */

                    //Establecemos una nueva imagen para el tapiz una vez cargada
                    this.PanelTapiz.setImagen(this.imagenTransformada);

                    //Ponemos la variable de modificado a false
                    this.modificado = false;

                    //Ponemos la variable de imagen abierta a true
                    this.imagenAbierta = true;

                    return true;
                } else {
                    error = true;
                }
            }

            //Si ha habido un error mostramos el mensaje
            if (error) {
                //No se puede abrir el archivo
                JOptionPane.showMessageDialog(this,
                        "No se puede abrir el archivo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }

    /**
     *
     * @return Si se ha cerrado la imagen
     */
    public boolean CerrarImagen() {
        boolean borrar = false;

        if (this.modificado) {
            //Mostramos el dialogo de confirmacion
            int opcion = JOptionPane.showConfirmDialog(this,
                    "Los cambios NO han sido guardados, ¿Desea guardar los cambios ahora?",
                    "ATENCIÓN", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            //Cerramos la imagen si ha dicho que no quiere guardar los cambios
            if (opcion == JOptionPane.NO_OPTION) {
                borrar = true;
            } else if (opcion == JOptionPane.YES_OPTION) {
                //Guardamos el fichero si ha aceptado
                if (this.GuardarImagen()) {
                    borrar = true;
                } else {
                    borrar = false;
                }
            }
        } //Caso en el que no esta modificada la imagen se cierra sin confirmacion
        else {
            borrar = true;
        }

        //Comprobamos si hay que borrar o no la imagen
        if (borrar) {
            //Eliminamos las imagenes
            this.imagenTransformada = null;
            this.imagenSinModificar = null;

            //Borramos el vector
            this.transformaciones.removeAllElements();

            //Establecemos por defecto otra vez todos las variables
            this.modificado = false;
            this.imagenAbierta = false;

            //Reestablecemos el icono de la aplicacion
            this.PanelTapiz.setImagen(this.iconoAplicacion);

            //Establecemos otra vez el icono de la aplicacion
            this.PanelTapiz.setImagen(this.iconoAplicacion);

            //Liberamos la memoria no utilizada
            System.gc();
            System.runFinalization();

            return true;
        }

        return false;
    }

    /**
     *
     * @return Si se ha guardado la imagen con exito
     */
    public boolean GuardarImagen() {
        if (this.modificado) {
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

                //Comprobamos el tipo elegido
                if (archivo.getFileFilter().equals(png)) {
                    //Guardamos en formato png
                    File destinoPNG = new File(destino.getPath() + ".png");
                    if (!destinoPNG.exists()) {
                        try {
                            ImageIO.write(this.imagenTransformada, "png",
                                    destinoPNG);
                            return true;
                        } catch (IOException ex1) {
                            System.out.println(ex1.getMessage());
                            error = true;
                        }
                    } else {
                        //Preguntamos si sobreescribir el archivo
                        int eleccion = JOptionPane.showConfirmDialog(this,
                                "CUIDADO: el archivo ya existe, ¿Desea sobreescribirlo?",
                                "SOBREESCRIBIR", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if (eleccion == JOptionPane.YES_OPTION) {
                            //Sobreescribimos el archivo
                            try {
                                ImageIO.write(this.imagenTransformada, "png",
                                        destinoPNG);
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

                            encoder.encode(this.imagenTransformada);
                            out.close();
                            return true;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            error = true;
                        }
                    } else {
                        //Preguntamos si sobreescribir el archivo
                        int eleccion = JOptionPane.showConfirmDialog(this,
                                "CUIDADO: el archivo ya existe, ¿Desea sobreescribirlo?",
                                "SOBREESCRIBIR", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                        //Sobreescribimos el archivo
                        if (eleccion == JOptionPane.YES_OPTION) {
                            try {
                                FileOutputStream out = new FileOutputStream(
                                        destinoJPEG);

                                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

                                encoder.encode(this.imagenTransformada);
                                out.close();
                                this.modificado = false;

                                return true;
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                error = true;
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
            }
        }

        return false;
    }

    /**
     *
     * @param e Evento
     */
    public void sBrillo_stateChanged(ChangeEvent e) {
        //Comprobamos que se ha cargado una imagen
        if (this.imagenAbierta) {
            RescaleOp rop = new RescaleOp(1.0f,
                    (float) this.sBrillo.getValue(), null);
            BufferedImage aux = new BufferedImage(this.imagenSinModificar.getWidth(),
                    this.imagenSinModificar.getHeight(),
                    this.imagenSinModificar.getType());

            //Aplicamos el filtro de brillo a la imagen transformada por los filtros
            rop.filter(this.imagenTransformada, aux);

            //Establecemos la nueva imagen a mostrar
            this.PanelTapiz.setImagen(aux);

            //Ponemos que se ha modificado la imagen
            this.modificado = true;
        }
    }

    /**
     *
     * @param e Evento
     */
    public void bReestablecer_actionPerformed(ActionEvent e) {
        if (this.imagenAbierta) {
            //Ponemos de nuevo la imagen
            this.sBrillo.setValue(0);

            //Copiamos de nuevo la imagen en modificada
            Graphics2D grImagenM = this.imagenTransformada.createGraphics();
            grImagenM.drawImage(this.imagenSinModificar, 0, 0, this.PanelTapiz);

            this.modificado = false;
            this.PanelTapiz.setImagen(this.imagenTransformada);

            //Borramos el vector de transformaciones
            this.transformaciones.removeAllElements();
        }
    }

    /**
     *
     * @param e Evento
     */
    public void Transformaciones_actionPerformed(ActionEvent e) {
        //Comprobamos que se ha cargado una imagen
        if (this.imagenAbierta) {
            int transf = ((JButton) e.getSource()).getMnemonic();

            Kernel kernel = new Kernel(3, 3, this.TRANSFORMACIONES[transf]);
            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

            //Añadimos al vector de transformaciones la operacion
            this.transformaciones.addElement(transf);

            BufferedImage aux = new BufferedImage(this.imagenSinModificar.getWidth(),
                    this.imagenSinModificar.getHeight(),
                    this.imagenSinModificar.getType());

            //Aplicamos la transformacion
            cop.filter(this.imagenTransformada, aux);
            this.imagenTransformada = aux;

            //Comprobamos si es una transformacion de deteccion de fronteras
            //en cuyo caso tenemos que repetir la convolucion pero ahora en vertical
            if (transf >= this.ROBERTS_H && transf <= this.FREICHEN_H) {
                Kernel kernel2 = new Kernel(3, 3,
                        this.TRANSFORMACIONES[transf + 1]);
                ConvolveOp cop2 = new ConvolveOp(kernel2, ConvolveOp.EDGE_NO_OP, null);
                BufferedImage aux2 = new BufferedImage(this.imagenSinModificar.getWidth(),
                        this.imagenSinModificar.getHeight(),
                        this.imagenSinModificar.getType());
                cop2.filter(this.imagenTransformada, aux2);
                this.imagenTransformada = aux2;
            }

            //Llamamos al brillo para que se le sume el brillo a la imagen dejando intacta
            //la de transformaciones
            sBrillo_stateChanged(new ChangeEvent(this));

            //Liberamos la memoria no utilizada
            System.gc();
            System.runFinalization();

            this.modificado = true;
        }
    }

    class EditorImagen_bSharpenAlto_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bSharpenAlto_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bSharpenBajo_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bSharpenBajo_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bPasoAlto_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bPasoAlto_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bLaplaciano_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bLaplaciano_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bFreiChen_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bFreiChen_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bSobel_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bSobel_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bmedia_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bmedia_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bpasobajo_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bpasobajo_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bRoberts_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bRoberts_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bPrewitt_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bPrewitt_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class Transformaciones_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        Transformaciones_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.Transformaciones_actionPerformed(e);
        }
    }

    class EditorImagen_bReestablecer_actionAdapter implements ActionListener {

        private EditorImagen adaptee;

        EditorImagen_bReestablecer_actionAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bReestablecer_actionPerformed(e);
        }
    }

    class EditorImagen_sBrillo_changeAdapter implements ChangeListener {

        private EditorImagen adaptee;

        EditorImagen_sBrillo_changeAdapter(EditorImagen adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.sBrillo_stateChanged(e);
        }
    }
}
