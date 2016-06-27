/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_manager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class DB_manager {

    private static Connection con = null;
    private static Statement st = null;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    private static ResultSetMetaData rsmd = null;
    private static DatabaseMetaData dbmt = null;

    public static boolean conectarBD(String usuario, String password) throws SQLException {
        try {
            Conexiones.cargarDriver(Conexiones.SGBD_POSTGRES);
            con = Conexiones.obtenerConexion(Conexiones.SGBD_POSTGRES, usuario, password);
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar driver: " + e);
            JOptionPane.showMessageDialog(null, "Error al conectarse: problema con el driver", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            System.out.println("Error al conectarse: " + e);
            JOptionPane.showMessageDialog(null, "Error al conectarse: problema de red", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static boolean conectarBD(int dbms, String url, String usuario, String password) throws SQLException {
        boolean b = false;
        switch (dbms) {
            case Conexiones.SGBD_ORACLE: {
                try {
                    Conexiones.cargarDriver(Conexiones.SGBD_ORACLE);
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al cargar driver" + e);
                }
            }
            break;
            case Conexiones.SGBD_POSTGRES: {
                try {
                    Conexiones.cargarDriver(Conexiones.SGBD_POSTGRES);
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al cargar driver" + e);
                }
            }
            break;
        }
        try {
            con = Conexiones.obtenerConexion(url, usuario, password);
            b = true;
        } catch (SQLException e) {
            System.out.println("Error al conectarse" + e);
        }
        return b;
    }

    public static Connection getConection() {
        return con;
    }

    public static String obtenerNombreBaseDatos() throws SQLException {
        String BaseDatos = null;
        BaseDatos = con.getMetaData().getDatabaseProductName();
        return BaseDatos;
    }

    public static String obtenerNombreUsuario() throws SQLException {
        String usuario = null;
        usuario = con.getMetaData().getUserName();
        return usuario;
    }

    public static String obtenerNombreServidor() throws SQLException {
        String servidor = null;
        servidor = con.getCatalog();
        return servidor;
    }

    /**
     * TOma una sentencia SQL, la pasa a la BD, y obtiene los resultados.
     * Retorna un objeto c_ResultSetTableModel, que contiene los resultados de
     * forma que el componente JTable pueda mostrarlos.
     *
     */
    public static ResultSetTableModel getResultSetTableModel(String query)
            throws SQLException {

        if (con == null) {
            throw new IllegalStateException("Connection already closed.");
        }

        //se crea una sentencia
        Statement statement
                = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // se ejecuta el query y se obtienen los resultados en un ResultSet
        ResultSet r = statement.executeQuery(query);

        ResultSetTableModel rstm = new ResultSetTableModel(r);

        return rstm;
    }

    public static void cerrarConexiones() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Vector obtenerRubro() {
        Vector rubro = null;
        String q = "SELECT rubr_descripcion "
                + "FROM rubro ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            rubro = new Vector();
            while (rs.next()) {
                rubro.add(rs.getString("rubr_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rubro;
    }

    public static ResultSetTableModel consultarRubro() {
        ResultSetTableModel rubro = null;
        String q = "SELECT rubr_id_rubro \"ID\" ,rubr_descripcion \"Descripcion\" "
                + "FROM rubro ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            rubro = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rubro;
    }

    public static Vector obtenerImpuesto() {
        Vector impuesto = null;
        String q = "SELECT impu_descripcion "
                + "FROM impuesto ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            impuesto = new Vector();
            while (rs.next()) {
                impuesto.add(rs.getString("impu_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return impuesto;
    }

    public static ResultSetTableModel consultarImpuesto() {
        ResultSetTableModel impuesto = null;
        String q = "SELECT impu_id_impuesto \"ID\" , impu_descripcion\"Descripcion\" "
                + "FROM impuesto ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            impuesto = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return impuesto;
    }

    public static Vector obtenerEstado() {
        Vector estado = null;
        String q = "SELECT esta_descripcion "
                + "FROM estado ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            estado = new Vector();
            while (rs.next()) {
                estado.add(rs.getString("esta_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return estado;
    }

    public static Vector consultarRespuesta() {
        Vector respuesta = null;
        String q = "SELECT resp_descripcion "
                + "FROM respuesta ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            respuesta = new Vector();
            while (rs.next()) {
                respuesta.add(rs.getString("resp_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return respuesta;
    }

    public static ResultSetTableModel consultarMarca() {
        ResultSetTableModel impuesto = null;
        String q = "SELECT marc_id_marca \"ID\" ,marc_descripcion \"Descripcion\" "
                + "FROM marca ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            impuesto = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return impuesto;
    }

    public static Vector obtenerMarca() {
        Vector impuesto = null;
        String q = "SELECT marc_descripcion  "
                + "FROM marca ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            impuesto = new Vector();
            while (rs.next()) {
                impuesto.add(rs.getString("marc_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return impuesto;
    }

    public static Vector obtenerPais() {
        Vector pais = null;
        String q = "SELECT pais_descripcion  "
                + "FROM pais ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            pais = new Vector();
            while (rs.next()) {
                pais.add(rs.getString("pais_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pais;
    }

    public static Integer obtenerIdPais(String nombrePais) {
        Integer pais = null;
        String q = "SELECT pais_id_pais  "
                + "FROM pais "
                + " WHERE PAIS_DESCRIPCION LIKE '" + nombrePais + "'";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                pais = (rs.getInt("pais_id_pais"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pais;
    }

    public static Integer obtenerIdGenero(String genero) {
        Integer sexo = null;
        String q = "SELECT SEXO_ID_SEXO "
                + "FROM SEXO "
                + "WHERE SEXO_DESCRIPCION LIKE '" + genero + "'";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                sexo = (rs.getInt("SEXO_ID_SEXO"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sexo;
    }

    public static Integer obtenerIdEstadoCivil(String estado_civil) {
        Integer est_civil = null;
        String q = "SELECT ESCI_ID_ESTADO_CIVIL "
                + "FROM ESTADO_CIVIL "
                + "WHERE ESCI_DESCRIPCION LIKE '" + estado_civil + "'";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                est_civil = (rs.getInt("ESCI_ID_ESTADO_CIVIL"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return est_civil;
    }

    public static Integer obtenerIdCiudad(String ciudad) {
        Integer id_ciudad = null;
        String q = "SELECT CIUD_ID_CIUDAD "
                + "FROM CIUDAD "
                + "WHERE CIUD_DESCRIPCION LIKE '" + ciudad + "'";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                id_ciudad = (rs.getInt("CIUD_ID_CIUDAD"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id_ciudad;
    }

    public static String obtenerCiudad(int id) {
        String ciudad = null;
        String q = "SELECT CIUD_DESCRIPCION "
                + "FROM CIUDAD "
                + "WHERE CIUD_ID_CIUDAD = " + id;
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                ciudad = (rs.getString("CIUD_DESCRIPCION"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ciudad;
    }

    public static String obtenerEstadoCivil(Integer id_estado_civil) {
        String estado_civil = null;
        String q = "SELECT ESCI_DESCRIPCION "
                + "FROM ESTADO_CIVIL "
                + "WHERE ESCI_ID_ESTADO_CIVIL = " + id_estado_civil;
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                estado_civil = (rs.getString("ESCI_DESCRIPCION"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return estado_civil;
    }

    public static String obtenerGenero(Integer id_genero) {
        String genero = null;
        String q = "SELECT SEXO_DESCRIPCION "
                + "FROM SEXO "
                + "WHERE SEXO_ID_SEXO = " + id_genero;
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                genero = (rs.getString("SEXO_DESCRIPCION"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return genero;
    }

    public static String obtenerPais(Integer id_pais) {
        String pais = null;
        String q = "SELECT PAIS_DESCRIPCION "
                + "FROM PAIS "
                + "WHERE PAIS_ID_PAIS = " + id_pais;
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                pais = (rs.getString("PAIS_DESCRIPCION"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pais;
    }

    public static Vector obtenerCiudad() {
        Vector ciudad = null;
        String q = "SELECT Ciud_descripcion  "
                + "FROM Ciudad ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            ciudad = new Vector();
            while (rs.next()) {
                ciudad.add(rs.getString("Ciud_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ciudad;
    }

    public static Vector obtenerEstadoCivil() {
        Vector estadoCivil = null;
        String q = "SELECT esci_descripcion  "
                + "FROM estado_civil ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            estadoCivil = new Vector();
            while (rs.next()) {
                estadoCivil.add(rs.getString("esci_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return estadoCivil;
    }

    public static Vector obtenerGenero() {
        Vector impuesto = null;
        String q = "SELECT sexo_descripcion "
                + "FROM sexo ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            impuesto = new Vector();
            while (rs.next()) {
                impuesto.add(rs.getString("sexo_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return impuesto;
    }

    public static Vector obtenerRoles() {
        Vector rol = null;
        String q = "SELECT rol_descripcion "
                + "FROM rol ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            rol = new Vector();
            while (rs.next()) {
                rol.add(rs.getString("rol_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rol;
    }

    public static Vector obtenerTablas(String rol) {
        Vector tablas = null;
        String q = "SELECT DISTINCT menu_descripcion "
                + "FROM funcionario f, rol r, rol_usuario ru, menu m, menu_item mi, permiso_rol pr "
                + "WHERE func_id_funcionario = rous_id_funcionario "
                + "AND   rol_id_rol = rous_id_rol "
                + "AND   menu_id_menu = pero_id_menu "
                + "AND   rol_id_rol = pero_id_rol "
                + "AND   meit_id_menu_item = pero_id_menu_item "
                + "AND   rol_descripcion = '" + rol + "' ";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            tablas = new Vector();
            while (rs.next()) {
                tablas.add(rs.getString("menu_descripcion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tablas;
    }
    /* public static c_ResultSetTableModel consultarProducto(producto producto)
     throws SQLException {

     if (con == null)
     throw new IllegalStateException("Connection already closed.");
     String q= "SELECT prod_id_producto  AS codigo   , "
     + "       prod_descripcion AS  Descripcion    "
     + "FROM producto "
     + "WHERE prod_descripcion LIKE '"+producto.getDescripcion()+"%' "
     + "ORDER BY prod_id_producto desc";
     //SELECT prod_id_producto   "ID producto"  ,  prod_descripcion  "Descripcion"   FROM producto
     //se crea una sentencia
     Statement statement =
     con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

     // se ejecuta el query y se obtienen los resultados en un ResultSet
     ResultSet r = statement.executeQuery(q);

     c_ResultSetTableModel rstm = new c_ResultSetTableModel(r);

     return rstm;
     }*/


    /*
     * Metodo para manejo de transaccion manual
     */
    public static void habilitarTransaccionManual() {
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deshabilita el autocommit de la base de datos
     *
     * @exception SQLException Si no se pudo establecer autocommit
     */
    public static void deshabilitarTransaccionManual() {
        try {

            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//	***Metodo para establecer transaccion***
    public static void establecerTransaccion() throws SQLException {
        con.commit();
    }

//	***Metodo para manejo deshacer la transaccion***
    public static void deshacerTransaccion() {
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int obtenerSqTelefono() {
        Integer sq = 0;
        String query = "SELECT MAX(TELE_ID_TELEFONO) \"TELE_ID_TELEFONO\" FROM TELEFONO";
        try {
            pst = DB_manager.getConection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            while (rs.next()) {
                sq = rs.getInt("TELE_ID_TELEFONO") + 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sq;
    }

    public static int obtenerSqPersona() {
        Integer sq = 0;
        String query = "SELECT MAX(PERS_ID_PERSONA) \"PERS_ID_PERSONA\" FROM PERSONA";
        try {
            pst = DB_manager.getConection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            while (rs.next()) {
                sq = rs.getInt("PERS_ID_PERSONA") + 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sq;
    }

    public static boolean verificarUsuario(String alias, String password) {
        String q = "SELECT alias  FROM FUNCIONARIO WHERE ALIAS ='" + alias + "' AND PASSWORD ='" + password + "';";
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            if (!rs.isBeforeFirst()) {
                System.out.println("No data");
                return false;
            }else{
                System.out.println("We got data!");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("There is NO data");
        return false;
    }
}
