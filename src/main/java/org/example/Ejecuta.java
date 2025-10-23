package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Ejecuta {
    public static void main(String[] args) {
        //comprobar que se han pasado argumentos
        if (args.length == 0) {
            System.out.println("Uso: java Ejecuta <comando> [opciones]");
            return;
        }

        try {
            //crear el proceso hijo con el comando y sus argumentos
            ProcessBuilder pb = new ProcessBuilder(args);
            pb.redirectErrorStream(true); // mezcla salida de error con la estándar

            System.out.println("Ejecutando comando: " + String.join(" ", args));

            //iniciar el proceso
            Process proceso = pb.start();

            // Leer la salida del proceso hijo
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            //el padre espera a que el hijo termine
            int exitCode = proceso.waitFor();

            //informar del resultado
            if (exitCode == 0) {
                System.out.println("\nEl comando se ejecutó correctamente (código 0).");
            } else {
                System.err.println("\nError: el comando terminó con código " + exitCode);
            }

        } catch (Exception e) {
            System.err.println("Error al ejecutar el comando: " + Arrays.toString(args));
            e.printStackTrace();
        }
    }
}
