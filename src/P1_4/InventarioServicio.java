package P1_4;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventarioServicio {
     static void procesarListaCompras(List<String> lista){
        lista.add("Monitor"); lista.add("Teclado"); lista.add("Monitor");
        lista.add("Ratón"); lista.add("Cable HDMI");
        System.out.println("Actividad 1:");
        System.out.println("Esta es la primera lista:");
        System.out.println("-------------------------");
         for (String s : lista) {
             System.out.printf("%s ", s);
         }
        System.out.println("\n-------------------------\n");
        lista.remove(2);
        System.out.println("Esta es la segunda lista:");
        System.out.println("-------------------------");
         for (String s : lista) {
             System.out.printf("%s ", s);
         }
        System.out.println("\n-------------------------");
    }


    static void obtenerCodigosUnicos(Set<String> codigos){
         codigos.add("A101"); codigos.add("B202"); codigos.add("A101");
         codigos.add("C303"); codigos.add("B202");
        System.out.println("Actividad 2:");
        System.out.println("Resultados:");
        System.out.println("-------------------------");
         for(String s:codigos){
             System.out.printf("%s ", s);
         }
         System.out.printf("\n¿Existe el código C303?: %b",codigos.contains("C303"));
         System.out.println("\n-------------------------");
    }

    static void actualizarInventario(Map<String,String> mapa,Set<String> codigos, List<String> lista){
         mapa.put("A101","Laptop");
         mapa.put("B202","Impresora");
         mapa.put("C303","Router");
         System.out.println("Actividad 3:");
         System.out.println("Estes es el contenido actual del hashset:\n"+codigos);
         System.out.println("Este es el contenido actual del hashmap:\n"+mapa);
         mapa.put("A101","Laptop Pro");
         System.out.printf("Este es el producto bajo el código 'A101' ahora:\n%s",mapa.get("A101"));
         System.out.printf("\nEste es el producto bajo el código 'B202':\n%s",mapa.get("B202"));
         System.out.println("\nEste es el nuevo contenido cambiando la primera clave:");
         System.out.println(mapa.entrySet());
    }
}
