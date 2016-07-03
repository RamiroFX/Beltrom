/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package empleado;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import Entities.M_funcionario;
import java.util.Date;

/**
 *
 * @author Ramiro
 */
public class C_crear_usuario extends MouseAdapter implements ActionListener, KeyListener {

    private V_crear_usuario vista;
    private M_crear_usuario modelo;

    public C_crear_usuario(M_crear_usuario modelo, V_crear_usuario vista) {
        this.modelo = modelo;
        this.vista = vista;
        inicializarVista();
        agregarListeners();
    }

    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista() {
        this.vista.setSize(800, 650);
        this.vista.setLocationRelativeTo(this.vista.getOwner());
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
        this.vista.jbCancelar.addActionListener(this);
        this.vista.jbAceptar.addActionListener(this);
        this.vista.jbQuitarRol.addActionListener(this);
        this.vista.jbAgregarRol.addActionListener(this);
        this.vista.jftCedulaIdentidad.addKeyListener(this);
        this.vista.jftSalario.addKeyListener(this);
        this.vista.jtfNombre.addMouseListener(this);
        this.vista.jtfApellido.addMouseListener(this);
        this.vista.jftCedulaIdentidad.addMouseListener(this);
        this.vista.dccFechaNacimiento.addMouseListener(this);
        this.vista.dccFechaIngreso.addMouseListener(this);
        this.vista.jpassword1.addMouseListener(this);
        this.vista.jpassword2.addMouseListener(this);
        this.vista.jtfAlias.addMouseListener(this);
    }

