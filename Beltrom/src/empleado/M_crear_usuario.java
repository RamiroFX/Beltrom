/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package empleado;

import DB_manager.DB_Funcionario;
import DB_manager.DB_manager;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import Entities.M_funcionario;
import Entities.M_rol;
import java.util.Vector;

/**
 *
 * @author Administrador
 */
public class M_crear_usuario {

    M_funcionario m_funcionario;
    DefaultListModel lmRol;
    String password;
    String nombreImagen;
    ImageIcon imagen;
    File fileImage;
    ArrayList roles;

    public M_crear_usuario() {
        m_funcionario = new M_funcionario();
        lmRol = new DefaultListModel();
        password = "";
        nombreImagen = "";
        imagen = new ImageIcon();
        fileImage = null;
        roles = new ArrayList();
    }

    public void agregarRol(String rol) {
        String rolActual = rol;
        String rolLista[] = new String[lmRol.getSize()];
        for (int i = 0; i < rolLista.length; i++) {
            rolLista[i] = lmRol.getElementAt(i).toString();
        }
        boolean existe = false;
        for (int i = 0; i < rolLista.length; i++) {
            if (rolLista[i].equals(rolActual)) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            lmRol.addElement(rolActual);
        }
    }

    public void quitarRol(int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex >= lmRol.size()) {
            lmRol.remove(selectedIndex);
        }
    }

    public boolean establecerRoles() {
        if (lmRol.isEmpty()) {
            /*JOptionPane.showMessageDialog(v_jdCreUsu, "Escoja un rol", "Parametros incompletos", JOptionPane.ERROR_MESSAGE, null);
             this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpRol);*/
            return false;
        }
        String nombreRol = "";
        roles.clear();
        for (int i = 0; i < lmRol.size(); i++) {
            nombreRol = (String) lmRol.getElementAt(i);
            M_rol rol = DB_Funcionario.obtenerRol(nombreRol);
            roles.add(rol);
        }
        return true;
    }

    public boolean isPasswordCorrect(char[] charPasswordTemp, char[] charPasswordTemp2) {
        if (charPasswordTemp.length == 0 || charPasswordTemp2.length == 0) {
            return false;
        }
        if (Arrays.equals(charPasswordTemp, charPasswordTemp2)) {
            password = String.copyValueOf(charPasswordTemp);
            return true;
        } else {
            return false;
        }
    }

    public boolean crearUsuario(char[] charPasswordTemp, char[] charPasswordTemp2) {
        if (isPasswordCorrect(charPasswordTemp, charPasswordTemp2) && establecerRoles()) {
            DB_Funcionario.insertarFuncionarioFX(m_funcionario, roles, password);
            return true;
        }
        return false;
    }

    public Vector obtenerGenero() {
        return DB_manager.obtenerGenero();
    }

    public Vector obtenerPais() {
        return DB_manager.obtenerPais();
    }

    public Vector obtenerCiudad() {
        return DB_manager.obtenerCiudad();
    }

    public Vector obtenerEstadoCivil() {
        return DB_manager.obtenerEstadoCivil();
    }

    public Vector obtenerRoles() {
        return DB_manager.obtenerRoles();
    }
}
