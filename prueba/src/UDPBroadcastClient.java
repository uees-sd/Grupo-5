import java.io.*;
import java.net.*;

public class UDPBroadcastClient {
    public static void main(String[] args) {
        int port = 12345; // Puerto de escucha

        try {
            // Crear socket UDP para recibir la señal de broadcast
            DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            System.out.println("Esperando señal de broadcast...");
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Señal de broadcast recibida desde: " + packet.getAddress().getHostAddress() + " con mensaje: " + message);

            // Enviar respuesta al servidor
            String responseMessage = "Hola servidor, soy el cliente!";
            byte[] responseBuffer = responseMessage.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, packet.getAddress(), port);
            socket.send(responsePacket);
            System.out.println("Mensaje de respuesta enviado al servidor.");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

