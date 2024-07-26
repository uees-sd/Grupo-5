package Estudiante.Modelo;
import javax.swing.SwingUtilities;

import Estudiante.Vista.*;
public class Cliente {
    public static void main(String[] args) throws Exception {
        //Vista ventana = new Vista();
        //ventana.setVisible(true);
        SwingUtilities.invokeLater(() -> new Registro());
        //System.out.println("Hello, World!");
    }
}
