/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionRol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Ramiro
 */
public class C_crear_rol implements ActionListener {

    M_crear_rol modelo;
    V_crear_rol vista;

    public C_crear_rol(M_crear_rol modelo, V_crear_rol vista) {
        this.modelo = modelo;
        this.vista = vista;
        inicializarVista();
        agregarListeners();
    }

    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista() {
        this.vista.setVisible(true);
    }

    /**
     * Elimina la vista.
     */
    private void cerrar() {
        this.vista.dispose();
        System.runFinalization();
    }

    /**
     * Agrega ActionListeners los controles.
     */
    private void agregarListeners() {
        this.vista.jbAceptar.addActionListener(this);
        this.vista.jbAgregar.addActionListener(this);
        this.vista.jbCancelar.addActionListener(this);
        this.vista.jbQuitar.addActionListener(this);
    }

    /**
     * Agrega valores a los componentes.
     */
    private void inicializarVista() {
        this.vista.jtPermisosDisponibles.setModel(modelo.obtenerAccesosDispon());
    }

    private void crearRol() {
        this.modelo.crearRol(this.vista.jtfNombreRol.getText());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.jbCancelar) {
            cerrar();
        }
        if (e.getSource() == this.vista.jbAceptar) {
            crearRol();
        }
        if (e.getSource() == this.vista.jbCancelar) {
            cerrar();
        }
    }

}
