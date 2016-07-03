/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuPrincipal;

import beltrom.C_inicio;
import empleado.Gestion_empleado;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.swing.JInternalFrame;

/**
 *
 * @author Usuario
 */
public class C_MenuPrincipal implements ActionListener {

    public V_MenuPrincipal vista;
    public C_inicio inicio;

    public C_MenuPrincipal(V_MenuPrincipal vista, C_inicio inicio) {
        this.vista = vista;
        this.inicio = inicio;
        inicializarVista();
        agregarListeners();
    }

    private void inicializarVista() {
        java.util.Date date = new java.util.Date();
        String fechaInicio = new Timestamp(date.getTime()).toString().substring(0, 11);
        fechaInicio = fechaInicio + "00:00:00.000";
        String fechaFinal = new Timestamp(date.getTime()).toString().substring(0, 11);
        fechaFinal = fechaFinal + "23:59:59.000";
        Timestamp tsInicio = Timestamp.valueOf(fechaInicio);
        Timestamp tsFinal = Timestamp.valueOf(fechaFinal);
        this.vista.jftFecha.setValue(Calendar.getInstance().getTime());
    }

    private void agregarListeners() {
        this.vista.jbProveedores.addActionListener(this);
        this.vista.jbProducto.addActionListener(this);
        this.vista.jbMovimientos.addActionListener(this);
        this.vista.jbSalir.addActionListener(this);
        this.vista.jbEmpleados.addActionListener(this);
    }

    void mostrarVista() {
        this.vista.setVisible(true);
        this.inicio.agregarVentana(vista);
        this.vista.setLocation(this.inicio.centrarPantalla(this.vista));
        //this.inicio.centrarPantalla(vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src.equals(this.vista.jbEmpleados)) {
            Gestion_empleado ges_usuario = new Gestion_empleado(inicio);
            ges_usuario.mostrarVista();
        } else if (src.equals(this.vista.jbSalir)) {
            System.exit(0);
        }
    }

    public Point centrarPantalla(JInternalFrame i) {
        /*con este codigo centramos el panel en el centro del contenedor
         la anchura del contenedor menos la anchura de nuestro componente divido a 2
         lo mismo con la altura.*/
        return new Point((this.vista.getWidth() - i.getWidth()) / 2, (this.vista.getHeight() - i.getHeight() - 45) / 2);
    }

    public Dimension establecerTamañoPanel() {
        return new Dimension((int) (this.vista.getWidth() * 0.8), (int) (this.vista.getHeight() * 0.8));
    }
}
