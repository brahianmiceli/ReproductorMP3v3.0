package org.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // En lugar de reproducir una ruta fija, lanzamos la interfaz gráfica
        // SwingUtilities.invokeLater asegura que la ventana se cree de forma segura
        SwingUtilities.invokeLater(() -> {
            // Creamos una instancia de la interfaz que diseñamos
            // Esta clase ya contiene internamente al ReproductorMP3
            new ReproductorGUI();
        });

        // El código anterior de reproducir una ruta fija ya no es necesario aquí,
        // ya que ahora usarás el botón "+" del reproductor para cargar tus canciones.
    }
}