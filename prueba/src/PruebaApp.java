
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;





public class PruebaApp {
    



    public static void main(String[] args) {
        try {
            List<String> subnets = getLocalSubnets();
            int range = 254; // Rango de direcciones IP por subred

            ExecutorService executor = Executors.newFixedThreadPool(20); // Número de hilos

            for (String subnet : subnets) {
                for (int i = 1; i <= range; i++) {
                    String host = subnet + i;
                    executor.execute(new PingTask(host));
                }
            }

            executor.shutdown();
        } catch (SocketException e) {
            System.err.println("Error obteniendo las subredes locales: " + e.getMessage());
        }
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
                }
            } catch (IOException e) {
                System.err.println("Error verificando " + ip + ": " + e.getMessage());
            }
        }
    }
}

    

