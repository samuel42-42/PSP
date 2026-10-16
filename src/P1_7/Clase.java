import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.*;
import java.io.File;

public class Clase{
    public static void main(String[] args) {
        try(BufferedReader lectorTeclado = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8))){
        ProcessBuilder pH = new ProcessBuilder(args);
        pH.inheritIO();
        Process P=pH.start();
        System.out.println(P.info());
        System.out.println();
        Path pathSalida = Path.of("P1_7/salida.log");
        Path pathError = Path.of("P1_7/error.log");
        File fSalida = pathSalida.toFile();
        File fError = pathError.toFile();
        pH.redirectOutput(ProcessBuilder.Redirect.to(fSalida));
        pH.redirectError(ProcessBuilder.Redirect.to(fError));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
}