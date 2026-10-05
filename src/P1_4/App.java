package P1_4;

import java.util.*;

public class App {
    static void main(String [] args) {
        List <String>listaDemo = new ArrayList<>();
        InventarioServicio.procesarListaCompras(listaDemo);
        Set <String> codigosDemo = new HashSet<>();
        InventarioServicio.obtenerCodigosUnicos(codigosDemo);
        Map <String,String> inventarioDemo2 = new HashMap<>();
        InventarioServicio.actualizarInventario(inventarioDemo2,codigosDemo,listaDemo);

    }
}
