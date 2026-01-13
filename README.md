# 🎵 Java Neon Music Player v3.0

Un reproductor de música MP3 moderno desarrollado en **Java**, enfocado en una estética **Neon-Dark** y una arquitectura de hilos robusta para una reproducción fluida.



## 🚀 Características principales

* **Interfaz Moderna:** Diseño "Dark Mode" con acentos neón desarrollado puramente en Java Swing.
* **Visualizador Animado:** Sistema de barras de espectro simulado que reacciona mientras la música suena.
* **Control de Pausa Inteligente:** Capacidad de pausar y reanudar canciones exactamente en el mismo segundo mediante el cálculo de *byte-skipping*.
* **Gestión de Listas:** Importación múltiple de archivos MP3 y navegación intuitiva.
* **Controles Avanzados:** Incluye modo aleatorio (Shuffle), control de volumen logarítmico y salto automático de pista.

## 🛠️ Tecnologías Utilizadas

* **Java SE:** Lenguaje principal.
* **JLayer (Javazoom):** Para la decodificación y procesamiento de archivos MP3.
* **Java Swing & AWT:** Para la creación de la interfaz gráfica y el renderizado del visualizador.
* **Multithreading:** Uso de hilos independientes para audio e interfaz, evitando que la ventana se bloquee.



## 📂 Estructura del Código

El proyecto se divide en tres clases clave para mantener la separación de responsabilidades:

1.  **`Cancion.java`**: Modelo de datos que gestiona la información de los archivos de audio.
2.  **`ReproductorMP3.java`**: El motor lógico. Gestiona los `InputStreams`, el volumen del sistema y la decodificación de audio.
3.  **`ReproductorGUI.java`**: La capa visual. Gestiona los eventos de usuario, el dibujo del visualizador y las actualizaciones en tiempo real.

## ⚙️ Instalación y Uso

1. **Requisitos:** Tener instalado el JDK 8 o superior y la librería `jl1.0.1.jar` (JLayer).
2. **Clonación:**
   ```bash
   git clone [https://github.com/tu-usuario/reproductor-neon-java.git](https://github.com/tu-usuario/reproductor-neon-java.git)
