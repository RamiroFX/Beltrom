/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empleado;

import DB_manager.DB_Funcionario;
import DB_manager.DB_manager;
import DB_manager.DB_rol;
import DB_manager.ResultSetTableModel;
import Entities.M_funcionario;
import Entities.M_rol;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Ramiro
 */
class M_modificar_usuario {

    private M_funcionario funcionario;

    public M_modificar_usuario(int idEmpleado) {
        this.funcionario = DB_Funcionario.obtenerDatosFuncionarioID(idEmpleado);
    }

    /**
     * @return the funcionario
     */
    public M_funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(M_funcionario funcionario) {
        this.funcionario = funcionario;
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

    private boolean verificarDatosEmpleado(M_funcionario funcionario) {
        return DB_Funcionario.existeEmpleado(funcionario);
    }

    boolean modificarUsuario(M_funcionario funcionario) {
        if (!verificarDatosEmpleado(funcionario)) {
            DB_Funcionario.actualizarFuncionario(funcionario);
            JOptionPane.showMessageDialog(null, "Usuario modificado", "Atención", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "El número de cedula o el Alias seleccionado se encuentra en uso.", "Atención", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void quitarRol(int idRol) {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea quitar el rol seleccionado?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            DB_Funcionario.quitarRol(getFuncionario().getId_funcionario(), idRol);
        }
    }

    public ResultSetTableModel obtenerRolesSelecc() {
        return DB_Funcionario.consultarRolUsuario(getFuncionario().getId_funcionario());
    }

    public void agregarRol(int idRol, String rolDescripcion) {
        ///agregar filtros para datos repetidos
        Vector roles = DB_Funcionario.obtenerRolFuncionario(getFuncionario());
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).toString().equals(rolDescripcion)) {
                JOptionPane.showMessageDialog(null, "El Rol seleccionado ya se ha encuentra", "Atención", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        M_rol rol = new M_rol(idRol, rolDescripcion);
        DB_Funcionario.insertarRolUsuario(getFuncionario(), rol);
    }
}
