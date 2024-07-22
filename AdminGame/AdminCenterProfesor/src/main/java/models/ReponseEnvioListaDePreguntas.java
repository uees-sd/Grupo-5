/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos_juego_sin_nombre;

import java.io.Serializable;

/**
 *
 * @author aleja
 */
public class ReponseEnvioListaDePreguntas implements Serializable{
    private boolean Recibido;
    private String mensaje;

    public ReponseEnvioListaDePreguntas(boolean Recibido, String mensaje) {
        this.Recibido = Recibido;
        this.mensaje = mensaje;
    }
    
    public ReponseEnvioListaDePreguntas(){}

    public boolean isRecibido() {
        return Recibido;
    }

    public void setRecibido(boolean Recibido) {
        this.Recibido = Recibido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
