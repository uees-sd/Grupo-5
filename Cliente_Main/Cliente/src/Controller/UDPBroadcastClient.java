package Controller;

import Vista.*;
import juegoPreguntasModels.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Enumeration;



public class UDPBroadcastClient {
    int port = 2020; // Puerto de escucha
    int port2 = 2030; // Puerto de envio
    public void inicio(String Name, String idAvatar) throws InterruptedException, ClassNotFoundException {
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

            Usuario usuario = new Usuario();
            usuario.setUserName(Name);
            usuario.setAvatar(idAvatar);
            //String IP = Ip();
            usuario.setIp(null);
            byte[] responseBuffer = Serializer.serializeObject(usuario);
            
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, packet.getAddress(), port2);

            Thread.sleep(3000);
            socket.send(responsePacket);
            System.out.println("Mensaje de respuesta enviado al servidor.");

            socket.close();
            
            recibir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void recibir() throws ClassNotFoundException{
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            //serverSocket.setSoTimeout(10000);
            
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            while (true) {  
                try
                {
                    System.out.println("Esperando batalla de servidor...");
                    serverSocket.receive(receivePacket);
                    Batalla batalla = (Batalla)Serializer.deserializeObject(receivePacket.getData());
                    System.out.println("Batalla recibida");
                    System.out.println(batalla.getJugador1().getAvatar());
                    System.out.println(batalla.getJugador1().getUserName()+" vs "+batalla.getJugador2().getUserName());
                    break;                  
                    }catch(SocketTimeoutException e)
                {
                    System.out.println("No se recibió ningún mensaje en los últimos 7 segundos. Saliendo del bucle.");
                    break;
                }
            }
            serverSocket.close();
            iniciarBatalla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void iniciarBatalla(){
        Registro vista = Registro.getInstance();
        vista.battle();
    }
    
}

