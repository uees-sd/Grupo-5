/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package admincenterprofesor;

import java.util.ArrayList;
import models.Batalla;
import models.Pregunta;
import models.Respuesta;
import models.Usuario;
import rastreoClientes.UDPBroadcastServer;

/**
 *
 * @author aleja
 */
public class AdminCenterProfesor {

    UDPBroadcastServer conexionUsuarios = new UDPBroadcastServer();
    private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
    private ArrayList<String> preguntasCrudas = new ArrayList<String>();
    private ArrayList<Batalla> batallas = new ArrayList<Batalla>();
    private Usuario[] jugadores = new Usuario[2];
    
    
    
    public ArrayList<Usuario> getJugadoresConectados()
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
        batallastr = batallas.size() + ".- " + batalla.getJugador1().getUserName() + " vs " + batalla.getJugador2().getUserName();
        return batallastr;
    }
    
    
}
