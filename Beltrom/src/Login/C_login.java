/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import beltrom.C_inicio;
import com.nitido.utils.toaster.Toaster;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_login implements ActionListener, KeyListener {

    M_login modelo;
    V_login vista;
    C_inicio c_main;

    public C_login(M_login modelo, V_login vista, C_inicio c_main) {
        this.c_main = c_main;
        this.modelo = modelo;
        this.vista = vista;
        inicializarVista();
        agregarListeners();
    }

    private void inicializarVista() {
        //this.vista.txtNombre.setText(this.modelo.configuracion.getUser());
        this.vista.txtNombre.setText("admin");
        this.vista.txtPassword.setText("admin");
        this.vista.txtPassword.requestFocusInWindow();
    }

    public void mostrarVista() {
        this.vista.setSize(400, 200);
        this.vista.setLocation(this.c_main.centrarPantalla(this.vista));
        c_main.agregarVentana(vista);
        this.vista.btnAceptar.requestFocusInWindow();
    }

    public void eliminarVista() {
        vista.dispose();
        vista = null;
    }

    private void agregarListeners() {
        vista.btnAceptar.addActionListener(this);
        vista.btnAceptar.addKeyListener(this);
        vista.btnSalir.addActionListener(this);
        vista.btnSalir.addKeyListener(this);
        vista.txtNombre.addActionListener(this);
        vista.txtPassword.addActionListener(this);
    }

    private void crearSeleccionRol() {
        seleccionarRol seleccionarRol = new seleccionarRol(c_main, c_main.getFuncionario());
        seleccionarRol.mostrarVista();
    }

    private void mostrarMensaje(String message) {
        Toaster popUp = new Toaster();
        popUp.showToaster(message);
    }

    public void logIn() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                c_main.setFuncionario(modelo.funcionario);
                c_main.getFuncionario().setAlias(vista.txtNombre.getText());
                //modelo.configuracion.setUser(c_main.getFuncionario().getAlias());
                //direccion de base de datos
                String url = vista.Conexion;
                //alias de usuario
                String user = c_main.getFuncionario().getAlias();
                //contraseña
                String password = "";
                char[] pss = vista.txtPassword.getPassword();
                for (char c : pss) {
                    password = password + c;
                }
                //se conecta contra la base de datos
                if (modelo.conectar("postgres", "postgres")) {
                    if (modelo.verificarUsuario(user, password)) {
                        c_main.setFuncionario(modelo.funcionario);
                        mostrarMensaje("La conexion se ha establecido con exito \n"
                                + "Bienvenido " + c_main.getFuncionario().getNombre() + " " + c_main.getFuncionario().getApellido() + "!");
                    //una vez correcto el nombre/contraseña se elimina la pantalla de logeo
                        //y se procede a mostrar la pantalla de seleccion de rol
                        vista.dispose();
                        vista = null;
                        crearSeleccionRol();
                    //se habilita la opcion de deslogeo
                        //c_main.vista.getJMbarraMenu().jmiLogOut.setEnabled(true);
                        //se coloca en la barra de menu el nombre de usuario
                        System.out.println("modelo.obtenerNombreUsuario(): " + modelo.obtenerNombreUsuario());
                        c_main.vista.setJtfUsuario(modelo.obtenerNombreUsuario());
                    }
                } else {
                    mostrarMensaje("Atencion\n"
                            + "Usuario y/o contraseña incorrectos");
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (vista != null) {
            if (vista.btnAceptar == e.getSource()) {
                logIn();
            } else if (vista.btnSalir == e.getSource()) {
                System.exit(0);
            }
            if (e.getSource() == vista.txtNombre) {
                vista.txtPassword.requestFocus();
            }
            if (e.getSource() == vista.txtPassword) {
                vista.btnAceptar.requestFocus();
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (this.vista.btnAceptar.hasFocus()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                logIn();
            }
        }
        if (this.vista.btnSalir.hasFocus()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                System.exit(0);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
