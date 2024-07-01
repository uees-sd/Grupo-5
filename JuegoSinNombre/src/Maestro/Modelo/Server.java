import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en el puerto " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while ((bytesRead = in.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            System.out.println("Archivo recibido, enviando a otros clientes...");

            for (Socket client : clients) {
                if (client != clientSocket && !client.isClosed()) {
                    try (BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream())) {
                        out.write(fileBytes);
                        out.flush();
                    } catch (IOException e) {
                        System.out.println("Error al enviar el archivo a un cliente: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el cliente: " + e.getMessage());
            }
        }
    }
}

