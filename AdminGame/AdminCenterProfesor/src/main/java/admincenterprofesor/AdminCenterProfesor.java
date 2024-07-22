/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package admincenterprofesor;

import java.util.ArrayList;
import models.Usuario;
import rastreoClientes.UDPBroadcastServer;

/**
 *
 * @author aleja
 */
public class AdminCenterProfesor {

    UDPBroadcastServer conexionUsuarios = new UDPBroadcastServer();
    
    
    public ArrayList<Usuario> getJugadoresConectados()
    {   
        return conexionUsuarios.getJugadoresClientes();
    }
}
