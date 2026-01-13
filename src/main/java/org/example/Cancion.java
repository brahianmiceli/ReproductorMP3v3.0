package org.example; // Define el paquete del proyecto

import java.io.File; // Importa la clase File para manejar archivos

public class Cancion {
    private String ruta;   // Variable para guardar la direccion del archivo en el disco
    private String nombre; // Variable para guardar solo el nombre del archivo

    public Cancion(String ruta) { // Constructor que recibe la ruta completa
        this.ruta = ruta; // Asigna la ruta recibida a la variable de clase
        this.nombre = new File(ruta).getName(); // Crea un objeto File temporal para extraer el nombre
    }

    public String getRuta() { return ruta; } // Metodo para obtener la ubicacion del archivo

    @Override
    public String toString() { // Metodo que decide que texto se muestra en la lista visual
        return " >  " + nombre; // Retorna el nombre con un prefijo estetico
    }
}