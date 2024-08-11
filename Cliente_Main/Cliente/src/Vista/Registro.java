package Vista;

import javax.swing.*;
import java.awt.*;
import Controller.UDPBroadcastClient;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registro extends JFrame {
    private static Registro instance; // Instancia única de la clase
    private JLabel button;
    private JTextField usernameField;
    private JLabel fondo;
    private String username = ""; // Variable para almacenar el nombre
    private String personajeSeleccionado = "";

    // Constructor privado para evitar la creación de instancias fuera de la clase
    private Registro() {
        super("Vista Estudiante");
        initializeUI();
    }

    // Método estático para obtener la instancia única de la clase
    public static synchronized Registro getInstance() {
        if (instance == null) {
            instance = new Registro();
        }
        return instance;
    }

    private void initializeUI() {
        // Configurar la JFrame para que se inicie maximizada
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar fondo con imagen
        fondo = new JLabel();
        fondo.setLayout(new GridBagLayout());
        ImageIcon img = new ImageIcon("src/Vista/UI/bg0.png");
        fondo.setIcon(new ImageIcon(img.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH)));
        add(fondo, BorderLayout.CENTER);

        // Panel para el nombre
        JPanel txtPanel = new JPanel();
        txtPanel.setOpaque(false);
        txtPanel.setLayout(new GridBagLayout());

        usernameField = new JTextField(20);

        JButton submitButton = new JButton("Continuar");
        submitButton.addActionListener(e -> handleNameSubmission());

        txtPanel.add(usernameField);
        txtPanel.add(submitButton);

        fondo.add(txtPanel);

        setVisible(true);
    }

    private void handleNameSubmission() {
        username = usernameField.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese su nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nombre ingresado: " + username);
            fondo.removeAll();
            UISelect();
        }
    }

    private void UISelect() {
        // Cargar imagen de fondo
        ImageIcon img = new ImageIcon("src/Vista/UI/bg1.gif");
        fondo.setIcon(img);
        fondo.setLayout(null); // Establecer LayoutManager nulo para posición absoluta

        add(fondo, BorderLayout.CENTER);

        // Crear el panel select con posición absoluta
        JPanel select = new JPanel();
        select.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        select.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        select.setBounds(132, 327, 463, 656); // Establecer la posición y tamaño del panel

        JLabel opc = new JLabel();
        ImageIcon im0 = new ImageIcon("src/Vista/UI/select1.gif");
        opc.setIcon(im0);
        opc.setBounds(0, 0, 462, 656); // Establecer la posición y tamaño de la etiqueta

        select.add(opc);
        fondo.add(select); // Añadir el panel select al fondo

        // Añadir el MouseListener al panel select
        select.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                personajeSeleccionado = "1";
                cont();
                System.out.println("1");
            }
        });

        // Crear el panel select0 con posición absoluta
        JPanel select0 = new JPanel();
        select0.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        select0.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        select0.setBounds(1320, 460, 479, 493);

        JLabel opc1 = new JLabel();
        ImageIcon im1 = new ImageIcon("src/Vista/UI/select2.gif");
        opc1.setIcon(im1);
        opc1.setBounds(0, 0, 479, 493);

        select0.add(opc1);
        fondo.add(select0); // Añadir el panel select al fondo

        // Añadir el MouseListener al panel select0
        select0.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                personajeSeleccionado = "2";
                cont();
                System.out.println("2");
            }
        });
    }

    private void cont(){
        JPanel panel = new JPanel();
        panel.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        panel.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        panel.setBounds(735, 553, 450, 152); // Establecer la posición y tamaño del panel
        button = new JLabel();
        ImageIcon btn = new ImageIcon("src/Vista/UI/btn0.png");
        button.setIcon(btn);
        button.setBounds(0, 0, 450, 152);
        panel.add(button);
        fondo.add(panel);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("1");
                try {
                    try {
                        load();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void load() throws InterruptedException, ClassNotFoundException{
        UDPBroadcastClient cliente = new UDPBroadcastClient();
        cliente.inicio(username, personajeSeleccionado);
    }
    
    public void battle(String key){
        fondo.removeAll();
        Map<String, String> Local = new HashMap<>();
        Local.put("1", "src/Vista/Avatares/Avatar1/Idle/idle_L.gif");
        Local.put("2", "src/Vista/Avatares/Avatar2/Idle/Idle_L.gif");
        
        Map<String, String> Visitante = new HashMap();
        Visitante.put("1", "src/Vista/Avatares/Avatar1/Idle/idle_R.gif");
        Visitante.put("2", "src/Vista/Avatares/Avatar2/Idle/Idle_R.gif");
        
        ImageIcon img = new ImageIcon("src/Vista/UI/Mapa.gif");
        fondo.setIcon(img);
        fondo.setLayout(null); // Establecer LayoutManager nulo para posición absoluta

        add(fondo, BorderLayout.CENTER);
        
        JPanel select0 = new JPanel();
        select0.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        select0.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        select0.setBounds(0, 0, 1920, 1080);

        JLabel opc1 = new JLabel();
        ImageIcon im1 = new ImageIcon(Local.get(personajeSeleccionado));
        opc1.setIcon(im1);
        opc1.setBounds(0, 0, 1920, 1080);

        select0.add(opc1);
        fondo.add(select0);
        
        // Crear el panel select con posición absoluta
        JPanel select = new JPanel();
        select.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        select.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        select.setBounds(0, 0, 1920, 1080); // Establecer la posición y tamaño del panel

        JLabel opc = new JLabel();
        ImageIcon im0 = new ImageIcon(Visitante.get(key));
        opc.setIcon(im0);
        opc.setBounds(0, 0, 1920, 1080); // Establecer la posición y tamaño de la etiqueta

        select.add(opc);
        fondo.add(select);
        UI();
    }
    
    private void UI(){
        // Crear el panel select con posición absoluta
        JPanel select = new JPanel();
        select.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        select.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        select.setBounds(0, 0, 1920, 1080); // Establecer la posición y tamaño del panel

        JLabel opc = new JLabel();
        ImageIcon im0 = new ImageIcon("src/Vista/UI/ui_vid");
        opc.setIcon(im0);
        opc.setBounds(0, 0, 1920, 1080); // Establecer la posición y tamaño de la etiqueta
        
        select.add(opc);
        fondo.add(select);
        
        Map<Integer, String> Local = new HashMap<>();
        Local.put(1, "src/Vista/UI/Vida/L/1.png");
        Local.put(2, "src/Vista/UI/Vida/L/2.png");
        Local.put(3, "src/Vista/UI/Vida/L/3.png");
        Local.put(4, "src/Vista/UI/Vida/L/4.png");
        Local.put(5, "src/Vista/UI/Vida/L/5.png");
        Local.put(6, "src/Vista/UI/Vida/L/6.png");
        
        Map<Integer, String> Visitante = new HashMap();
        Visitante.put(1, "src/Vista/UI/Vida/R/1.png");
        Visitante.put(2, "src/Vista/UI/Vida/R/2.png");
        Visitante.put(3, "src/Vista/UI/Vida/R/3.png");
        Visitante.put(4, "src/Vista/UI/Vida/R/4.png");
        Visitante.put(5, "src/Vista/UI/Vida/R/5.png");
        Visitante.put(6, "src/Vista/UI/Vida/R/6.png");
        
        
        JPanel vida = new JPanel();
        vida.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        vida.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        vida.setBounds(0, 0, 1920, 1080); // Establecer la posición y tamaño del panel

        JLabel heart = new JLabel();
        ImageIcon local = new ImageIcon(Local.get(1));
        heart.setIcon(local);
        heart.setBounds(0, 0, 1920, 1080);
        
        vida.add(heart);
        fondo.add(vida);
        
        JPanel vida0 = new JPanel();
        vida0.setOpaque(false); // Hacer el panel transparente para mostrar el fondo
        vida0.setLayout(null); // Establecer LayoutManager nulo para posición absoluta
        vida0.setBounds(0, 0, 1920, 1080); // Establecer la posición y tamaño del panel

        JLabel heart0 = new JLabel();
        ImageIcon Visit = new ImageIcon(Visitante.get(1));
        heart0.setIcon(Visit);
        heart0.setBounds(0, 0, 1920, 1080);
        
        vida.add(heart0);
        fondo.add(vida);
    }
}
