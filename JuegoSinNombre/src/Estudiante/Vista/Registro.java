package Estudiante.Vista;

import javax.swing.*;
import java.awt.*;
import Estudiante.*;

public class Registro extends JFrame {
    private JTextField usernameField;
    private JLabel fondo;
    private String username = ""; // Variable para almacenar el nombre

    public Registro() {
        super("Vista Estudiante");
        initializeUI();
    }

    private void initializeUI() {
        setBounds(10, 10, 1920, 1080);
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

        JLabel nameLabel = new JLabel("Ingrese su nombre:");
        usernameField = new JTextField(20);

        JButton submitButton = new JButton("Continuar");
        submitButton.addActionListener(e -> handleNameSubmission());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        txtPanel.add(nameLabel, gbc);

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
            // Aquí puedes agregar la lógica para continuar con el programa, por ejemplo, abrir una nueva ventana.
            
        }
    }
}
