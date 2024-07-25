//package prueba.src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Deteccion {

    public String prefix(){
        try {
            // Obtenemos todas las interfaces de red de la computadora
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // Filtramos las interfaces inactivas o loopback
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                // Obtenemos las direcciones IP de la interfaz
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();

                    // Filtramos solo las direcciones IPv4 
                    if (address instanceof java.net.Inet4Address) {
                        // Obtenemos la máscara de subred
                        int prefixLength = networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
                        int mask = 0xffffffff << (32 - prefixLength);

                        // Convertimos la dirección IP y la máscara de subred a formato de entero
                        int ip = byteArrayToInt(address.getAddress());
                        int netmask = mask;

                        // Calculamos el prefijo de red
                        int network = ip & netmask;
                        String subnet = intToIp(network);

                        System.out.println("Dirección IP: " + address.getHostAddress());
                        System.out.println(subnet);
                        System.out.println("Prefijo de red: " + subnet + "/" + prefixLength);
                        return subnet;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return"";
    }

    private static int byteArrayToInt(byte[] bytes) {
        int result = 0;
        for (byte b : bytes) {
            result = (result << 8) | (b & 0xff);
        }
        return result;
    }

    private static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + ".";
               //(ip & 0xFF);
    }

    public List<String> IPs(){
        String subnet = prefix();
        int timeout = 1000;
        List<String> connectedIPs = new ArrayList<>();

        for (int i = 1; i < 255; i++) {
            String host = subnet + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    System.out.println(host + " is reachable.");
                    connectedIPs.add(host);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connectedIPs;
    }

    public String ip2(String ip){

        return " ";
    }
}
