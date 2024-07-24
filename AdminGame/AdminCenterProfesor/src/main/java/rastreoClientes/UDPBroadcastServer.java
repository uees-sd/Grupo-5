/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rastreoClientes;
import admincenterprofesor.Serializer;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import models.Usuario;

/**
 *
 * @author aleja
 */
public class UDPBroadcastServer {
    
    public ArrayList<Usuario> getJugadoresClientes() throws ClassNotFoundException
    {
        ArrayList<Usuario> usuarios = new ArrayList<>(); //jugadores usuarios conectados
        
        List<String> connectedIPs = new ArrayList<>(); // ip de jugadores conectados
        
        int port = 2020; // Puerto del servidor ENVIA
        int port2 = 2030; // Puerto del servidor RECIBE
        String message = "Hola desde el servidor!";
        // Enviar señal de broadcast
        try (DatagramSocket socket = new DatagramSocket()) {
            //List<String> connectedIPs = new ArrayList<>();
            Deteccion detect = new Deteccion();
            //connectedIPs =  detect.IPs();
            //String prefix = detect.prefix();
            String prefix = "10.2.105.";
            for (int i = 0; i < 255; i++) {
                String host = prefix + i;
                //String host = connectedIPs.get(i);
                socket.setBroadcast(true);
                byte[] buffer = message.getBytes();
                InetAddress broadcastAddress = InetAddress.getByName(host); // Dirección de broadcast
                //System.out.println(connectedIPs);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Señal de broadcast enviada");
            }
            
            prefix = "10.2.108.";
            for (int i = 0; i < 255; i++) {
                String host = prefix + i;
                //String host = connectedIPs.get(i);
                socket.setBroadcast(true);
                byte[] buffer = message.getBytes();
                InetAddress broadcastAddress = InetAddress.getByName(host); // Dirección de broadcast
                //System.out.println(connectedIPs);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Señal de broadcast enviada");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        // Configurar el servidor UDP para recibir mensajes de los clientes
        try (DatagramSocket serverSocket = new DatagramSocket(port2)) {
            serverSocket.setSoTimeout(10000);
            
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            while (true) {  
                try
                {
                    System.out.println("Esperando mensajes de clientes...");
                    serverSocket.receive(receivePacket);

                    //String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    Usuario usuario = (Usuario)Serializer.deserializeObject(receivePacket.getData());
                    System.out.println("Mensaje recibido de " + receivePacket.getAddress().getHostAddress() + ": " + usuario.getUserName());
                    String reciverAddress = receivePacket.getAddress().getHostAddress();
                    connectedIPs.add(reciverAddress);
                    usuario.setIp((usuario.getIp() == null) ? reciverAddress : usuario.getIp());
                    usuarios.add(usuario);
                }catch(SocketTimeoutException e)
                {
                    System.out.println("No se recibió ningún mensaje en los últimos 7 segundos. Saliendo del bucle.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    } 
}
