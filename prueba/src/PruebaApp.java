
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import models.Batalla;


public class PruebaApp {
    public static void main(String[] args) throws ClassNotFoundException {
        
        // try {
        //     Usuario usuario = new Usuario("Alejandro", "Imagen", "4454455");
        //     byte[] mensaje = Serializer.serializeObject(usuario);
        //     Usuario usuario2 = (Usuario)Serializer.deserializeObject(mensaje);
        //     System.out.println(usuario2.getUserName());

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        ArrayList<Batalla> batallas = new ArrayList<>();
        Batalla batallaini = new Batalla(1, null, null, 0, 0, null, null, 0);
        Batalla batallaini2 = new Batalla(2, null, null, 0, 0, null, null, 0);
        batallas.add(batallaini);
        batallas.add(batallaini2);
        Batalla batalla = batallaini2;
        Optional<Batalla> optionalBatalla = batallas.stream()
            .filter(c -> c.getNumBatalla() == batalla.getNumBatalla())
            .findFirst();
        
        Batalla bta = optionalBatalla.get();
        int index = batallas.indexOf(bta);
        System.out.println(index);
    }
}
