/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package empleado;

import Utilities.c_packColumn;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Vector;
import GestionRol.Gestion_rol;
import Interface.Gestion;
import beltrom.C_inicio;
import java.awt.event.KeyEvent;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_gestion_usuario implements Gestion {

    public C_inicio c_main;
    M_gestion_usuario modelo;
    V_gestion_usuario vista;

    public C_gestion_usuario(M_gestion_usuario modelo, V_gestion_usuario vista, C_inicio c_main) {
        this.c_main = c_main;
        this.modelo = modelo;
        this.vista = vista;
        inicializarVista();
        agregarListeners();
    }

    @Override
    public final void inicializarVista() {
        c_packColumn.packColumns(this.vista.jtUsuario, 2);
        this.vista.jbModificarUsuario.setEnabled(false);
        this.vista.jftCedulaIdentidad.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                new java.text.DecimalFormat("#,##0"))));
    }

    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista() {
        this.vista.setSize(this.c_main.establecerTamañoPanel());
        this.vista.setLocation(this.c_main.centrarPantalla(this.vista));
        this.c_main.agregarVentana(this.vista);
    }

    /**
     * Elimina la vista.
     */
    @Override
    public final void cerrar() {
        this.vista.dispose();
        System.runFinalization();
    }

    /**
     * Agrega ActionListeners los controles.
     */
    @Override
    public final void agregarListeners() {
        this.vista.jbCrearUsuario.addActionListener(this);
        this.vista.jbModificarUsuario.addActionListener(this);
        this.vista.jtUsuario.addMouseListener(this);
        this.vista.jtfBuscar.addKeyListener(this);
        this.vista.jbGestionRol.addActionListener(this);
        this.vista.jckbCedula.addActionListener(this);
        this.vista.jckbNombreApellido.addActionListener(this);
        this.vista.jrbExclusivo.addActionListener(this);
        this.vista.jrbInclusivo.addActionListener(this);
        this.vista.jbCambiarPassword.addActionListener(this);
    }

    public void displayQueryResults() {
        /*
         * Para permitir que los mensajes puedan ser desplegados, no se ejecuta
         * el query directamente, sino que se lo coloca en una cola de eventos
         * para que se ejecute luego de los eventos pendientes.
         */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*
                 * Se utiliza el objeto factory para obtener un TableModel
                 * para los resultados del query.
                 */
                String busqueda = vista.jtfBuscar.getText().toLowerCase();
                boolean isExclusivo = false;
                boolean entidad = false;
                boolean ruc = false;
                if (vista.jrbExclusivo.isSelected()) {
                    isExclusivo = true;
                }
                if (vista.jckbNombreApellido.isSelected()) {
                    entidad = true;
                }
                if (vista.jckbCedula.isSelected()) {
                    ruc = true;
                }
                vista.jtUsuario.setModel(modelo.consultarFuncionario(busqueda, isExclusivo, entidad, ruc));
                c_packColumn.packColumns(vista.jtUsuario, 2);
                vista.jbModificarUsuario.setEnabled(false);
            }
        });
    }

    private void cambiarContraseña() {
        CambiarPassword cp = new CambiarPassword(this.c_main.vista, c_main);
        cp.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.jbCrearUsuario) {
            Crear_empleado crearEmpleado = new Crear_empleado(c_main);
            crearEmpleado.mostrarVista();
        } else if (e.getSource() == this.vista.jbModificarUsuario) {
            int row = this.vista.jtUsuario.getSelectedRow();
            int idEmpleado = Integer.valueOf(String.valueOf(this.vista.jtUsuario.getValueAt(row, 0)));
            Modificar_empleado modEmpleado = new Modificar_empleado(c_main, idEmpleado);
            modEmpleado.mostrarVista();
        } else if (e.getSource() == this.vista.jbCambiarPassword) {
            cambiarContraseña();
        } else if (e.getSource() == this.vista.jbGestionRol) {
            Gestion_rol gestion_rol = new Gestion_rol(c_main);
            gestion_rol.mostrarVista();
        } else if (e.getSource() == this.vista.jrbExclusivo) {
            displayQueryResults();
        } else if (e.getSource() == this.vista.jrbInclusivo) {
            displayQueryResults();
        } else if (e.getSource() == this.vista.jckbCedula) {
            displayQueryResults();
        } else if (e.getSource() == this.vista.jckbNombreApellido) {
            displayQueryResults();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.vista.jtUsuario.rowAtPoint(e.getPoint());
        int columna = this.vista.jtUsuario.columnAtPoint(e.getPoint());
        int idFuncionario = (Integer.valueOf((String) this.vista.jtUsuario.getValueAt(fila, 0)));
        //modelo.setFuncionario(DB_Funcionario.obtenerDatosFuncionarioID(idFuncionario));
        modelo.setFuncionario(modelo.obtenerDatosFuncionarioID(idFuncionario));
        if ((fila > -1) && (columna > -1)) {
            this.vista.jbModificarUsuario.setEnabled(true);
            //this.v_jifGesUsu.jbEliminar.setEnabled(true);
            this.vista.jtfAlias.setText(modelo.getFuncionario().getAlias());
            this.vista.jtfDireccion.setText(modelo.getFuncionario().getDireccion());
            this.vista.jtfCorreoElectronico.setText(modelo.getFuncionario().getEmail());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.vista.jtfFechaIngreso.setText(sdf.format(modelo.getFuncionario().getFecha_ingreso()));
            } catch (java.lang.NullPointerException ex) {
                this.vista.jtfFechaIngreso.setText("");
            }
            try {
                this.vista.jtfFechaNacimiento.setText(sdf.format(modelo.getFuncionario().getFecha_nacimiento()));
            } catch (java.lang.NullPointerException ex) {
                this.vista.jtfFechaNacimiento.setText("");
            }
            this.vista.jtaObservacion.setText(modelo.getFuncionario().getObservacion());
            this.vista.jtfNroCelular.setText(modelo.getFuncionario().getNro_celular());
            this.vista.jtfNroTelefono.setText(modelo.getFuncionario().getNro_telefono());
            //Datos personales
            this.vista.jtfNombre.setText(modelo.getFuncionario().getNombre());
            this.vista.jtfApellido.setText(modelo.getFuncionario().getApellido());
            this.vista.jftCedulaIdentidad.setValue(modelo.getFuncionario().getCedula());
            this.vista.jtfGenero.setText(modelo.getFuncionario().getSexo());
            this.vista.jtfEstadoCivil.setText(modelo.getFuncionario().getEstado_civil());
            this.vista.jtfNacionalidad.setText(modelo.getFuncionario().getPais());
            this.vista.jtfCiudad.setText(modelo.getFuncionario().getCiudad());
            this.vista.jcbRol.removeAllItems();
            Vector vRolUsuario = modelo.obtenerRolFuncionario();
            for (int i = 0; i < vRolUsuario.size(); i++) {
                this.vista.jcbRol.addItem(vRolUsuario.get(i));
            }
        } else {
            this.vista.jbModificarUsuario.setEnabled(false);
        }
        if (e.getClickCount() == 2) {
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        displayQueryResults();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
