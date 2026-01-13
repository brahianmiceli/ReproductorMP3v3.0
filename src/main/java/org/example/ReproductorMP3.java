package org.example; // Define el paquete del proyecto

import java.io.*; // Importa clases para entrada y salida de datos
import javax.sound.sampled.*; // Importa clases para control de hardware de audio
import javazoom.jl.player.Player; // Importa la libreria JLayer para reproducir MP3

public class ReproductorMP3 {
    private Player player; // Objeto encargado de decodificar el MP3
    private Thread reproduccionThread; // Hilo para que el audio no trabe la ventana
    private String rutaActual; // Guarda la ruta del archivo que esta sonando
    private FileInputStream fileInputStream; // Flujo de lectura del archivo
    private long totalBytes; // Tamaño total del archivo en bytes
    private long bytesPausados; // Bytes que ya se escucharon antes de pausar
    private float volumenActual = 0.8f; // Nivel de volumen inicial (80%)
    private boolean estaMuteado = false; // Estado del silencio
    private boolean cancionTerminada = false; // Estado de finalizacion
    private boolean estaPausado = false; // Estado de la pausa

    public void reproducir(String ruta) { // Metodo para iniciar una cancion
        detener(); // Detiene cualquier reproduccion previa
        this.rutaActual = ruta; // Guarda la nueva ruta
        this.totalBytes = new File(ruta).length(); // Obtiene el peso total del archivo
        this.bytesPausados = 0; // Reinicia el contador de pausa
        this.estaPausado = false; // Asegura que no este en modo pausa
        iniciarHilo(0); // Llama al hilo empezando desde el principio (0)
    }

    public void pausar() { // Metodo para detener temporalmente
        if (player != null && !estaPausado) { // Si hay algo sonando y no esta ya pausado
            try {
                // Calcula la posicion actual: Total menos lo que falta por leer
                this.bytesPausados = totalBytes - fileInputStream.available();
                this.estaPausado = true; // Cambia el estado a pausado
                detenerHilos(); // Cierra el flujo para liberar memoria
            } catch (Exception e) { e.printStackTrace(); } // Muestra errores si ocurren
        }
    }

    public void reanudar() { // Metodo para seguir escuchando
        if (rutaActual != null && estaPausado) { // Si hay una cancion en pausa
            this.estaPausado = false; // Quita el estado de pausa
            iniciarHilo(bytesPausados); // Inicia el hilo desde donde se quedo
        }
    }

    public void detener() { // Metodo para parar todo
        detenerHilos(); // Cierra procesos de audio
        bytesPausados = 0; // Reinicia posicion
        estaPausado = false; // Quita pausa
        cancionTerminada = false; // Reinicia estado de fin
    }

    private void detenerHilos() { // Metodo interno para limpieza
        if (player != null) { player.close(); player = null; } // Cierra el decodificador
        if (reproduccionThread != null) { // Si el hilo existe
            reproduccionThread.interrupt(); // Interrumpe la ejecucion del hilo
            reproduccionThread = null; // Borra la referencia
        }
    }

    private void iniciarHilo(long skip) { // Crea el proceso de reproduccion
        cancionTerminada = false; // Reinicia flag de final
        reproduccionThread = new Thread(() -> { // Crea un nuevo hilo (multitarea)
            try {
                fileInputStream = new FileInputStream(rutaActual); // Abre el archivo
                if (skip > 0) fileInputStream.skip(skip); // Salta los bytes ya escuchados
                player = new Player(fileInputStream); // Prepara el decodificador

                new Thread(() -> { // Hilo extra corto para aplicar volumen con retraso
                    try { Thread.sleep(200); setVolumen(estaMuteado ? 0 : volumenActual); } catch(Exception e){}
                }).start(); // Inicia el hilo de volumen

                player.play(); // Ejecuta la musica (aqui el codigo se queda esperando)

                if (player.isComplete() && !estaPausado) { // Si termino y no fue por pausa
                    cancionTerminada = true; // Marca que la cancion finalizo
                }
            } catch (Exception e) { System.err.println("Hilo de audio cerrado."); }
        });
        reproduccionThread.start(); // Arranca el hilo de musica
    }

    public void setVolumen(float v) { // Metodo para ajustar el sonido
        if (!estaMuteado) volumenActual = v; // Guarda el valor si no esta en mute
        try {
            for (Mixer.Info info : AudioSystem.getMixerInfo()) { // Recorre los mezcladores de la PC
                Mixer mixer = AudioSystem.getMixer(info); // Obtiene el mezclador
                for (Line line : mixer.getSourceLines()) { // Recorre las lineas de salida
                    if (line.isOpen() && line.isControlSupported(FloatControl.Type.MASTER_GAIN)) { // Si tiene control de volumen
                        FloatControl gain = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN); // Obtiene el control
                        float dB = (float) (Math.log(v == 0 ? 0.0001 : v) / Math.log(10.0) * 20.0); // Convierte a Decibelios
                        gain.setValue(dB); // Aplica el cambio a la placa de sonido
                    }
                }
            }
        } catch (Exception e) {} // Ignora errores de hardware
    }

    public boolean estaReproduciendo() { return player != null && !estaPausado; } // Chequea actividad
    public boolean estaFinalizado() { return cancionTerminada; } // Chequea si acabo

    public int getPorcentajeProgreso() { // Calcula el avance de la barra
        if (totalBytes <= 0 || fileInputStream == null || player == null) return 0; // Evita error division por cero
        try {
            long leido = totalBytes - fileInputStream.available(); // Cuanto se ha leido ya
            return (int) ((leido * 100) / totalBytes); // Regla de tres para el porcentaje
        } catch (Exception e) { return 0; }
    }
}