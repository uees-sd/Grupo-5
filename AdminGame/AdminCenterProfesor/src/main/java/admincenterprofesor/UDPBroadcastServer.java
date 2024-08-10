/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admincenterprofesor;
import admincenterprofesor.Serializer;
import java.net.*;
import java.util.ArrayList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import juegoPreguntasModels.Usuario;
/**
 *
 * @author aleja
 */
public class UDPBroadcastServer {

    //private static List<String> subnets = new ArrayList<>();
    private static List<String> connectedIPs = new ArrayList<>();

    public ArrayList<Usuario> getJugadoresClientes() throws ClassNotFoundException {
        List<String> subnets;
        try {
            subnets = getLocalSubnets();
            int range = 254; // Rango de direcciones IP por subred

            ExecutorService executor = Executors.newFixedThreadPool(20); // Número de hilos

            for (String subnet : subnets) {
                for (int i = 1; i <= range; i++) {
                    String host = subnet + i;
                    executor.execute(new PingTask(host));
                }
            }
            executor.shutdown();
            
            // Esperar hasta que todos los hilos terminen o se agote el tiempo de espera
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Forzar la terminación de los hilos si es necesario
            }
        } catch (SocketException ex) {
            Logger.getLogger(UDPBroadcastServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UDPBroadcastServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Usuario> usuarios = new ArrayList<>(); //jugadores usuarios conectados
        
         // ip de jugadores conectados
        
        int port = 2020; // Puerto del servidor ENVIA
        int port2 = 2030; // Puerto del servidor RECIBE
        String message = "Hola desde el servidor!";
        // Enviar señal de broadcast
        try (DatagramSocket socket = new DatagramSocket()) {
            //List<String> connectedIPs = new ArrayList<>();
            //Deteccion detect = new Deteccion();
            //connectedIPs =  detect.IPs();
            //String prefix = detect.prefix();
            
            for (String ip : connectedIPs) {
                //String host = prefix + i;
                //String host = connectedIPs.get(i);
                socket.setBroadcast(true);
                byte[] buffer = message.getBytes();
                InetAddress broadcastAddress = InetAddress.getByName(ip); // Dirección de broadcast
                //System.out.println(connectedIPs);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Señal de broadcast enviada");
            }

            socket.setBroadcast(true);
            byte[] buffer = message.getBytes();
            InetAddress broadcastAddress = InetAddress.getByName("192.168.100.210"); // Dirección de broadcast
            //System.out.println(connectedIPs);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
            socket.send(packet);
            System.out.println("Señal de broadcast enviada");
            
            socket.setBroadcast(true);
            byte[] buffer2 = message.getBytes();
            broadcastAddress = InetAddress.getByName("192.168.100.108"); // Dirección de broadcast
            //System.out.println(connectedIPs);
            DatagramPacket packet2 = new DatagramPacket(buffer2, buffer.length, broadcastAddress, port);
            socket.send(packet2);
            System.out.println("Señal de broadcast enviada");
            
//            String prefix = "192.168.100.";
//            for (int i = 0; i < 255; i++) {
//                String host = prefix + i;
//                //String host = connectedIPs.get(i);
//                socket.setBroadcast(true);
//                byte[] buffer = message.getBytes();
//                InetAddress broadcastAddress = InetAddress.getByName(host); // Dirección de broadcast
//                //System.out.println(connectedIPs);
//                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
//                socket.send(packet);
//                System.out.println("Señal de broadcast enviada");
//            }
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
    
    static List<String> getLocalSubnets() throws SocketException {
        List<String> subnets = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            networkInterface.getInterfaceAddresses().forEach(address -> {
                if (address.getAddress().isSiteLocalAddress()) {
                    String subnet = getSubnet(address.getAddress().getHostAddress(), address.getNetworkPrefixLength());
                    if (subnet != null && !subnets.contains(subnet)) {
                        subnets.add(subnet);
                    }
                }
            });
        }

        return subnets;
    }

    static String getSubnet(String ipAddress, short prefixLength) {
        try {
            int subnetMask = 0xffffffff << (32 - prefixLength);
            int ip = inetAddressToInt(InetAddress.getByName(ipAddress));

            int subnet = ip & subnetMask;
            int subnetOctets = subnet >>> 8;
            return String.format("%d.%d.%d.", (subnetOctets >>> 16) & 0xff, (subnetOctets >>> 8) & 0xff, subnetOctets & 0xff);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int inetAddressToInt(InetAddress inetAddress) {
        byte[] addressBytes = inetAddress.getAddress();
        int addressInt = 0;
        for (int i = 0; i < 4; i++) {
            addressInt = (addressInt << 8) | (addressBytes[i] & 0xff);
        }
        return addressInt;
    }

    static class PingTask implements Runnable {
        private final String ip;

        PingTask(String ip) {
            this.ip = ip;
        }

        @Override
        public void run() {
            try {
                InetAddress inet = InetAddress.getByName(ip);
                if (inet.isReachable(1000)) { // Timeout de 1 segundo
                    System.out.println(ip + " está conectado.");
                    connectedIPs.add(ip);
                }
            } catch (IOException e) {
                System.err.println("Error verificando " + ip + ": " + e.getMessage());
            }
        }
    }
}
