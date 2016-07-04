/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empleado;

import DB_manager.DB_manager;
import DB_manager.DB_rol;
import DB_manager.ResultSetTableModel;
import Entities.M_funcionario;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Ramiro
 */
class M_modificar_usuario {


    public boolean isPasswordCorrect(char[] charPasswordTemp, char[] charPasswordTemp2) {
        if (charPasswordTemp.length == 0 || charPasswordTemp2.length == 0) {
            return false;
        }
        return Arrays.equals(charPasswordTemp, charPasswordTemp2);
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

    public ResultSetTableModel obtenerRolesDisp() {
        return DB_rol.consultarRoles("");
    }

    boolean modificarUsuario(M_funcionario funcionario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void quitarRol(int idRol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    TableModel obtenerRolesSelecc() {
        return null;
    }
}
