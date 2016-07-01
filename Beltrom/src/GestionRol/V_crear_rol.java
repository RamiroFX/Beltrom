/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionRol;

import beltrom.V_inicio;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Ramiro
 */
public class V_crear_rol extends JDialog {

    private JLabel jlNombreRol;
    public JTextField jtfNombreRol;
    private JPanel jpPermisosSeleccionados, jpPermisosDisponibles;
    private JScrollPane jspPermisosSeleccionados, jspPermisosDisponibles;
    public JTable jtPermisosSeleccionados, jtPermisosDisponibles;
    public JButton jbQuitar, jbAgregar, jbAceptar, jbCancelar;

    public V_crear_rol(JFrame frame) {
        super(frame, "Crear rol", DEFAULT_MODALITY_TYPE);
        setName("crearRol");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(frame);
        inicializarComponentes();
    }
    
    public V_crear_rol(JDialog dialog) {
        super(dialog, "Crear rol", DEFAULT_MODALITY_TYPE);
        setName("crearRol");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(dialog);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        jlNombreRol = new JLabel("Nombre de rol");
        jtfNombreRol = new JTextField();
        jpPermisosSeleccionados = new JPanel(new BorderLayout());
        jpPermisosDisponibles = new JPanel(new BorderLayout());
        jtPermisosSeleccionados = new JTable();
        jtPermisosDisponibles = new JTable();
        jspPermisosSeleccionados = new JScrollPane(jtPermisosSeleccionados);
        jspPermisosDisponibles = new JScrollPane(jtPermisosDisponibles);
        jbQuitar = new JButton("Quitar");
        jbAgregar = new JButton("Agregar");
        jbAceptar = new JButton("Aceptar");
        jbCancelar = new JButton("Cancelar");

        JPanel jpSouth = new JPanel();
        jpSouth.add(jbAceptar);
        jpSouth.add(jbCancelar);

        jpPermisosDisponibles.add(jspPermisosDisponibles, BorderLayout.CENTER);
        jpPermisosDisponibles.add(jbQuitar, BorderLayout.SOUTH);
        jpPermisosSeleccionados.add(jspPermisosSeleccionados, BorderLayout.CENTER);
        jpPermisosSeleccionados.add(jbAgregar, BorderLayout.SOUTH);

        JPanel jpCenter = new JPanel(new GridLayout(1, 2));
        jpSouth.add(jpPermisosDisponibles);
        jpSouth.add(jpPermisosSeleccionados);

        JPanel jpNorth = new JPanel(new BorderLayout());
        jpNorth.add(jlNombreRol, BorderLayout.EAST);
        jpNorth.add(jtfNombreRol, BorderLayout.WEST);

        getContentPane().add(jpNorth, BorderLayout.NORTH);
        getContentPane().add(jpCenter, BorderLayout.CENTER);
        getContentPane().add(jpSouth, BorderLayout.SOUTH);
    }

}
