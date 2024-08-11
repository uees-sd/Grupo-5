/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegoPreguntasModels;

import java.io.Serializable;

/**
 *
 * @author aleja
 */
public class RespuestaPregunta implements Serializable{
    private Usuario jugador;
    private Pregunta pregunta;
    private boolean esCorrecta;

    public RespuestaPregunta(Usuario jugador, Pregunta pregunta, boolean esCorrecta) {
        this.jugador = jugador;
        this.pregunta = pregunta;
        this.esCorrecta = esCorrecta;
    }

    public Usuario getJugador() {
        return jugador;
    }

    public void setJugador(Usuario jugador) {
        this.jugador = jugador;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
    
    

    
    
    
}
