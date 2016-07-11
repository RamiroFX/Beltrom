/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import beltrom.C_inicio;

/**
 *
 * @author Administrador
 */
public class SeleccionarRol {

    private C_seleccionarRol controlador;
    private V_jifSeleccionarRol vista;
    private C_inicio c_inicio;

    public SeleccionarRol(C_inicio c_main) {
        this.c_inicio = c_main;
        vista = new V_jifSeleccionarRol();
        controlador = new C_seleccionarRol(vista, this.c_inicio);
    }

    /**
     * @return the selecRol
     */
    public C_seleccionarRol getSelecRol() {
        return controlador;
    }

    /**
     * @param selecRol the selecRol to set
     */
    public void setSelecRol(C_seleccionarRol selecRol) {
        this.controlador = selecRol;
    }

    /**
     * @return the jifSelRol
     */
    public V_jifSeleccionarRol getJifSelRol() {
        return vista;
    }

    /**
     * @param jifSelRol the jifSelRol to set
     */
    public void setJifSelRol(V_jifSeleccionarRol jifSelRol) {
        this.vista = jifSelRol;
    }

    public void mostrarVista() {
        controlador.mostrarVista();
    }
}
