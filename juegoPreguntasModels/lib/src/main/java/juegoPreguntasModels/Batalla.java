/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegoPreguntasModels;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author aleja
 */
public class Batalla implements Serializable{
    private int numBatalla;
    private Usuario jugador1;
    private Usuario jugador2;
    private double vidaJugador1;
    private double vidaJugador2;
    private Usuario Ganador;
    private Usuario Perdedor;
    private double duracion;
    private ArrayList<Pregunta> preguntas;

    public Batalla(){};

    public Batalla(int numBatalla, Usuario jugador1, Usuario jugador2, double vidaJugador1, double vidaJugador2, Usuario ganador,
            Usuario perdedor, double duracion, ArrayList<Pregunta> preguntas) {
        this.numBatalla = numBatalla;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.vidaJugador1 = vidaJugador1;
        this.vidaJugador2 = vidaJugador2;
        Ganador = ganador;
        Perdedor = perdedor;
        this.duracion = duracion;
        this.preguntas = preguntas;
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public int getNumBatalla() {
        return numBatalla;
    }
    public void setNumBatalla(int numBatalla) {
        this.numBatalla = numBatalla;
    }
    public Usuario getJugador1() {
        return jugador1;
    }
    public void setJugador1(Usuario jugador1) {
        this.jugador1 = jugador1;
    }
    public Usuario getJugador2() {
        return jugador2;
    }
    public void setJugador2(Usuario jugador2) {
        this.jugador2 = jugador2;
    }
    public double getVidaJugador1() {
        return vidaJugador1;
    }
    public void setVidaJugador1(double vidaJugador1) {
        this.vidaJugador1 = vidaJugador1;
    }
    public double getVidaJugador2() {
        return vidaJugador2;
    }
    public void setVidaJugador2(double vidaJugador2) {
        this.vidaJugador2 = vidaJugador2;
    }
    public Usuario getGanador() {
        return Ganador;
    }
    public void setGanador(Usuario ganador) {
        Ganador = ganador;
    }
    public Usuario getPerdedor() {
        return Perdedor;
    }
    public void setPerdedor(Usuario perdedor) {
        Perdedor = perdedor;
    }
    public double getDuracion() {
        return duracion;
    }
    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

   

}
