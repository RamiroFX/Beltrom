/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package empleado;

import DB_manager.DB_Funcionario;
import DB_manager.DB_manager;
import DB_manager.DB_rol;
import DB_manager.ResultSetTableModel;
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
import Utils.ArrayListTableModel;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrador
 */
public class M_crear_usuario {

    M_funcionario m_funcionario;
    String password;
    String nombreImagen;
    ArrayList<M_rol> roles;
    M_rol rol;

    public M_crear_usuario() {
        m_funcionario = new M_funcionario();
        password = "";
        nombreImagen = "";
        roles = new ArrayList();
    }

    public boolean establecerRoles() {
        if (roles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Escoja un rol como mínimo", "Parametros incompletos", JOptionPane.ERROR_MESSAGE, null);
            return false;
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

    public boolean crearUsuario(M_funcionario funcionario, char[] charPasswordTemp, char[] charPasswordTemp2) {
        if (isPasswordCorrect(charPasswordTemp, charPasswordTemp2) && establecerRoles()) {
            DB_Funcionario.insertarFuncionarioFX(funcionario, roles, password);
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

    public ArrayListTableModel obtenerRolesSelecc() {
        ArrayListTableModel model = new ArrayListTableModel();
        model.addColumn("Id");
        model.addColumn("Rol");
        for (int i = 0; i < roles.size(); i++) {
            Object[] fila = {roles.get(i).getId(),
                roles.get(i).getDescripcion()};
            model.addRow(fila);
        }
        return model;
    }

    void agregarRol(int idRol, String descripcion) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getId() == idRol) {
                JOptionPane.showMessageDialog(null, "El Rol seleccionado ya se ha encuentra", "Atención", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        M_rol menu_item = new M_rol(idRol, descripcion);
        roles.add(menu_item);
    }

    void quitarRol(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getId() == idRol) {
                roles.remove(i);
                return;
            }
        }
    }

    public ResultSetTableModel obtenerRolesDisp() {
        return DB_rol.consultarRoles("");
    }
}
