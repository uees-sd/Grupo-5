import java.io.IOException;

import models.Usuario;

public class PruebaApp {
    public static void main(String[] args) throws ClassNotFoundException {
        
        try {
            Usuario usuario = new Usuario("Alejandro", "Imagen", "4454455");
            byte[] mensaje = Serializer.serializeObject(usuario);
            Usuario usuario2 = (Usuario)Serializer.deserializeObject(mensaje);
            System.out.println(usuario2.getUserName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
