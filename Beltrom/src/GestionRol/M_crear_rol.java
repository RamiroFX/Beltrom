/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionRol;

import DB_manager.DB_rol;
import DB_manager.ResultSetTableModel;
import Entities.M_rol;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Ramiro
 */
public class M_crear_rol {

    private M_rol rol;

    public M_crear_rol() {
        this.rol = new M_rol();
    }

    /**
     * @return the rol
     */
    public M_rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(M_rol rol) {
        this.rol = rol;
    }

    public void crearRol(String nombreRol) {
        //maximo 30 caracteres
        if (nombreRol.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo nombre rol esta vacío", "Atención", JOptionPane.ERROR_MESSAGE);

        } else {
            if (nombreRol.length() > 30) {
                JOptionPane.showMessageDialog(null, "El campo nombre rol puede contener hasta 30 caracteres", "Atención", JOptionPane.ERROR_MESSAGE);
            } else {
                if (DB_rol.existeRol(nombreRol)) {
                    javax.swing.JOptionPane.showMessageDialog(null, "El nombre del Rol se encuentra en uso. Verifique el nombre del Rol", "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                } else {
                    DB_rol.crearRol(nombreRol);
                }
            }
        }
    }

    public ResultSetTableModel obtenerAccesosDispon() {
        return DB_rol.obtenerAccesosDisponibles();
    }

}
