package Estudiante.Vista;

import javax.swing.*;


import java.awt.*;
//import models.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Registro extends JFrame {
    private JLabel button;
    private JTextField usernameField;
    private JLabel fondo;
    private String username = ""; // Variable para almacenar el nombre
    private String personajeSeleccionado = "";


    public Registro() {
        super("Vista Estudiante");
        initializeUI();
    }

    private void initializeUI() {
        setBounds(0, 0, 1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar fondo con imagen
        fondo = new JLabel();
        fondo.setLayout(new GridBagLayout());
        ImageIcon img = new ImageIcon("src/Estudiante/Vista/UI/bg0.png");
        fondo.setIcon(new ImageIcon(img.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH)));
        add(fondo, BorderLayout.CENTER);

        // Panel para el nombre
        JPanel txtPanel = new JPanel();
        txtPanel.setOpaque(false);
        txtPanel.setLayout(new GridBagLayout());

        //JLabel nameLabel = new JLabel("Ingrese su nombre:");
        usernameField = new JTextField(20);

        JButton submitButton = new JButton("Continuar");
        submitButton.addActionListener(e -> handleNameSubmission());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        //txtPanel.add(gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        txtPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        txtPanel.add(submitButton, gbc);

        fondo.add(txtPanel, new GridBagConstraints());

        setVisible(true);
    }

    private void handleNameSubmission() {
        username = usernameField.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese su nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nombre ingresado: " + username);
            UISelect();            
        }
    }
    private void UISelect() {
        JLabel opc;
        Map<Integer, String> Seleccion = new HashMap<>();
        Seleccion.put(1, "src/Estudiante/Vista/Avatares/Avatar1/Idle/idle_R.gif");
        Seleccion.put(2, "src/Estudiante/Vista/Avatares/Avatar2/Idle/Idle_L.gif");

        fondo.removeAll();
        ImageIcon img = new ImageIcon("src/Estudiante/Vista/UI/bg1.png");
        fondo.setIcon(new ImageIcon(img.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH)));

        // Panel inferior para el botón y la etiqueta de personaje seleccionado
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());
        
        JButton continuarButton = new JButton("Continuar");
        continuarButton.setVisible(false);
        
        JLabel personajeLabel = new JLabel("");
        
        panelInferior.add(personajeLabel);
        panelInferior.add(continuarButton);
        add(panelInferior, BorderLayout.SOUTH);

        // Añadir personajes al fondo
        for (Map.Entry<Integer, String> entry : Seleccion.entrySet()) {
            opc = new JLabel();
            ImageIcon icon = new ImageIcon(entry.getValue());
            opc.setIcon(new ImageIcon(icon.getImage().getScaledInstance(267, 441, Image.SCALE_SMOOTH)));
            opc.setName("Personaje " + entry.getKey());

            // Añadir listener de clic para cada personaje
            opc.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    personajeSeleccionado = label.getName();
                    personajeLabel.setText("Personaje seleccionado: " + personajeSeleccionado);
                    continuarButton.setVisible(true);
                }
            });

            fondo.add(opc);
        }

        fondo.revalidate();
        fondo.repaint();
    }

    private void cont(){
        button = new JLabel();
        ImageIcon btn = new ImageIcon("src/Estudiante/Vista/UI/btn0.png");
        button.setIcon(new ImageIcon(btn.getImage().getScaledInstance(341, 107, Image.SCALE_SMOOTH)));
        fondo.add(button);
    }
}
