package Estudiante.Vista;
import java.awt.*;
import javax.swing.*;

public class Vista extends JFrame{
   JPanel frame;
   public JLabel fondo;
   public JLabel arena;
 public Vista() {
        super("Vista Estudiante");
        ImageIcon img = new ImageIcon("src/Estudiante/Vista/UI/959fb1f51bc1026ace27acede55e1bc8.gif");
        setBounds(10, 10, 1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame = new JPanel();
        frame.setLayout(new BorderLayout());
        setContentPane(frame);
        fondo = new JLabel();
        fondo.setIcon(img);
        frame.add(fondo, BorderLayout.CENTER);
        screen("src/Estudiante/Vista/UI/txt.gif", 0, 400, 1900, 946);
        screen("src/Estudiante/Vista/Juez/Juez.gif", -750, 400, 1900, 946);
        screen("src/Estudiante/Vista/Avatares/Avatar1/Idle/idle_R.gif", 250, 75, 1900, 946);
        screen("src/Estudiante/Vista/Avatares/Avatar2/Idle/Idle_L.gif", -250, 75, 1900, 946);   
        screen("src/Estudiante/Vista/UI/Picture1.png", 0, 70, 1900, 946);
        screen("src/Estudiante/Vista/UI/heart.png",250 ,170, 100, 100);
        screen("src/Estudiante/Vista/UI/heart.png",350 ,165, 100, 100);
        screen("src/Estudiante/Vista/UI/heart.png",450 ,160, 100, 100);
        screen("src/Estudiante/Vista/UI/heart.png",1555 ,170, 100, 100);
        screen("src/Estudiante/Vista/UI/heart.png",1455 ,165, 100, 100);
        screen("src/Estudiante/Vista/UI/heart.png",1355 ,160, 100, 100);
        screen("src/Estudiante/Vista/UI/ui_vid.png",0 ,100, 1900, 1200);       
        screen("src/Estudiante/Vista/UI/final_destination___ultimate__ssf2_style__by_darktremor100_ddovxn1-fullview.png",0 ,0, 1900, 946);
        
        
        
    }
 
    private void screen(String inter, int x, int y, int w, int h){
        ImageIcon screen = new ImageIcon(inter);
        arena = new JLabel(screen);
        arena.setBounds(x, y, w, h);
        fondo.add(arena);
    }
}
