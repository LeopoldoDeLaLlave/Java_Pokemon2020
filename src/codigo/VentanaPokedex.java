/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.sql.Statement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Javier
 */
public class VentanaPokedex extends javax.swing.JFrame {

    BufferedImage buffer1 = null;
    Image imagen1 = null;
    int contador = 0;
    
    Graphics2D g2, jpanelGraphics;

    Statement estado;
    ResultSet resultadoConsulta;
    Connection conexion;

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) imagenPokemon2.getGraphics();
        g2.drawImage(buffer1, 0, 0, imagenPokemon2.getWidth(), imagenPokemon2.getHeight(), null);

    }

    /**
     * Creates new form VentanaPokedex
     */
    public VentanaPokedex() {
        initComponents();
        try {
            imagen1 = ImageIO.read(getClass().getResource("/imagenes/black-white.png"));
        } catch (IOException ex) {

        }

        buffer1 = (BufferedImage) imagenPokemon2.createImage(imagenPokemon2.getWidth(), imagenPokemon2.getHeight());
        
        g2 = buffer1.createGraphics();
        
        g2.setColor(Color.red);
        g2.fillRect(0, 0, jPanel1.getWidth(), jPanel1.getHeight());
        
        jpanelGraphics = (Graphics2D) jPanel1.getGraphics();

        dibujaPokemonPosicion(0);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.182.136/test", "root", "");
            estado = conexion.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void dibujaPokemonPosicion(int posicion) {
        int fila = posicion / 31;
        int columna = posicion % 31;
        Graphics2D g2 = (Graphics2D) buffer1.getGraphics();
        g2.setColor(Color.BLACK); //Pinta el fondo del Jpanel negro
        g2.fillRect(0, 0, imagenPokemon2.getWidth(), imagenPokemon2.getHeight());
        g2.drawImage(imagen1,
                0//Posicion x inicial dentro del jpanel
                ,
                 0//Posicion y inicial dentro del jpanel
                ,
                 imagenPokemon2.getWidth()//Ancho Jpanel
                ,
                 imagenPokemon2.getHeight()//LArgo jpanel
                ,
                 columna * 96//Posicion x inicial dentro de la imagen
                ,
                 fila * 96//Posicion x inicial dentro de la imagen
                ,
                 columna * 96 + 96//Posicion final x
                ,
                 fila * 96 + 96//Posicion final x
                ,
                 null);

        repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        imagenPokemon2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        nombrePokemon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout imagenPokemon2Layout = new javax.swing.GroupLayout(imagenPokemon2);
        imagenPokemon2.setLayout(imagenPokemon2Layout);
        imagenPokemon2Layout.setHorizontalGroup(
            imagenPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 144, Short.MAX_VALUE)
        );
        imagenPokemon2Layout.setVerticalGroup(
            imagenPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 144, Short.MAX_VALUE)
        );

        jButton1.setText("<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(">");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(imagenPokemon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombrePokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(458, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagenPokemon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrePokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(428, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        dibujaPokemonPosicion(contador);
        try {
            resultadoConsulta = estado.executeQuery("select * from pokemon where id=" + (contador + 1));
            if (resultadoConsulta.next()) {
                nombrePokemon.setText(resultadoConsulta.getString(2));
            } else {

            }
        } catch (SQLException ex) {
            nombrePokemon.setText("Este Pokemon no figura en la Pokedex");
        }
        contador++;
        if (contador >= 649) {
            contador = 649;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dibujaPokemonPosicion(contador);
        try {
            resultadoConsulta = estado.executeQuery("select * from pokemon where id=" + (contador + 1));
            if (resultadoConsulta.next()) {
                nombrePokemon.setText(resultadoConsulta.getString(2));
            } else {

            }
        } catch (SQLException ex) {
            nombrePokemon.setText("Este Pokemon no figura en la Pokedex");
        }
        contador--;
        if (contador <= 0) {
            contador = 0;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPokedex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPokedex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel imagenPokemon;
    private javax.swing.JPanel imagenPokemon1;
    private javax.swing.JPanel imagenPokemon2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nombrePokemon;
    // End of variables declaration//GEN-END:variables
}
