package org.example; // Define el paquete del proyecto

import javax.swing.*; // Importa componentes graficos basicos
import javax.swing.border.LineBorder; // Importa estilos para bordes
import java.awt.*; // Importa herramientas de diseño y colores
import java.io.File; // Importa manejo de archivos
import java.util.Random; // Importa generador de numeros aleatorios

public class ReproductorGUI {
    private ReproductorMP3 reproductor; // Referencia al motor de audio
    private JFrame frame; // Ventana principal
    private JLabel estadoLabel; // Texto que muestra que esta pasando
    private JList<Cancion> listaCanciones; // Lista visual de temas
    private DefaultListModel<Cancion> listModel; // Almacen de datos de la lista
    private JProgressBar barraProgreso; // Barra que se llena con la musica
    private JSlider sliderVolumen; // Deslizador para el volumen
    private JPanel visualizerPanel; // Panel para las barritas que saltan
    private boolean esAleatorio = false; // Variable para el modo Shuffle
    private Random random = new Random(); // Objeto para generar el azar

    private Color colorFondo = new Color(15, 15, 15); // Color gris casi negro
    private Color colorPanel = new Color(30, 30, 30); // Color gris oscuro para paneles
    private Color colorAcento = new Color(0, 255, 180); // Color verde neon

    public ReproductorGUI() { // Constructor de la interfaz
        this.reproductor = new ReproductorMP3(); // Inicializa el motor de audio
        inicializarInterfaz(); // Llama a la creacion de la ventana
        configurarTimer(); // Inicia el reloj de actualizacion automatica
    }

    private void inicializarInterfaz() { // Crea todos los botones y paneles
        frame = new JFrame("Reproductor MP3 v2.3"); // Crea la ventana con titulo
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al salir
        frame.setSize(600, 600); // Define tamaño 600x600 pixels
        frame.getContentPane().setBackground(colorFondo); // Pone el fondo oscuro
        frame.setLayout(null); // Desactiva diseño automatico para usar coordenadas
        frame.setResizable(false); // Evita que el usuario cambie el tamaño

        visualizerPanel = new JPanel() { // Crea el panel del visualizador
            @Override
            protected void paintComponent(Graphics g) { // Metodo que dibuja las barras
                super.paintComponent(g); // Limpia el dibujo anterior
                if (reproductor.estaReproduciendo()) { // Si hay musica, dibuja
                    g.setColor(colorAcento); // Usa el color neon
                    for (int i = 0; i < 22; i++) { // Dibuja 22 barritas
                        int h = random.nextInt(40) + 5; // Calcula altura al azar
                        g.fillRect(i * 25 + 10, 50 - h, 18, h); // Dibuja el rectangulo
                    }
                }
            }
        };
        visualizerPanel.setBounds(20, 20, 545, 60); // Posicion del visualizador
        visualizerPanel.setBackground(colorFondo); // Fondo igual a la ventana
        frame.add(visualizerPanel); // Agrega el panel a la ventana

        listModel = new DefaultListModel<>(); // Inicializa el modelo de la lista
        listaCanciones = new JList<>(listModel); // Crea el componente de lista
        listaCanciones.setBackground(colorPanel); // Fondo oscuro para la lista
        listaCanciones.setForeground(Color.WHITE); // Letras blancas
        listaCanciones.setSelectionBackground(colorAcento); // Color de seleccion neon
        listaCanciones.setSelectionForeground(Color.BLACK); // Letra negra al seleccionar
        listaCanciones.setFont(new Font("Dialog", Font.PLAIN, 14)); // Fuente compatible
        listaCanciones.setFixedCellHeight(35); // Altura de cada fila

        JScrollPane scroll = new JScrollPane(listaCanciones); // Agrega barras de desplazamiento
        scroll.setBounds(20, 100, 545, 200); // Posicion de la lista
        scroll.setBorder(new LineBorder(colorAcento, 1)); // Borde neon fino
        frame.add(scroll); // Agrega el scroll a la ventana

        barraProgreso = new JProgressBar(0, 100); // Barra de 0 a 100%
        barraProgreso.setBounds(20, 315, 545, 6); // Posicion de la barra
        barraProgreso.setBackground(colorPanel); // Fondo de la barra
        barraProgreso.setForeground(colorAcento); // Relleno de la barra neon
        barraProgreso.setBorderPainted(false); // Quita bordes feos
        frame.add(barraProgreso); // Agrega la barra

        int yB = 345; // Variable para alinear botones en el mismo eje Y
        crearBotonControl("⏮", 20, yB, 55, e -> cambiarCancion(-1)); // Boton Anterior
        crearBotonControl("▶", 80, yB, 70, e -> reproducirSeleccionada()); // Boton Play
        crearBotonControl("⏸", 155, yB, 70, e -> { // Boton Pausa
            reproductor.pausar(); // Pausa el audio
            estadoLabel.setText("ESTADO: PAUSADO"); // Avisa en pantalla
        });
        crearBotonControl("⏯", 230, yB, 70, e -> { // Boton Seguir
            reproductor.reanudar(); // Reanuda el audio
            estadoLabel.setText("ESTADO: REPRODUCIENDO"); // Avisa en pantalla
        });
        crearBotonControl("⏭", 305, yB, 55, e -> cambiarCancion(1)); // Boton Siguiente

        crearBotonControl("SHUF", 370, yB, 70, e -> { // Boton Aleatorio
            esAleatorio = !esAleatorio; // Cambia entre verdadero y falso
            estadoLabel.setText("SHUFFLE: " + (esAleatorio ? "ON" : "OFF")); // Avisa el estado
        });

        crearBotonControl("⏹", 445, yB, 55, e -> { // Boton Detener
            reproductor.detener(); // Para todo
            barraProgreso.setValue(0); // Baja la barra a cero
        });

        crearBotonControl("➕", 510, yB, 55, e -> seleccionarArchivos()); // Boton Añadir

        sliderVolumen = new JSlider(0, 100, 80); // Slider de volumen 0 a 100
        sliderVolumen.setBounds(20, 410, 200, 30); // Posicion del slider
        sliderVolumen.setBackground(colorFondo); // Fondo oscuro
        sliderVolumen.addChangeListener(e -> reproductor.setVolumen(sliderVolumen.getValue() / 100f)); // Cambia volumen al mover
        frame.add(sliderVolumen); // Agrega slider

        estadoLabel = new JLabel("LISTO"); // Etiqueta inferior
        estadoLabel.setBounds(20, 500, 545, 30); // Posicion del texto
        estadoLabel.setForeground(colorAcento); // Color neon
        estadoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
        estadoLabel.setFont(new Font("Monospaced", Font.BOLD, 13)); // Fuente tipo programador
        frame.add(estadoLabel); // Agrega etiqueta

        frame.setVisible(true); // Muestra la ventana finalmente
    }

