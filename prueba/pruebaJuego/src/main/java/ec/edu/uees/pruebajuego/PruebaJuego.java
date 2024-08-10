/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.uees.pruebajuego;

import admincenterprofesor.Serializer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import juegoPreguntasModels.Batalla;
import juegoPreguntasModels.Pregunta;
import juegoPreguntasModels.Usuario;


/**
 *
 * @author aleja
 */
public class PruebaJuego {

    public static void main(String[] args) {
        int port = 2020;
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress broadcastAddress;
            DatagramPacket packet;
            
            ArrayList<Batalla> batallas = new ArrayList<>();
            
            Batalla batalla = new Batalla();
            Usuario jugador1= new Usuario("Galaxy", "","192.168.100.210");
            Usuario jugador2= new Usuario("AndresLi", "","192.168.100.108");
            batalla.setJugador1(jugador1);
            batalla.setJugador2(jugador2);
            //ArrayList<Pregunta> preguntas = new ArrayList<>();
            //batalla.setPreguntas(preguntas);
            socket.setBroadcast(true);
            byte[] buffer = Serializer.serializeObject(batalla);
            broadcastAddress = InetAddress.getByName(batalla.getJugador1().getIp());
            packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
            socket.send(packet);
            System.out.println("Señal de broadcast al usuario " + batalla.getJugador1().getUserName() + " con ip: " + batalla.getJugador1().getIp());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