    /**
     * Agrega valores a los componentes.
     */
    private void inicializarVista() {
        this.vista.jtfIdFuncionario.setText("");
        Vector genero = modelo.obtenerGenero();
        for (int i = 0; i < genero.size(); i++) {
            this.vista.jcbGenero.addItem(genero.get(i));
        }
        Vector pais = modelo.obtenerPais();
        for (int i = 0; i < pais.size(); i++) {
            this.vista.jcbNacionalidad.addItem(pais.get(i));
        }
        Vector ciudad = modelo.obtenerCiudad();
        for (int i = 0; i < ciudad.size(); i++) {
            this.vista.jcbCiudad.addItem(ciudad.get(i));
        }
        Vector estadoCivil = modelo.obtenerEstadoCivil();
        for (int i = 0; i < estadoCivil.size(); i++) {
            this.vista.jcbEstadoCivil.addItem(estadoCivil.get(i));
        }
        Vector roles = modelo.obtenerRoles();
        for (Object role : roles) {
            this.vista.jcbRoles.addItem(role);
        }
        this.vista.jltRol.setModel(this.modelo.lmRol);
        this.vista.dccFechaIngreso.setDate(Calendar.getInstance().getTime());
        this.vista.jftCedulaIdentidad.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                        new javax.swing.text.NumberFormatter(
                                new java.text.DecimalFormat("#,##0"))));
        this.vista.jftSalario.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                        new javax.swing.text.NumberFormatter(
                                new java.text.DecimalFormat("#,##0"))));
    }

    private boolean isValidDataEntry() {
        /*
         * VALIDAR NOMBRE
         */
        String nombre;
        if (this.vista.jtfNombre.getText().isEmpty()) {
            this.vista.jtfNombre.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo nombre esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        } else {
            nombre = this.vista.jtfNombre.getText();

        }
        /*
         * VALIDAR APELLIDO
         */
        String apellido;
        if (this.vista.jtfApellido.getText().isEmpty()) {
            this.vista.jtfApellido.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo apellido esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        } else {
            apellido = this.vista.jtfApellido.getText();
        }
        /*
         *VALIDAR CI
         */
        Integer cedula;
        try {
            String LongToString = String.valueOf(this.vista.jftCedulaIdentidad.getValue());
            cedula = Integer.valueOf(LongToString.replace(".", ""));
        } catch (Exception e) {
            this.vista.jftCedulaIdentidad.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista, "Coloque un Numero de CI valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        }
        /*
         * VALIDAR FECHA NACIMIENTO
         */
        Date fechaNacimiento;
        try {
            fechaNacimiento = this.vista.dccFechaNacimiento.getDate();
        } catch (Exception e) {
            this.vista.dccFechaNacimiento.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Ingrese una fecha valida en el campo Fecha nacimiento",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        }
        /*
         * VALIDAR ALIAS
         */
        String alias;
        if (this.vista.jtfAlias.getText().isEmpty()) {
            this.vista.jtfAlias.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo alias esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosEmpresariales);
            return false;
        } else {
            alias = this.vista.jtfAlias.getText().toLowerCase();
        }
        /*
         *VALIDAR SALARIO
         */
        Integer salario;
        try {
            String valor = this.vista.jftSalario.getText();
            if (valor.isEmpty()) {
                salario = 0;
            } else {
                salario = Integer.valueOf(this.vista.jftSalario.getText());
            }
        } catch (NumberFormatException e) {
            this.vista.jftSalario.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Coloque un numero salario valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosEmpresariales);

            return false;
        }
        /*
         * VALIDAR TELEFONO
         */
        String telefono;
        try {
            String valor = this.vista.jtfNroTelefono.getText();
            if (valor.isEmpty()) {
                telefono = null;
            } else {
                telefono = this.vista.jtfNroTelefono.getText();
            }
        } catch (NumberFormatException e) {
            this.vista.jtfNroTelefono.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Coloque un numero de telefono valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales2);
            return false;
        }
        /*
         * VALIDAR CELULAR
         */
        String celular;
        try {
            String valor = this.vista.jtfNroCelular.getText();
            if (valor.isEmpty()) {
                celular = null;
            } else {
                celular = this.vista.jtfNroCelular.getText();
            }
        } catch (NumberFormatException e) {
            this.vista.jtfNroCelular.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Coloque un numero de celular valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales2);
            return false;
        }
        /*
         * VALIDAR FECHA INGRESOO
         */
        Date fechaIngreso;
        try {
            //Inicio fechas
            if (this.vista.dccFechaIngreso.getDate() == null) {
                throw new Exception(new NullPointerException());
            } else {
                fechaIngreso = this.vista.dccFechaIngreso.getDate();
            }
        } catch (Exception e) {
            this.vista.dccFechaIngreso.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Ingrese una fecha valida en el campo Fecha ingreso",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosEmpresariales);
            return false;
        }
        if (this.vista.jltRol.getModel().getSize() < 1) {
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Seleccione por lo menos un rol",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpRol);
            return false;
        }
        M_funcionario funcionario = new M_funcionario();
        String email = this.vista.jtfCorreoElectronico.getText();
        String direccion = this.vista.jtfDireccion.getText();
        String observacion = this.vista.jtaObservacion.getText();
        funcionario.setNombre(nombre);
        funcionario.setApellido(apellido);
        funcionario.setCedula(cedula);
        funcionario.setFecha_nacimiento(fechaNacimiento);
        funcionario.setAlias(alias);
        funcionario.setNro_telefono(telefono);
        funcionario.setNro_celular(celular);
        funcionario.setFecha_ingreso(fechaIngreso);
        funcionario.setEstado_civil((String) this.vista.jcbEstadoCivil.getSelectedItem());
        funcionario.setObservacion(observacion);
        funcionario.setDireccion(direccion);
        funcionario.setEmail(email);
        funcionario.setCiudad((String) this.vista.jcbCiudad.getSelectedItem());
        funcionario.setRol(null);//se establece en el modelo
        funcionario.setSexo((String) this.vista.jcbGenero.getSelectedItem());
        funcionario.setPais((String) this.vista.jcbNacionalidad.getSelectedItem());
        return true;
    }

    private void checkJFTcedula() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                String valorIngresado = vista.jftCedulaIdentidad.getText().replace(".", "");
                valorIngresado = valorIngresado.replace(",", "");
                Long StringToLong = null;
                try {
                    StringToLong = Long.valueOf(valorIngresado);
                } catch (NumberFormatException numberFormatException) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Ingrese solo numeros",
                            "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                }
                vista.jftCedulaIdentidad.setValue(StringToLong);
                String valorJFT = vista.jftCedulaIdentidad.getText();
                vista.jftCedulaIdentidad.select(valorJFT.length(), valorJFT.length());
            }
        });
    }

    private void checkJFTsalario() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                String valorIngresado = vista.jftSalario.getText().replace(".", "");
                Long StringToLong = null;
                try {
                    StringToLong = Long.valueOf(valorIngresado);
                } catch (NumberFormatException numberFormatException) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Ingrese solo numeros",
                            "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                }
                vista.jftSalario.setValue(StringToLong);
                String valorJFT = vista.jftSalario.getText();
                vista.jftSalario.select(valorJFT.length(), valorJFT.length());
            }
        });
    }

    private void crearUsuario() {
        if (isValidDataEntry()) {
            char[] password1 = vista.jpassword1.getPassword();
            char[] password2 = vista.jpassword2.getPassword();
            modelo.crearUsuario(password1, password2);
            cerrar();
        } else {
            JOptionPane.showMessageDialog(vista, "Failed", "Attention", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void agregarRol() {
        modelo.agregarRol(vista.jcbRoles.getSelectedItem().toString());
    }

    private void quitarRol() {
        modelo.quitarRol(vista.jltRol.getSelectedIndex());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.jbCancelar) {
            cerrar();
        } else if (e.getSource() == this.vista.jbAceptar) {
            crearUsuario();
        } else if (e.getSource() == this.vista.jbAgregarRol) {
            agregarRol();
        } else if (e.getSource() == this.vista.jbQuitarRol) {
            quitarRol();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.vista.jftCedulaIdentidad.hasFocus()) {
            this.vista.jftCedulaIdentidad.setBackground(Color.white);
        } else if (this.vista.jtfNombre.hasFocus()) {
            this.vista.jtfNombre.setBackground(Color.white);
        } else if (this.vista.jtfApellido.hasFocus()) {
            this.vista.jtfApellido.setBackground(Color.white);
        } else if (this.vista.jtfAlias.hasFocus()) {
            this.vista.jtfAlias.setBackground(Color.white);
        } else if (this.vista.dccFechaIngreso.hasFocus()) {
            this.vista.dccFechaIngreso.setBackground(Color.white);
        } else if (this.vista.dccFechaNacimiento.hasFocus()) {
            this.vista.dccFechaNacimiento.setBackground(Color.white);
        } else if (this.vista.jpassword1.hasFocus()) {
            this.vista.jpassword1.setBackground(Color.white);
        } else if (this.vista.jpassword2.hasFocus()) {
            this.vista.jpassword2.setBackground(Color.white);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.vista.jftCedulaIdentidad) {
            checkJFTcedula();
        } else if (e.getSource() == this.vista.jftSalario) {
            checkJFTsalario();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
