package crearprocesos;
import java.io.*;
public class ProcesoBasico {
	public static void main(String[] args){
		System.out.println("----- PROCESO PADRE(JVM): Iniciada la ejecución -----");

		//0 Obtener información de la JVM respecto a memoria reservada y núcleos disponibles
		Runtime r = Runtime.getRuntime();
		System.out.println("-> Memoria total_ " + r.totalMemory() + "bytes");
		System.out.println("-> Cores disponibles para la JVM: " + r.availableProcessors());

		//1. Identificar el OS en el que se está ejecutando esta aplicación de monitoreo
		String os = System.getProperty("os.name").toLowerCase();
		boolean esWindows = os.contains("win");

		/*2. Configurar el comando nativo según en qué OS estoy
		 *En Win "cmd.exe /c dir"
		 *En Win "ls -l"
		*/
		ProcessBuilder pH;
		if(esWindows){
			pH = new ProcessBuilder("cmd.exe /c dir");
		}
		else {
			pH = new ProcessBuilder("ls -l");
		}
		//3. Configurar el directorio de trabajo del subproceso(mi directorio como usuario)
		pH.directory(new File(System.getProperty("user.home")));

		/*4. Redirecciono la IO del proceso hijo para evitar usar lecturas de buffers
		 * y creación de hilos que los lean. Por que con inheritIO(), 
		 * el proceso hijo escribe directamente en la consola del OS :D
		*/
		pH.inheritIO();

		try {
			System.out.println("\n Lanzando proceso hijo en el OS...");
			System.out.println("---------------- RESULTADOS DE LA EJECUCIÓN DEL PROCESO HIJO --------------");
			//5. inicio del proceso hijo.
			Process p = pH.start();

			long pid= p.pid();
			boolean isAlive = p.isAlive();
			System.out.println("\n---------- Status del proceso hijo ----------");
			System.out.println("-> Proceso Hijo creadocon PID: "+pid + "¿Está vivo?: " + isAlive);
			/*6. De momento con Wait que supone espera síncrona.
			 * Veremos otro método onExit que permite que
			 * el proceso padre no se quede aquí congelado :D
			*/
			int codigo = p.waitFor();
			System.out.println("-> El proceso hijo (PID: " + pid + ") terminó con código: " + codigo);
		}
		catch(IOException e){
			System.out.println(e.getStackTrace());
		}
		catch(InterruptedException e){
			System.out.println("Se ha producido una interrupción del proceso padre mientras se ejecutaba");
		}

		System.out.println("\n Se ha terminado el programa al cerrarse el PROCESO PADRE (JVM). !Hasta pronto¡");
	}
}
