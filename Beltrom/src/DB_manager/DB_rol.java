/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
            String query = "SELECT DESCRIPCION \"Descripción\" "
                    + "FROM MENU_ITEM "
                    + "ORDER BY DESCRIPCION";
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
}
