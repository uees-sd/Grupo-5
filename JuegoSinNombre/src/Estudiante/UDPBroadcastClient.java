package Estudiante;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import models.Serializer;
import models.Usuario;

public class UDPBroadcastClient {
    public static void main(String[] args) throws InterruptedException {
        int port = 2020; // Puerto de escucha
        int port2 = 2030; // Puerto de envio

        try {
            // Crear socket UDP para recibir la señal de broadcast
            DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
            //DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
            //String ip = InetAddress.getByName("0.0.0.0").toString();
            socket.setBroadcast(true);

            System.out.println("Esperando señal de broadcast...");
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Señal de broadcast recibida desde: " + packet.getAddress().getHostAddress() + " con mensaje: " + message);

            Usuario usuario = new Usuario();
            usuario.setUserName("Alejandro");
            usuario.setIp("");
            byte[] responseBuffer = Serializer.serializeObject(usuario);
            
            //Enviar respuesta al servidor
            // String responseMessage = "Hola servidor, soy el cliente!";
            // byte[] responseBuffer = responseMessage.getBytes();
           
           

            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, packet.getAddress(), port2);

            Thread.sleep(3000);
            socket.send(responsePacket);
            System.out.println("Mensaje de respuesta enviado al servidor.");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

