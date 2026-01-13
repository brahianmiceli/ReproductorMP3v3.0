# 🎵 Reproductor MP3 v3.0

Un reproductor de música MP3 de alto rendimiento desarrollado en **Java**, con una estética **Neon-Dark** y una arquitectura de hilos robusta diseñada para una experiencia de usuario fluida y moderna.

---

## 🚀 Características Principales

* **🎨 Interfaz Moderna:** Diseño "Dark Mode" con acentos neón, construido íntegramente sobre **Java Swing** con componentes personalizados.
* **📊 Visualizador Animado:** Sistema de barras de espectro simulado que proporciona feedback visual en tiempo real.
* **⏸️ Control de Pausa Inteligente:** Implementación de *byte-skipping* técnico para reanudar canciones exactamente en el milisegundo donde se detuvieron.
* **📂 Gestión de Listas:** Soporte para importación múltiple de archivos MP3 y navegación intuitiva entre pistas.
* **⚙️ Controles Avanzados:** Incluye modo aleatorio (**Shuffle**), control de volumen logarítmico profesional y salto automático al finalizar la pista.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java SE (JDK 8+)
* **Librerías:** [JLayer (Javazoom)](http://www.javazoom.net/javalayer/javalayer.html) para decodificación de MP3.
* **Gráficos:** Java Swing & AWT para el renderizado de la GUI y animaciones.
* **Arquitectura:** Multithreading (Concurrencia) para la separación de procesos de audio e interfaz.

---

## 📂 Estructura del Proyecto

El código sigue el principio de separación de responsabilidades en tres clases principales:

1.  **`Cancion.java`**: Modelo de datos que encapsula los metadatos y rutas de los archivos de audio.
2.  **`ReproductorMP3.java`**: Motor lógico que gestiona los `InputStreams`, la decodificación y el hardware de salida.
3.  **`ReproductorGUI.java`**: Capa visual que orquestra los eventos de usuario y el renderizado del visualizador.

---

## ⚙️ Instalación y Ejecución

### 🖥️ Para Usuarios finales (Windows)
1. Descarga el archivo ejecutable **`Reproductor MP3 v3.0 by Brahian Miceli.exe`**.
2. Haz doble clic para iniciar la aplicación (No requiere abrir un IDE).
3. Utiliza el botón **➕** para cargar archivos MP3 y comenzar la reproducción.

### 💻 Para Desarrolladores
1. Clona el repositorio e impórtalo en IntelliJ IDEA o Eclipse.
2. Asegúrate de incluir la librería **`jl1.0.1.jar`** en el classpath.
3. Ejecuta la clase **`Main.java`**.

---

## 🧠 Conceptos Aprendidos

Durante el desarrollo de este software se aplicaron y consolidaron conceptos avanzados de ingeniería:

* **🛠️ Manipulación de Streams:** Implementación técnica de lectura de archivos binarios mediante `FileInputStream` y gestión de saltos de bytes para el control de la posición de reproducción.
* **🧵 Programación Concurrente (Multithreading):** Uso estratégico de la clase `Thread` para separar el proceso de audio del hilo de eventos de la interfaz (EDT), manteniendo la fluidez de la GUI.
* **📐 Matemáticas Aplicadas:** Implementación de algoritmos logarítmicos para convertir señales lineales en niveles de Decibelios ($dB$) naturales al oído humano.
* **📦 Distribución y Packaging:** Generación de artefactos `.jar` y creación de envoltorios nativos (`Wrappers`) `.exe` mediante **Launch4j**, configurando requisitos mínimos de JRE para entornos de producción.
* **🎨 UX/UI en Desktop:** Diseño de experiencia de usuario mediante *event listeners* complejos y renderizado personalizado para lograr una estética moderna coherente.

---

### Desarrollado con ❤️ por **Brahian**
