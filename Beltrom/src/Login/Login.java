/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import beltrom.C_inicio;

/**
 *
 * @author Ramiro Ferreira
 */
public class Login {

    private V_login vista = null;
    private C_login controlador = null;
    private M_login modelo = null;

    public Login(C_inicio c_inicio) {
        this.modelo = new M_login();
        this.vista = new V_login(c_inicio.vista);
        this.controlador = new C_login(this.modelo, this.vista, c_inicio);
    }

    public void mostrarVista() {
        controlador.mostrarVista();
    }
}
