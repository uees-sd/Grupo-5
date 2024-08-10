/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package admincenterprofesor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Optional;
import juegoPreguntasModels.*;



/**
 *
 * @author aleja
 */
public class AdminCenterProfesor {

    static int port = 2020; // Puerto del servidor ENVIA
    static int port2 = 2030; // Puerto del servidor RECIBE
    UDPBroadcastServer conexionUsuarios = new UDPBroadcastServer();
    private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
    private ArrayList<String> preguntasCrudas = new ArrayList<String>();
    private ArrayList<Batalla> batallas = new ArrayList<Batalla>();
    private Usuario[] jugadores = new Usuario[2];
    
    
    
    public ArrayList<Usuario> getJugadoresConectados() throws ClassNotFoundException
    {   
        return conexionUsuarios.getJugadoresClientes();
    }
    
    public boolean cargarPreguntas(String preguntaCruda)
    {
        if (preguntasCrudas.contains(preguntaCruda)) {
            return false;
        }
        try {
            String[] propiedades = preguntaCruda.split(";");
            Pregunta pregunta = new Pregunta();
            pregunta.setPregunta(propiedades[0]);
            pregunta.setMateria(propiedades[1]);
            String[] propiedadesRespuestas = propiedades[2].split(",");
            ArrayList<Respuesta> respuestas = new ArrayList<>();
            for(String resp : propiedadesRespuestas){
                Respuesta respuesta = new Respuesta();
                String esCorrecta = "(SI)";
                if(resp.contains(esCorrecta)){
                    String respCorrecta = resp.replace(esCorrecta, "").trim();
                    respuesta.setRespuesta(respCorrecta);
                    respuesta.setEsCorrecta(true);
                }else{
                    respuesta.setRespuesta(resp);
                }
                respuestas.add(respuesta);
            }
            pregunta.setRespuestasPosibles(respuestas);
            preguntas.add(pregunta);
            preguntasCrudas.add(preguntaCruda);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean cargarJugador(Usuario jugador, int numero){
        jugadores[numero] = jugador;
        return true;
    }
    
    public boolean limpiarJugadores()
    {
        jugadores[0] = null;
        jugadores[1] = null;
        return true;
    }
    
    public String crearBatalla()
    {
        String batallastr = "";
        Batalla batalla = new Batalla();
        batalla.setJugador1(jugadores[0]);
        batalla.setJugador2(jugadores[1]);
        batalla.setVidaJugador1(100);
        batalla.setVidaJugador2(100);
        batalla.setDuracion(10);
        batallas.add(batalla);
        batalla.setPreguntas(preguntas);
        batallastr = batallas.size() + ".- " + batalla.getJugador1().getUserName() + " vs " + batalla.getJugador2().getUserName();
        return batallastr;
    }
    
    public boolean enviarBatallas(){
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress broadcastAddress;
            DatagramPacket packet;
            for(Batalla batalla : batallas){
                socket.setBroadcast(true);
                byte[] buffer = Serializer.serializeObject(batalla);
                // Jugador 1
                broadcastAddress = InetAddress.getByName(batalla.getJugador1().getIp());
                packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Señal de broadcast al usuario " + batalla.getJugador1().getUserName() + " con ip: " + batalla.getJugador1().getIp());
                // Jugador 2
                broadcastAddress = InetAddress.getByName(batalla.getJugador2().getIp());
                packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Señal de broadcast al usuario " + batalla.getJugador2().getUserName() + " con ip: " + batalla.getJugador2().getIp());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<Batalla> obtenerBatallasActualizadas(){
        return this.batallas;
    }
    
    public void RecibirBatallas() throws ClassNotFoundException{
        try (DatagramSocket serverSocket = new DatagramSocket(port2)) {
            
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            while (true) {  
                try
                {
                    System.out.println("Esperando Batllas clientes...");
                    serverSocket.receive(receivePacket);
                    Batalla batalla = (Batalla) Serializer.deserializeObject(receivePacket.getData());

                    Optional<Batalla> batallaLst = batallas.stream()
                            .filter(c -> c.getNumBatalla() == batalla.getNumBatalla())
                            .findFirst();

                    if (batallaLst.isPresent()) {
                        int index = batallas.indexOf(batallaLst.get());
                        batallas.set(index, batalla);
                    } else {
                        System.out.println("No se relaciono la batalla.");
                    }
                } catch (SocketTimeoutException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