    private JButton crearBotonControl(String t, int x, int y, int w, java.awt.event.ActionListener al) { // Metodo para fabricar botones
        JButton b = new JButton(t); // Crea boton con texto
        b.setBounds(x, y, w, 40); // Define posicion y tamaño
        b.setBackground(colorPanel); // Fondo oscuro
        b.setForeground(Color.WHITE); // Letras blancas
        b.setFocusPainted(false); // Quita recuadro de foco
        b.setBorder(new LineBorder(colorAcento, 1)); // Borde neon
        b.setFont(new Font("Dialog", Font.BOLD, 12)); // Fuente clara
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia flecha por mano
        b.addActionListener(al); // Asigna la accion que hara al clic
        b.addMouseListener(new java.awt.event.MouseAdapter() { // Añade efectos visuales
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(colorAcento); b.setForeground(Color.BLACK); } // Ilumina al entrar
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(colorPanel); b.setForeground(Color.WHITE); } // Apaga al salir
        });
        frame.add(b); // Lo pone en la ventana
        return b; // Retorna el boton listo
    }

    private void configurarTimer() { // Reloj que corre cada 0.1 segundos
        new Timer(100, e -> { // Crea el timer
            barraProgreso.setValue(reproductor.getPorcentajeProgreso()); // Actualiza barra
            if (reproductor.estaReproduciendo()) visualizerPanel.repaint(); // Mueve las barritas
            if (reproductor.estaFinalizado()) cambiarCancion(1); // Si acabo, pasa a la siguiente
        }).start(); // Arranca el timer
    }

    private void cambiarCancion(int delta) { // Lógica de navegacion
        int total = listModel.getSize(); // Mira cuantas canciones hay cargadas
        if (total == 0) return; // Si no hay nada, no hace nada
        int nuevo = esAleatorio && delta > 0 ? random.nextInt(total) : listaCanciones.getSelectedIndex() + delta; // Decide si es azar o correlativo
        if (nuevo >= 0 && nuevo < total) { // Si el numero es valido
            listaCanciones.setSelectedIndex(nuevo); // Selecciona en la lista visual
            reproducirSeleccionada(); // Llama a tocar la musica
        }
    }

    private void reproducirSeleccionada() { // Metodo para tocar el tema elegido
        Cancion c = listaCanciones.getSelectedValue(); // Obtiene el objeto cancion
        if (c != null) { // Si hay algo elegido
            reproductor.reproducir(c.getRuta()); // Le pasa la ruta al motor
            estadoLabel.setText("SONANDO: " + c.toString().substring(3).toUpperCase()); // Muestra nombre en pantalla
        }
    }

    private void seleccionarArchivos() { // Abre el explorador de archivos
        JFileChooser fc = new JFileChooser(); // Crea el cuadro de dialogo
        fc.setMultiSelectionEnabled(true); // Deja elegir varios archivos
        if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { // Si el usuario dio en Abrir
            for (File f : fc.getSelectedFiles()) listModel.addElement(new Cancion(f.getAbsolutePath())); // Agrega cada ruta a la lista
            if (listaCanciones.getSelectedIndex() == -1 && !listModel.isEmpty()) listaCanciones.setSelectedIndex(0); // Elige la primera si no hay nada
        }
    }

    public static void main(String[] args) { // Punto de inicio
        SwingUtilities.invokeLater(() -> new ReproductorGUI()); // Lanza la interfaz graficamente
    }
}