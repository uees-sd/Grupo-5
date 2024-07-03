import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;





public class UDPBroadcastServer {

    
    public static void main(String[] args) {
        //List<String> connectedIPs = new ArrayList<>();
        int port = 2020; // Puerto del servidor
        String message = "Hola desde el servidor!";
        
        // Enviar señal de broadcast
        try (DatagramSocket socket = new DatagramSocket()) {
            List<String> connectedIPs = new ArrayList<>();
            Deteccion detect = new Deteccion();
            connectedIPs =  detect.IPs();
            for(int i=0;i<connectedIPs.size();i++){
                String host = connectedIPs.get(i);
                socket.setBroadcast(true);
                byte[] buffer = message.getBytes();
                InetAddress broadcastAddress = InetAddress.getByName(host); // Dirección de broadcast
                System.out.println(connectedIPs);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Señal de broadcast enviada");
            }
            


            

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configurar el servidor UDP para recibir mensajes de los clientes
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            while (true) {
                System.out.println("Esperando mensajes de clientes...");
                serverSocket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Mensaje recibido de " + receivePacket.getAddress().getHostAddress() + ": " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
