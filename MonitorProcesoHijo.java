import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class MonitorProcesoHijo {
    public static void main(String[] args) {
        System.out.println("--------------------PROCESO PADRE(JVM): Iniciando la ejecución...");

        //0. Obtener info de la memoria de la JVM y cores disponibles para este proceso main de mi máquina
        Runtime r= Runtime.getRuntime();
        System.out.println(">>>>>>>>>> Núcleos totales: " + r.availableProcessors() + " cores");
        System.out.println(">>>>>>>>>> Memoria total: " + r.freeMemory()+ " bytes");

        //1. Identificar el OS en el que estoy para poner el ping correcto
        String os = System.getProperty("os.name").toLowerCase();
        boolean esWindows = os.contains("win");
        /*
        *Configurar el comando ping a localhost 5 veces
        * En Win11 "ping", "-n", "5", "127.0.0.1"
        * En Linux "ping", "-c", "5", "127.0.0.1"
        */
        ProcessBuilder pH;
        if(esWindows){
            pH = new ProcessBuilder("ping", "-n", "10", "127.0.0.1");
        }
        else{
            pH = new ProcessBuilder("ping", "-c", "10", "127.0.0.1");
        }


        /*
         *3. Redirecciono la IO del proceso hijo para no usar aún letras de buffers
         * que aprenderemos más adelante, junto a la creación de hilos que los lean.
         * Así que, aquí usamos inheritIO() para quye el proceso hijo
         * escriba también en la consola del OS. 
         */
        pH.inheritIO();
        try {
            File archivoSalida= new File("salida_proceso.txt");
            pH.redirectOutput(archivoSalida);
            System.out.println("\nLanzando el proceso hijo en nuestro hermoso OS....");
            System.out.println("-------------------- RESULTADOS DE LA EJECUCIÓN DEL PROCESO HIJO --------------------");
            //4. Inicio del proceso hijo
            Process p = pH.start();

            long pid = p.pid();
            boolean vivo= p.isAlive();
            System.out.println("-------------------- STATUS DEL PROCESO HIJO --------------------");
            System.out.printf("-> Proceso hijo creado con PID: %s\n -> ¿Está vivo?: %b", pid,vivo);
            /*-------------------------------------------------------------------
             *5. En vez de waitFor() que supone espera asíncrona, uso aquí onExit()
             * que permite que el proceso padre no se quede congelado hatsa que
             * el hijo termine.
             *-------------------------------------------------------------------
             *
             * PASO 5.1: Obtener el ProcessHandle e invocar onExit()
             * para poder hacer otras cosas desde este main()(Proceso principal)
             * IMPORTANTE: El ProcessHandle es simplemente un puntero
             * para interactuar, a nivel de OS, con el mismo proceso hijp "pH"
             */
            ProcessHandle h = p.toHandle();
            CompletableFuture <ProcessHandle> tareaFutura = h.onExit();

            /*
             * PASO 5.2: Definimos que asinctonamente se compruebe
             * lo que queremos que se haga cuando el proceso hijo termine.
             */

            tareaFutura.thenAccept(ph ->{
                System.out.printf("\n[EVENTO ASÍNCRONO] ¡El proceso hijo con PID: %s ha terminado!\n",ph.pid());
                System.out.printf("[EVENTO ASÍNCRONO] ¿Sigue vivo?: %b\n",ph.isAlive());
                System.out.printf("[EVENTO ASÍNCRONO] Ejecutando en el hilo: %s\n",Thread.currentThread().getName());
            });

            //PASO 5.3 Demostrar que el proceso principal SIGUE LIBRE haciendo otras cosas
            System.out.println("3. El hilo principal continúa sin bloquearse...");
            for (int i = 0; i <5; i++) {
                System.out.println("---> Hilo Principal trabajando ...(" + i +" segundos)");
                Thread.sleep(1000);    
            }
            System.out.println("4. Fin del programa principal. :(");
        }
        catch(IOException e){
            System.out.println("Se ha producido un error al iniciar el proceso hijo: " + e.getMessage());
        }
        catch(InterruptedException e){
            System.out.println("Se ha interrumpido la ejecución del proceso padre:"+ e.getMessage());
        }
        System.out.println("\n-------------------- PROCESO PADRE (JAVM) ¡Ha terminado! --------------------");
    }
}
