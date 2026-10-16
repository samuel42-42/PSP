package P1_6;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LectorFicheroNio {
    //Hay que usar preferiblemente rutas de archivos relativas configurables con variables de entorno
    private static final Path NOMBRE_ARCHIVO = Paths.get("src","P1_6","AlbionOnline.txt");
    public static void main(String[] args) {
        System.out.println(" ------ DEMO DE LECTRUA DE ARCHIVO CON JAVA.NIO.FILE ------");
        try(BufferedReader lectorFichero = Files.newBufferedReader(NOMBRE_ARCHIVO,StandardCharsets.UTF_8)) {
            String linea;
            int contador=0;
            while((linea = lectorFichero.readLine())!=null){
                contador++;
                //PASO DE FILTRADO PARA DIVERTIRNOS UN POCO :D
                //Quito líneas en blanco (líneas que empiecen por " ")
                if(linea.trim().isEmpty() || linea.trim().startsWith(",")){
                   System.out.println("Línea "+ contador + " OMITIDA(las comas del final de cada línea)"); 
                }
                else System.out.println("Línea " + contador + "(Contenido): " + linea);
                
            }
            System.out.print("\nProcesamiento del archivo " + NOMBRE_ARCHIVO +"completado.");
        } catch (IOException e) {
            System.err.println("Se ha producido un error, más información: "+e.getMessage());
        }
        
    }
    
}
