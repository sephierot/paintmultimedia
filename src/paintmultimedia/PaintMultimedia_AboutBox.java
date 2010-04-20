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
public class PaintMultimedia_AboutBox extends JDialog implements ActionListener {

    String dirImagenes = "/imagenes/";
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JButton button1 = new JButton();
    JLabel imageLabel = new JLabel();
    ImageIcon image1 = new ImageIcon();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    String linea = "  \u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015  \n";
    String product = "  |                PaintMultimedia v1.0            |\n";
    String copyright =
            "  |    Copyright (C) 2006 GNU GPL V3    |\n  |http://www.gnu.org/licenses/gpl.txt|\n";
    String comments1 =
            "   Aplicación que permite realizar dibujos,\n   editándolos como objetos, ";
    String comments2 =
            "mostrar y \n   aplicar filtros a imágenes de diferentes\n   formatos, ";
    String comments3 =
            "guardándolas con formatos \n   *.png y *.jpeg.\n\n   Reproduce sonido con ";
    String comments4 = "formatos *.au,\n   *.wav,*.mp3 etc. Además reproduce\n   vídeo con formato *.mpeg,*.avi y otras";
    String comments5 =
            "\n   extensiones.\n\n   Realizado por:\n\tDavid Armenteros Escabias.";
    JTextArea jTextArea1 = new JTextArea();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel1 = new JLabel();

    /**
     *
     * @param parent
     */
    public PaintMultimedia_AboutBox(Frame parent) {
        super(parent);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        this.setPreferredSize(new Dimension(570, 420));
        image1 = new ImageIcon(paintmultimedia.PaintMultimedia.class.getResource(
                dirImagenes + "PaintMultimedia.png"));
        jTextArea1.setText(this.linea + this.product
                + this.copyright + this.linea + this.comments1
                + this.comments2 + this.comments3 + this.comments4
                + this.comments5);
        imageLabel.setBorder(null);
        imageLabel.setPreferredSize(new Dimension(240, 256));
        imageLabel.setIcon(image1);
        setTitle("Acerca de PaintMultimedia");
        panel1.setLayout(borderLayout1);
        panel2.setLayout(borderLayout2);
        button1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        button1.setText("Aceptar");
        button1.addActionListener(this);
        jTextArea1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        jTextArea1.setBorder(BorderFactory.createRaisedBevelBorder());
        jTextArea1.setOpaque(false);
        jTextArea1.setPreferredSize(new Dimension(300, 335));
        jTextArea1.setEditable(false);
        jPanel1.setPreferredSize(new Dimension(140, 28));
        borderLayout2.setHgap(10);
        jPanel2.setPreferredSize(new Dimension(5, 10));
        jPanel3.setPreferredSize(new Dimension(5, 5));
        jLabel1.setText("jLabel1");
        insetsPanel1.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().add(panel1, null);
        insetsPanel1.add(button1, null);
        panel1.add(insetsPanel1, BorderLayout.SOUTH);
        panel1.add(panel2, java.awt.BorderLayout.CENTER);
        jPanel1.add(jTextArea1);
        panel2.add(jPanel2, java.awt.BorderLayout.EAST);
        panel2.add(jPanel1, java.awt.BorderLayout.CENTER);
        panel2.add(imageLabel, java.awt.BorderLayout.WEST);
        panel2.add(jPanel3, java.awt.BorderLayout.NORTH);
        setResizable(false);
    }

    /**
     * Close the dialog on a button event.
     *
     * @param actionEvent ActionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == button1) {
            dispose();
        }
    }
}
