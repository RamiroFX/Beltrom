/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_manager;

import Entities.M_menu_item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author Ramiro
 */
public class DB_rol {

    private static Statement st = null;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;

    public static ResultSetTableModel consultarRoles(String busqueda) {
        ResultSetTableModel rstm = null;
        try {
            if (DB_manager.getConection() == null) {
                throw new IllegalStateException("Connection already closed.");
            }
            String SELECT = "SELECT ID_ROL\"ID\",DESCRIPCION \"Descripcion\" FROM ROL"
                    + " WHERE DESCRIPCION LIKE '" + busqueda + "%'";
            String ORDER_BY = " ORDER BY DESCRIPCION ";

            String query = SELECT + ORDER_BY;
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // se ejecuta el query y se obtienen los resultados en un ResultSet
            rs = st.executeQuery(query);
            rstm = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } /*finally {
         try {
         if (rs != null) {
         rs.close();
         }
         if (st != null) {
         st.close();
         }
         } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
         lgr.log(Level.WARNING, ex.getMessage(), ex);
         } /*finally {
         try {
         if (rs != null) {
         rs.close();
         }
         if (st != null) {
         st.close();
         }
         } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
         lgr.log(Level.WARNING, ex.getMessage(), ex);
         }
         }*/

        return rstm;
    }

    public static TableModel consultarPermisos(int idRol) {

        ResultSetTableModel rstm = null;
        try {
            if (DB_manager.getConection() == null) {
                throw new IllegalStateException("Connection already closed.");
            }
            String SELECT = "SELECT MI.DESCRIPCION \"Descripción\" "
                    + "FROM PERMISO_ROL PR, MENU_ITEM MI, ROL R, MENU M "
                    + "WHERE PR.ID_ROL = R.ID_ROL "
                    + "AND PR.ID_MENU = M.ID_MENU "
                    + "AND PR.ID_MENU_ITEM = MI.ID_MENU_ITEM "
                    + "AND R.ID_ROL = " + idRol + " ";
            String ORDER_BY = " ORDER BY MI.DESCRIPCION; ";

            String query = SELECT + ORDER_BY;
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // se ejecuta el query y se obtienen los resultados en un ResultSet
            rs = st.executeQuery(query);
            rstm = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } /*finally {
         try {
         if (rs != null) {
         rs.close();
         }
         if (st != null) {
         st.close();
         }
         } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
         lgr.log(Level.WARNING, ex.getMessage(), ex);
         } /*finally {
         try {
         if (rs != null) {
         rs.close();
         }
         if (st != null) {
         st.close();
         }
         } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
         lgr.log(Level.WARNING, ex.getMessage(), ex);
         }
         }*/

        return rstm;
    }

    public static boolean existeRol(String nombreRol) {
        String Query = "SELECT DESCRIPCION FROM ROL "
                + "WHERE DESCRIPCION LIKE '" + nombreRol + "'";
        try {
            st = DB_manager.getConection().createStatement();
            rs = st.executeQuery(Query);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }

    public static long crearRol(String nombreRol) {
        long id_producto = -1L;
        String stm = "INSERT INTO ROL(DESCRIPCION)VALUES (?);";
        try {
            pst = DB_manager.getConection().prepareStatement(stm, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, nombreRol);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs != null && rs.next()) {
                id_producto = rs.getLong(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    System.err.println("no se pudo agregar nuevo rol");
                }
            }
            System.err.println("no se pudo agregar nuevo rol");
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.err.println("no se pudo agregar nuevo rol");
            }
        }
        return id_producto;
    }

    public static ResultSetTableModel obtenerAccesosDisponibles() {
        ResultSetTableModel rstm = null;
        try {
            if (DB_manager.getConection() == null) {
                throw new IllegalStateException("Connection already closed.");
            }
            String query = "SELECT M.ID_MENU \"Id\", M.DESCRIPCION \"Descripción\", "
                    + "MI.ID_MENU_ITEM \"Id\", MI.DESCRIPCION \"Descripción\" "
                    + "FROM MENU_ITEM MI, MENU M "
                    + "WHERE MI.ID_MENU = M.ID_MENU "
                    + "ORDER BY MI.ID_MENU_ITEM";
            //SELECT prod_id_producto   "ID producto"  ,  prod_descripcion  "Descripcion"   FROM producto
            //se crea una sentencia
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // se ejecuta el query y se obtienen los resultados en un ResultSet
            rs = st.executeQuery(query);
            rstm = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_rol.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return rstm;
    }

    public static long crearRol(String nombreRol, ArrayList<M_menu_item> accesos) {

        String INSERT_PERMISO_ROL = "INSERT INTO PERMISO_ROL(ID_ROL, ID_MENU, ID_MENU_ITEM)VALUES (?, ?, ?);";
        String INSERT_ROL = "INSERT INTO ROL(DESCRIPCION)VALUES (?);";
        long SQ_ROL = -1L;
        try {
            DB_manager.getConection().setAutoCommit(false);
            pst = DB_manager.getConection().prepareStatement(INSERT_ROL, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, nombreRol);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs != null && rs.next()) {
                SQ_ROL = rs.getLong(1);
            }
            pst.close();
            rs.close();
            for (int i = 0; i < accesos.size(); i++) {
                pst = DB_manager.getConection().prepareStatement(INSERT_PERMISO_ROL);
                pst.setInt(1, (int) SQ_ROL);
                pst.setInt(2, (int) accesos.get(i).getIdMenu());
                pst.setInt(3, (int) accesos.get(i).getItemId());
                pst.executeUpdate();
                pst.close();
            }
            //pst.setInt(1, egreso_cabecera.getId_cabecera());
            /*try {
             if (egreso_cabecera.getNro_factura() == null) {
             pst.setNull(1, Types.NUMERIC);
             } else {
             pst.setInt(1, egreso_cabecera.getNro_factura());
             }
             } catch (Exception e) {
             pst.setNull(1, Types.NUMERIC);
             }
             pst.setInt(2, egreso_cabecera.getId_proveedor());
             pst.setInt(3, egreso_cabecera.getId_empleado());
             pst.setTimestamp(4, egreso_cabecera.getTiempo());
             pst.setInt(5, egreso_cabecera.getId_condVenta());
             pst.executeUpdate();
             rs = pst.getGeneratedKeys();
             if (rs != null && rs.next()) {
             sq_egreso_cabecera = rs.getLong(1);
             }
             pst.close();
             rs.close();
             for (int i = 0; i < egreso_detalle.length; i++) {
             pst = DB_manager.getConection().prepareStatement(INSERT_PERMISO_ROL);
             pst.setInt(1, (int) sq_egreso_cabecera);
             pst.setInt(2, egreso_detalle[i].getId_producto());
             pst.setDouble(3, egreso_detalle[i].getCantidad());
             pst.setInt(4, egreso_detalle[i].getPrecio());
             pst.setDouble(5, egreso_detalle[i].getDescuento());
             pst.setInt(6, egreso_detalle[i].getIva_exenta());
             pst.setInt(7, egreso_detalle[i].getIva_cinco());
             pst.setInt(8, egreso_detalle[i].getIva_diez());
             pst.setInt(9, egreso_detalle[i].getTotal());
             pst.setString(10, egreso_detalle[i].getObservacion());
             pst.executeUpdate();
             pst.close();
             }
             System.out.println("Se inserto exitosamente");*/
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_rol.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_rol.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_rol.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return SQ_ROL;
    }
}
