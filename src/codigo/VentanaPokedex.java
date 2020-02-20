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
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Javier
 */
public class VentanaPokedex extends javax.swing.JFrame {

    BufferedImage buffer1, buffer2, buffer3, buffer4 = null;
    Image imagen1 = null;
    int contador = 1;

    Graphics2D g2, g3, g4, g5;

    Statement estado;
    ResultSet resultadoConsulta;
    Connection conexion;

    //estructura para guardar todo el contenido de la base de datos de golpe
    HashMap<String, Pokemon> listaPokemons = new HashMap();

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) imagenPokemon2.getGraphics();
        g2.drawImage(buffer1, 0, 0, imagenPokemon2.getWidth(), imagenPokemon2.getHeight(), null);

        Graphics2D g3 = (Graphics2D) evolucion1.getGraphics();
        g3.drawImage(buffer2, 0, 0, evolucion1.getWidth(), evolucion1.getHeight(), null);

        Graphics2D g4 = (Graphics2D) evolucion2.getGraphics();
        g4.drawImage(buffer3, 0, 0, evolucion2.getWidth(), evolucion2.getHeight(), null);

        Graphics2D g5 = (Graphics2D) evolucion3.getGraphics();
        g5.drawImage(buffer4, 0, 0, evolucion3.getWidth(), evolucion3.getHeight(), null);

    }

    /**
     * Creates new form VentanaPokedex
     */
    public VentanaPokedex() {
        initComponents();
        this.setLocationRelativeTo(null);
        //Título e icono
        setTitle("Pokedex");
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/pokemoniconpng.png")).getImage());
        try {
            imagen1 = ImageIO.read(getClass().getResource("/imagenes/black-white.png"));
        } catch (IOException ex) {

        }

        buffer1 = (BufferedImage) imagenPokemon2.createImage(imagenPokemon2.getWidth(), imagenPokemon2.getHeight());

        g2 = buffer1.createGraphics();

        buffer2 = (BufferedImage) evolucion1.createImage(evolucion1.getWidth(), evolucion1.getHeight());

        g3 = buffer2.createGraphics();

        buffer3 = (BufferedImage) evolucion2.createImage(evolucion2.getWidth(), evolucion2.getHeight());

        g4 = buffer3.createGraphics();

        buffer4 = (BufferedImage) evolucion3.createImage(evolucion3.getWidth(), evolucion3.getHeight());

        g5 = buffer3.createGraphics();

        //Ponemos el primer Pokemon
        dibujaPokemonPosicion(0, buffer1, imagenPokemon2);

        //Ponemos la primera descripción
        descripcion.setText("Una rara semilla fue plantada en su espalda al nacer. La planta brota y crece con este Pokémon.");

        //Ponemos logo Pokemon
        ImageIcon miImagen = new ImageIcon((new ImageIcon(getClass().getResource("/imagenes/pokemon.png"))
                .getImage()
                .getScaledInstance(346, 71, Image.SCALE_SMOOTH)));

        jLabel1.setIcon(miImagen);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.182.136/test", "root", "");
            estado = conexion.createStatement();
            resultadoConsulta = estado.executeQuery("Select * from pokemon");

            //Recorremos el array del resultado uno a uno
            //para ir cat¡rgando al hashmap
            while (resultadoConsulta.next()) {
                Pokemon p = new Pokemon();
                p.nombre = resultadoConsulta.getString("nombre");
                p.especie = resultadoConsulta.getString("especie");
                p.habitat = resultadoConsulta.getString("habitat");
                p.tipo1 = resultadoConsulta.getString("tipo1");
                p.tipo2 = resultadoConsulta.getString("tipo2");
                p.habilidad = resultadoConsulta.getString("habilidad");
                p.movimiento1 = resultadoConsulta.getString("movimiento1");
                p.movimiento2 = resultadoConsulta.getString("movimiento2");
                p.movimiento3 = resultadoConsulta.getString("movimiento3");
                p.movimiento4 = resultadoConsulta.getString("movimiento4");
                p.peso = resultadoConsulta.getString("peso");
                p.altura = resultadoConsulta.getString("altura");
                p.preEvolucion = resultadoConsulta.getString("preEvolucion");
                p.posEvolucion = resultadoConsulta.getString("posEvolucion");
                p.descripcion = resultadoConsulta.getString("descripcion");
                listaPokemons.put(resultadoConsulta.getString("id"), p);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void dibujaPokemonPosicion(int posicion, BufferedImage _buffer, JPanel _panel) {
        int fila = posicion / 31;
        int columna = posicion % 31;
        Graphics2D g2 = (Graphics2D) _buffer.getGraphics();
        if (_buffer == buffer1) {
            g2.setColor(Color.BLACK); //Pinta el fondo del Jpanel negro
        } else {
            g2.setColor(Color.GRAY); //Pinta el fondo del Jpanel negro
        }
        g2.fillRect(0, 0, _panel.getWidth(), _panel.getHeight());
        g2.drawImage(imagen1,
                0//Posicion x inicial dentro del jpanel
                ,
                 0//Posicion y inicial dentro del jpanel
                ,
                 _panel.getWidth()//Ancho Jpanel
                ,
                 _panel.getHeight()//LArgo jpanel
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

    public void evoluciones() {

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
        jPanel2 = new javax.swing.JPanel();
        nombrePokemon = new javax.swing.JLabel();
        labelid = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descripcion = new javax.swing.JTextPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tipo1 = new javax.swing.JLabel();
        tipo2 = new javax.swing.JLabel();
        altura = new javax.swing.JLabel();
        peso = new javax.swing.JLabel();
        habilidad = new javax.swing.JLabel();
        especie = new javax.swing.JLabel();
        habitat = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        ataque1 = new javax.swing.JLabel();
        ataque2 = new javax.swing.JLabel();
        ataque4 = new javax.swing.JLabel();
        ataque3 = new javax.swing.JLabel();
        evolucion2 = new javax.swing.JPanel();
        evolucion3 = new javax.swing.JPanel();
        evolucion1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1110, 900));
        setMinimumSize(new java.awt.Dimension(1110, 900));
        setPreferredSize(new java.awt.Dimension(1110, 900));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));

        imagenPokemon2.setPreferredSize(new java.awt.Dimension(250, 250));

        javax.swing.GroupLayout imagenPokemon2Layout = new javax.swing.GroupLayout(imagenPokemon2);
        imagenPokemon2.setLayout(imagenPokemon2Layout);
        imagenPokemon2Layout.setHorizontalGroup(
            imagenPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        imagenPokemon2Layout.setVerticalGroup(
            imagenPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/f1.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/f2.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        nombrePokemon.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 36)); // NOI18N
        nombrePokemon.setText("Bulbasaur");

        labelid.setFont(new java.awt.Font("Microsoft JhengHei UI", 3, 36)); // NOI18N
        labelid.setText("Nº: 001");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(nombrePokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelid, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelid)
                    .addComponent(nombrePokemon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        descripcion.setEditable(false);
        descripcion.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        descripcion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        descripcion.setEnabled(false);
        jScrollPane2.setViewportView(descripcion);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Tipo:");
        jLabel13.setOpaque(true);
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 119, 30));

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Altura:");
        jLabel15.setOpaque(true);
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 119, 30));

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Peso:");
        jLabel16.setOpaque(true);
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 119, 30));

        jLabel17.setBackground(new java.awt.Color(204, 204, 204));
        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Habilidad:");
        jLabel17.setOpaque(true);
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 119, 40));

        jLabel18.setBackground(new java.awt.Color(204, 204, 204));
        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Especie:");
        jLabel18.setOpaque(true);
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 119, 30));

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Habitat:");
        jLabel19.setOpaque(true);
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 119, 30));

        tipo1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tipo1.setForeground(new java.awt.Color(0, 0, 0));
        tipo1.setText("Planta");
        jPanel6.add(tipo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, -1));

        tipo2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tipo2.setForeground(new java.awt.Color(0, 0, 0));
        tipo2.setText("Veneno");
        jPanel6.add(tipo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, -1));

        altura.setBackground(new java.awt.Color(255, 255, 255));
        altura.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        altura.setForeground(new java.awt.Color(0, 0, 0));
        altura.setText("0,7 m");
        jPanel6.add(altura, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 80, 30));

        peso.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        peso.setForeground(new java.awt.Color(0, 0, 0));
        peso.setText("6'9 kg");
        jPanel6.add(peso, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 110, -1));

        habilidad.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        habilidad.setForeground(new java.awt.Color(0, 0, 0));
        habilidad.setText("Espesura");
        jPanel6.add(habilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 170, -1));

        especie.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        especie.setForeground(new java.awt.Color(0, 0, 0));
        especie.setText("Semilla");
        jPanel6.add(especie, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 186, 170, -1));

        habitat.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        habitat.setForeground(new java.awt.Color(0, 0, 0));
        habitat.setText("Pradera");
        jPanel6.add(habitat, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 150, 30));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel20.setBackground(new java.awt.Color(204, 204, 204));
        jLabel20.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Movimientos");
        jLabel20.setOpaque(true);

        ataque1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ataque1.setForeground(new java.awt.Color(0, 0, 0));
        ataque1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ataque1.setText("Placaje");

        ataque2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ataque2.setForeground(new java.awt.Color(0, 0, 0));
        ataque2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ataque2.setText("Gruñido");

        ataque4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ataque4.setForeground(new java.awt.Color(0, 0, 0));
        ataque4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ataque4.setText("Latigo cepa");

        ataque3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ataque3.setForeground(new java.awt.Color(0, 0, 0));
        ataque3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ataque3.setText("Drenadoras");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ataque4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ataque1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ataque2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ataque3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(29, 29, 29)
                .addComponent(ataque1)
                .addGap(18, 18, 18)
                .addComponent(ataque2)
                .addGap(18, 18, 18)
                .addComponent(ataque3)
                .addGap(18, 18, 18)
                .addComponent(ataque4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout evolucion2Layout = new javax.swing.GroupLayout(evolucion2);
        evolucion2.setLayout(evolucion2Layout);
        evolucion2Layout.setHorizontalGroup(
            evolucion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        evolucion2Layout.setVerticalGroup(
            evolucion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout evolucion3Layout = new javax.swing.GroupLayout(evolucion3);
        evolucion3.setLayout(evolucion3Layout);
        evolucion3Layout.setHorizontalGroup(
            evolucion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        evolucion3Layout.setVerticalGroup(
            evolucion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout evolucion1Layout = new javax.swing.GroupLayout(evolucion1);
        evolucion1.setLayout(evolucion1Layout);
        evolucion1Layout.setHorizontalGroup(
            evolucion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        evolucion1Layout.setVerticalGroup(
            evolucion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(imagenPokemon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(evolucion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(evolucion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(evolucion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagenPokemon2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(evolucion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(evolucion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(evolucion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(519, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Botón para pasar al siguiente Pokemon
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        contador++;
        if (contador > 151) {
            contador = 151;
        }

        
        dibujaPokemonPosicion(contador - 1, buffer1, imagenPokemon2);
        Pokemon p = listaPokemons.get(String.valueOf(contador));
        if (p != null) {
            if (contador < 10) {//Ponemos el ID
                labelid.setText("Nº: 00" + (contador));
            } else if (contador < 100) {
                labelid.setText("Nº: 0" + (contador));
            } else {
                labelid.setText("Nº: " + (contador));
            }
            nombrePokemon.setText(p.nombre);
            altura.setText(p.altura + " m");
            peso.setText(p.peso + " kg");
            habitat.setText(p.habitat);
            especie.setText(p.especie);
            tipo1.setText(p.tipo1);
            tipo2.setText(p.tipo2);
            habilidad.setText(p.habilidad);
            ataque1.setText(p.movimiento1);
            ataque2.setText(p.movimiento2);
            ataque3.setText(p.movimiento3);
            ataque4.setText(p.movimiento4);
            descripcion.setText(p.descripcion);

            if (p.preEvolucion == null || p.preEvolucion.equals("")) {//si ocupa la primera casilla

                dibujaPokemonPosicion((contador - 1), buffer2, evolucion1);
                if (p.posEvolucion != null || !p.posEvolucion.equals("")) {
                    dibujaPokemonPosicion((Integer.parseInt(p.posEvolucion) - 1), buffer3, evolucion2);
                    p = listaPokemons.get(String.valueOf(p.posEvolucion));
                    if (p.posEvolucion != null && !p.posEvolucion.equals("")) {
                        dibujaPokemonPosicion((Integer.parseInt(p.posEvolucion) - 1), buffer4, evolucion3);
                    }

                }
            } else if (p.posEvolucion == null || p.posEvolucion.equals("")) {//Si es el útimo de su linea evolutiva
                p = listaPokemons.get(String.valueOf(p.preEvolucion));
                if (p.preEvolucion != null && !p.preEvolucion.equals("")) {//Si tiene dos pokemons anteriores a él en su linea evolutiva
                    dibujaPokemonPosicion((contador - 2), buffer3, evolucion2);//Pintamos el pokemon del centro
                    dibujaPokemonPosicion((contador - 1), buffer4, evolucion3);//Pintamos el pokemon de la derecha
                    dibujaPokemonPosicion((contador - 3), buffer2, evolucion2);//Pinamos el pokemon de la izquierda
                } else {//si tiene un pokemon anterior a él en su línea evolutiva
                    dibujaPokemonPosicion((contador - 1), buffer3, evolucion2);//Pintamos el pokemon del centro
                    dibujaPokemonPosicion((contador - 2), buffer2, evolucion2);//Pinamos el pokemon de la izquierda
                }
            } else {//Si es el segundo pokemon de su línea evolutiva
                dibujaPokemonPosicion((contador - 1), buffer3, evolucion2);//Pintamos el pokemon del centro
                dibujaPokemonPosicion((contador), buffer4, evolucion3);//Pintamos el pokemon de la derecha
                dibujaPokemonPosicion((contador - 2), buffer2, evolucion2);//Pinamos el pokemon de la izquierda
            }

        } else {
            labelid.setText("Nº: Error");
            nombrePokemon.setText("Error");
            altura.setText("Error");
            peso.setText("Error");
            habitat.setText("Error");
            especie.setText("Error");
            tipo1.setText("Error");
            tipo2.setText("Error");
            habilidad.setText("Error");
            ataque1.setText("Error");
            ataque2.setText("Error");
            ataque3.setText("Error");
            ataque4.setText("Error");
            descripcion.setText("Error");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    //Botón para volver al anterior Pokemon
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        contador--;
        if (contador <= 1) {
            contador = 1;
        }
        dibujaPokemonPosicion(contador - 1, buffer1, imagenPokemon2);

        Pokemon p = listaPokemons.get(String.valueOf(contador));
        if (p != null) {
            if (contador < 10) {//Ponemos el ID
                labelid.setText("Nº: 00" + (contador));
            } else if (contador < 100) {
                labelid.setText("Nº: 0" + (contador));
            } else {
                labelid.setText("Nº: " + (contador));
            }
            nombrePokemon.setText(p.nombre);
            altura.setText(p.altura + " m");
            peso.setText(p.peso + " kg");
            habitat.setText(p.habitat);
            especie.setText(p.especie);
            tipo1.setText(p.tipo1);
            tipo2.setText(p.tipo2);
            habilidad.setText(p.habilidad);
            ataque1.setText(p.movimiento1);
            ataque2.setText(p.movimiento2);
            ataque3.setText(p.movimiento3);
            ataque4.setText(p.movimiento4);
            descripcion.setText(p.descripcion);
        } else {
            labelid.setText("Nº: Error");
            nombrePokemon.setText("Error");
            altura.setText("Error");
            peso.setText("Error");
            habitat.setText("Error");
            especie.setText("Error");
            tipo1.setText("Error");
            tipo2.setText("Error");
            habilidad.setText("Error");
            ataque1.setText("Error");
            ataque2.setText("Error");
            ataque3.setText("Error");
            ataque4.setText("Error");
            descripcion.setText("Error");
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
    private javax.swing.JLabel altura;
    private javax.swing.JLabel ataque1;
    private javax.swing.JLabel ataque2;
    private javax.swing.JLabel ataque3;
    private javax.swing.JLabel ataque4;
    private javax.swing.JTextPane descripcion;
    private javax.swing.JLabel especie;
    private javax.swing.JPanel evolucion1;
    private javax.swing.JPanel evolucion2;
    private javax.swing.JPanel evolucion3;
    private javax.swing.JLabel habilidad;
    private javax.swing.JLabel habitat;
    private javax.swing.JPanel imagenPokemon2;
    private javax.swing.JPanel imagenPokemon4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelid;
    private javax.swing.JLabel nombrePokemon;
    private javax.swing.JLabel peso;
    private javax.swing.JLabel tipo1;
    private javax.swing.JLabel tipo2;
    // End of variables declaration//GEN-END:variables
}
