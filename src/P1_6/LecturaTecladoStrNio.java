package P1_6;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;

public class LecturaTecladoStrNio {
    public static void main(String [] args){
        System.out.println("---------- ESTO ES UNA DEMO DE LECTURA DEL TECLDADO ----------");
        System.out.println("\nEscribe tu nombre ( y pulsa Enter) :");
        /**
         *Vamos a usar la versión java.nio que más actualizada
         * de java manejo de ficheros y Buffers
         */

         /*****************************************************
          * PASOS A DAR:
          * 1. Crear un InputStreamReader: Conecta/lee el flujo de bytes (Sytem.in)
          * y entrega un flujo de caracteres que así podremos interpretar
          *         InputStreamReader isr = new InputStreamReader(System.in)
          * 
          * 2. Crear un BufferedReader: Añade funcionalidad de lectura eficiente.
          * En concreto, permite poder leer por líneas el InputStreamReader.
          * Esto es importante para el rendimiento cuando hay que leer bloques de datos grandes.
          *         BufferReader lectorTeclado = new BufferedReader(new InputStreamReader (System.in))
          *****************************************************/
         try(BufferedReader lectorTeclado = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8))){
            String s = lectorTeclado.readLine();
            System.out.println("Hola "+s+" Escribe tu lenguaje de programación favorito: ");
            String s2= lectorTeclado.readLine();
            System.out.println("\n¡¡¡Genial!!! "+s+ "Te gusta complicarte demasiado la vida por 2ms de eficiencia con "+s2);
         }catch(IOException e){
            System.out.println("Ha ocurrido un error, más detalles:\n"+e.getMessage());
         }
    }
}
