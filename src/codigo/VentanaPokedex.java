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

    //Foto principal y línea evolutiva
    BufferedImage buffer1, buffer2, buffer3, buffer4 = null;
    //Imagen del Pokemon
    Image imagen1 = null;
    //Contador que nos indica en que Pokemon estamos
    int contador = 1;

    int busquedaID = 0;//Guarda el valor que introduce el usuario para buscarlo

    Graphics2D g2, g3, g4, g5;

    //Para conectarnos con la base de datos
    Statement estado;
    ResultSet resultadoConsulta;
    Connection conexion;

    //estructura para guardar todo el contenido de la base de datos de golpe
    HashMap<String, Pokemon> listaPokemons = new HashMap();

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        //Para el pokemon que tiene que aparecer
        Graphics2D g2 = (Graphics2D) imagenPokemon2.getGraphics();
        g2.drawImage(buffer1, 0, 0, imagenPokemon2.getWidth(), imagenPokemon2.getHeight(), null);

        //Para la línea evolutiva
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

        primeraPantalla();

        conectarBBDD();

    }

    //Nos conectamos con la Base de datos
    private void conectarBBDD() {
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

    //Prepara la primera pantalla
    private void primeraPantalla() {
        //Título e icono
        setTitle("Pokedex");
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/pokemoniconpng.png")).getImage());

        try {
            imagen1 = ImageIO.read(getClass().getResource("/imagenes/black-white.png"));
        } catch (IOException ex) {

        }

        inicializaBuffers();

        //Ponemos los pokemon por defecto
        dibujaPokemonPosicion(0, buffer1, imagenPokemon2);
        dibujaPokemonPosicion(0, buffer2, evolucion1);
        dibujaPokemonPosicion(1, buffer3, evolucion2);
        dibujaPokemonPosicion(2, buffer4, evolucion3);

        //Ponemos la primera descripción
        descripcion.setText("Una rara semilla fue plantada en su espalda al nacer. La planta brota y crece con este Pokémon.");

        //Ponemos logo Pokemon
        ImageIcon miImagen = new ImageIcon((new ImageIcon(getClass().getResource("/imagenes/pokemon.png"))
                .getImage()
                .getScaledInstance(346, 71, Image.SCALE_SMOOTH)));

        jLabel1.setIcon(miImagen);

        jButton1.setEnabled(false);
    }

    //Se encarga de inicializar los buffers
    private void inicializaBuffers() {
        buffer1 = (BufferedImage) imagenPokemon2.createImage(imagenPokemon2.getWidth(), imagenPokemon2.getHeight());

        g2 = buffer1.createGraphics();

        buffer2 = (BufferedImage) evolucion1.createImage(evolucion1.getWidth(), evolucion1.getHeight());

        g3 = buffer2.createGraphics();

        buffer3 = (BufferedImage) evolucion2.createImage(evolucion2.getWidth(), evolucion2.getHeight());

        g4 = buffer3.createGraphics();

        buffer4 = (BufferedImage) evolucion3.createImage(evolucion3.getWidth(), evolucion3.getHeight());

        g5 = buffer3.createGraphics();
    }

    //Pone el Pokemon que correspondiente en pantalla
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

    //cada vez que se cambia de Pokemon, borra las fotos de la línea evolutiva
    private void limpiarCuadrosEvoluciones(BufferedImage _buffer, JPanel _imagen) {
        Graphics2D g2 = (Graphics2D) _buffer.getGraphics();

        g2.setColor(Color.GRAY); //Pinta el fondo del Jpanel negro

        g2.fillRect(0, 0, _imagen.getWidth(), _imagen.getHeight());

        g2.drawImage(_buffer, 0, 0, null);
    }

    //Pinta en pantalla las líneas evolutivas
    private void pintarEvoluciones(Pokemon _p) {
        if (_p.preEvolucion == null || _p.preEvolucion.equals("")) {//si ocupa la primera casilla

            dibujaPokemonPosicion((contador - 1), buffer2, evolucion1);
            if (_p.posEvolucion != null && !_p.posEvolucion.equals("")) {//Si tiene una segunta evolución

                dibujaPokemonPosicion((Integer.parseInt(_p.posEvolucion) - 1), buffer3, evolucion2);
                _p = listaPokemons.get(String.valueOf(_p.posEvolucion));

                if (_p.posEvolucion != null && !_p.posEvolucion.equals("")) {//Si tiene una tercera evolución
                    dibujaPokemonPosicion((Integer.parseInt(_p.posEvolucion) - 1), buffer4, evolucion3);
                }
            }
        } else if (_p.posEvolucion == null || _p.posEvolucion.equals("")) {//Si es el útimo de su linea evolutiva
            _p = listaPokemons.get(String.valueOf(_p.preEvolucion));
            if (_p.preEvolucion != null && !_p.preEvolucion.equals("")) {//Si tiene dos pokemons anteriores a él en su linea evolutiva
                dibujaPokemonPosicion((contador - 2), buffer3, evolucion2);//Pintamos el pokemon del centro
                dibujaPokemonPosicion((contador - 1), buffer4, evolucion3);//Pintamos el pokemon de la derecha
                dibujaPokemonPosicion((contador - 3), buffer2, evolucion2);//Pinamos el pokemon de la izquierda
            } else {//si tiene un pokemon anterior a él en su línea evolutiva
                dibujaPokemonPosicion((contador - 1), buffer3, evolucion2);//Pintamos el pokemon del centro
                dibujaPokemonPosicion((contador - 2), buffer2, evolucion2);//Pinamos el pokemon de la izquierda
            }
        } else {//Si es el segundo pokemon de su línea evolutiva y tiene tanto posEvolución como preEvolución
            dibujaPokemonPosicion((contador - 1), buffer3, evolucion2);//Pintamos el pokemon del centro
            dibujaPokemonPosicion((contador), buffer4, evolucion3);//Pintamos el pokemon de la derecha
            dibujaPokemonPosicion((contador - 2), buffer2, evolucion2);//Pinamos el pokemon de la izquierda
        }
    }

    //Cambia los datos de la Pokedex al siguiente Pokemon o al anterior
    private void apretarBoton() {

        limpiarCuadrosEvoluciones(buffer2, evolucion1);
        limpiarCuadrosEvoluciones(buffer3, evolucion2);
        limpiarCuadrosEvoluciones(buffer4, evolucion3);
        repaint();

        dibujaPokemonPosicion(contador - 1, buffer1, imagenPokemon2);

        //obtenemos los datos del Pokemon correspondiente a través del Hassmap
        Pokemon p = listaPokemons.get(String.valueOf(contador));

        if (p != null) {//Ponemos todos los datos en pantalla
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

            pintarEvoluciones(p);

        } else {//En caso de error lo señalarmeos
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

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jDialog2 = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jDialog3 = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
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
        jLabel2 = new javax.swing.JLabel();
        buscaid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        jDialog1.setSize(new java.awt.Dimension(400, 200));

        jLabel4.setText("Hay que introducir un número");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );

        jDialog2.setSize(new java.awt.Dimension(400, 200));

        jLabel5.setText("Número demasiado alto");

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jDialog3.setSize(new java.awt.Dimension(400, 200));

        jLabel6.setText("Número demasiado bajo");

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel6)
                .addContainerGap(250, Short.MAX_VALUE))
        );

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

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Linea evolutiva");

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ID:");
        jLabel3.setOpaque(true);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imagenPokemon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscaid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(evolucion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(evolucion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(evolucion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(194, 194, 194))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(345, 345, 345))))
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
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscaid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(evolucion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(evolucion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(evolucion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(484, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Botón para pasar al siguiente Pokemon
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        jButton1.setEnabled(true);
        contador++;
        if (contador >= 151) {
            contador = 151;
            jButton2.setEnabled(false);
        }

        apretarBoton();
    }//GEN-LAST:event_jButton2ActionPerformed

    //Botón para volver al anterior Pokemon
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jButton2.setEnabled(true);
        contador--;
        if (contador <= 1) {
            contador = 1;
            jButton1.setEnabled(false);
        }

        apretarBoton();

    }//GEN-LAST:event_jButton1ActionPerformed

    //Botón para buscar por ID
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            if (!buscaid.getText().isEmpty()) {//Evitamos error si no hay ningún texto en el jtextfield
                busquedaID = Integer.parseInt(buscaid.getText());
                if(busquedaID>0 && busquedaID<=151){
                    jButton1.setEnabled(true);
                    jButton2.setEnabled(true);
                    contador=busquedaID;
                    apretarBoton();
                    if(contador==1){
                        jButton1.setEnabled(false);
                    }
                    if(contador==151){
                        jButton2.setEnabled(false);
                    }
                    
                }else if(busquedaID<=0){
                    jDialog3.setVisible(true);
                }else{
                    jDialog2.setVisible(true);
                }

            }
        } catch (Exception e) {
            jDialog1.setVisible(true);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.JTextField buscaid;
    private javax.swing.JTextPane descripcion;
    private javax.swing.JLabel especie;
    private javax.swing.JPanel evolucion1;
    private javax.swing.JPanel evolucion2;
    private javax.swing.JPanel evolucion3;
    private javax.swing.JLabel habilidad;
    private javax.swing.JLabel habitat;
    private javax.swing.JPanel imagenPokemon2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
