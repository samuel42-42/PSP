import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.*;
import java.io.File;

public class Clase{
    public static void main(String[] args) {
        if(args.length <=0){
            System.out.println("No ha se ha introducido ningún comando a ejecutar.");
            System.exit(0);
        }
        File fSalida = new File("salida.log");
        File fError = new File("error.log");
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectOutput(fSalida);
        pb.redirectError(fError);
        try{
            Process P=pb.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
}