/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos_juego_sin_nombre;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author aleja
 */
public class Pregunta implements Serializable{
    private String pregunta;
    private String materia;
    private ArrayList<Respuesta> respuestasPosibles;

    public Pregunta(String pregunta, String materia, ArrayList<Respuesta> respuestasPosibles) {
        this.pregunta = pregunta;
        this.materia = materia;
        this.respuestasPosibles = respuestasPosibles;
    }
    
    public Pregunta(){}

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public ArrayList<Respuesta> getRespuestasPosibles() {
        return respuestasPosibles;
    }

    public void setRespuestasPosibles(ArrayList<Respuesta> respuestasPosibles) {
        this.respuestasPosibles = respuestasPosibles;
    }
}
