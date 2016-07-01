/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionRol;

import DB_manager.DB_rol;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Ramiro
 */
public class C_gestion_rol extends MouseAdapter implements ActionListener {

    private V_gestion_rol vista;

    public C_gestion_rol(M_gestion_rol modelo, V_gestion_rol vista) {
        this.vista = vista;
        inicializarVista();
        agregarListeners();
    }

    private void agregarListeners() {
        this.vista.jbCrearRol.addActionListener(this);
        this.vista.jbModificarRol.addActionListener(this);
        this.vista.jbEliminarRol.addActionListener(this);
        this.vista.jtRoles.addMouseListener(this);
    }

    private void inicializarVista() {
        this.vista.jtRoles.setModel(DB_rol.consultarRoles(""));
    }

    public void mostrarVista() {
        this.vista.setVisible(true);
    }

    private void mostrarPermisos(int idRol) {
        this.vista.jtPermisos.setModel(DB_rol.consultarPermisos(idRol));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object e = ae.getSource();
        if (e.equals(this.vista.jbCrearRol)) {
            Crear_rol crear_rol = new Crear_rol(this.vista);
            crear_rol.mostrarVista();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        int fila = this.vista.jtRoles.rowAtPoint(me.getPoint());
        int columna = this.vista.jtRoles.columnAtPoint(me.getPoint());
        int idRol = (Integer.valueOf((String) this.vista.jtRoles.getValueAt(fila, 0)));
        mostrarPermisos(idRol);
        if ((fila > -1) && (columna > -1)) {
            this.vista.jbModificarRol.setEnabled(true);
            this.vista.jbEliminarRol.setEnabled(true);
        } else {
            this.vista.jbModificarRol.setEnabled(false);
            this.vista.jbEliminarRol.setEnabled(false);
        }
        if (me.getClickCount() == 2) {
            //modificar rol
        }
    }
}
