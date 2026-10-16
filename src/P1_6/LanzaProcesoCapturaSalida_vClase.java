package P1_6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;

public class LanzaProcesoCapturaSalida_vClase {
    public static void main(String[] args) {
        //Leer los argumentos de entrada
        if(args.length <=0){
            System.out.println("Debes darme un comando a ejecutar.");
            System.exit(1);
        }

        ProcessBuilder pH = new ProcessBuilder();
        /**
         * REQUISITO 1:
         * El hijo debe heredar la E/S del padre
         */
        pH.inheritIO();
        
        //Controlar excepciones IOException, InterruptedException, RuntimeException
        try {
            
            Process p= pH.start();
        //Crear proceso hijo con los comandos anteriores
         /**
          * REQUISITO 2:
          *  Extraer toda la información posible por si decido crear un log.
          *  Mucha de esta información puede no estar disponible, si procesoHijo
          *  no está aún en ejecución, mientras que los print si que se han ejecutado muy rápido.
          *  Debes usar:
          *     1)métodos de Process(isAlive, info, pid) ProcessBuilder(directory)
          *     2) ->->buscar de info(), sus métodos de acceso a cada tipo de información "Info" del proceso.
          *         2.1)En esta tarea te pido usar: arguments(), command(), startInstant() y user().
          *         2.2)Fíjate, en el caso de arguments(), que devuelve un Array de Strings que
          *             se puede convertir al String que pintar con toString()
          *         2.3)Debes repasar la Clase Optional para saber usar el método "orElse" en info().arguments().orElse()
          *         porque, cuando "orElse" devuelve Other, queremos que entregue un mensaje
          *         que pones entre "" (String no vacio) o bien pones un String vacio <con >new String[0]" 
          */

        System.out.printf("Proceso hijo en ejecución: %s\n",p.isAlive() ? "Sí" : "No");
        System.out.printf("Directorio del proceso: %s\n",pH.directory());
        System.out.printf("Más información del proceso %s\n", Arrays.toString(p.info().arguments().orElse(new String[0])));
        System.out.printf("Otra info del proceso sobre el comando que lo creó %s\n",p.info().command().orElse("¡No hay comando!"));
        System.out.printf("Inicio del proceso : %s \n", p.info().startInstant().orElse(Instant.now()));
        System.out.printf("PID: %d\n",p.pid());
        System.out.printf("User info: %s\n",p.info().user().orElse("No hay usuario conocido :("));
        //Pintar el comando que nos ha llegado en los argumentos
        System.out.printf(" Sea va a lanzar un proceso hijo consistente en %s, en el dicertorio %s"
                        , pH.command(), pH.directory());
        /**
         * REQUISITO 3:
         * Uso un "try-with-resources" que aprendimos en la clase anterior
         * para no necesitar hacer un "final" para close()
         */
            try(InputStream is = p.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr))
                {
                /**
                 * No se puede usar directamente InputStreamReade sin un InputStream,
                 * porque el constructor de InputStreamReader requiere un objeto
                 * de tipo InputStream como argumento.
                 *
                 * Debemos repasar de las clases anteriores dónde se leía de teclado, se leía de fichero
                 * para, ahora, leer argumentos de la entrada del proceso.
                 * InputStreamReader es una clase que convierte bytes en caracteres,
                 * y necesita saber de dónde vienen esos byte, que oueden venir de:
                 *          System.in
                 *          FileInputStream
                 *          Process.getInputSream() que es este caso
                 * 
                 */
                int codigo = p.waitFor(); //Se puede mejorar con onExit()
                System.out.println("La ejecución de "+ Arrays.toString((args))+ " devuelve " + codigo + (codigo==0 ? "Se ha ejecutado correctamente" : " Ha habido un error"));
                System.out.println("-> -> ¡HA TERMINADO!"); 
                System.out.printf("Fin de la info de %s en el directorio %s \n" +Arrays.toString(args), pH.directory());
                System.out.println("Salida del proceso");
            }
            catch (IOException e) {
                System.err.println("Error durante la ejecuciónn del proceso");
                System.out.println("---------------------------------------");
                e.printStackTrace();
                System.out.println("---------------------------------------");
                System.exit(2); //Para la JVM(el main) y el procesoHijo comunica a la shell (2) argumentos o sintaxis incorrectos en la línea de comandos
            }
            catch(InterruptedException e){
                System.err.println("Proceso interrumpido" + e.getStackTrace());
            }
            
        } 
        catch (Exception e) {
            e.getStackTrace();
        }
        

    }
}
