
package capaDatos;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/** Clase que gestiona el acceso a la Base de Datos.
 *
 * @author Alberto Esteves Correia
 */
public class DataAccessObject {


    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /** Contiene la direcciÃ³n de la Base de Datos.
     *  Se inicializa al comenzar la aplicaciÃ³n.
     *  El usuario podrÃ¡ modificarlo en el menÃº de configuraciÃ³n.
     */    
    public static String URL;

    /** Contiene el usuario para la conexiÃ³n a la Base de Datos.
     *  Se inicializa al comenzar la aplicaciÃ³n.
     *  El usuario podrÃ¡ modificarlo en el menÃº de configuraciÃ³n.
     */
    public static String USER;

    /** Contiene la contraseÃ±a para la conexiÃ³n a la Base de Datos.
     *  Se inicializa al comenzar la aplicaciÃ³n.
     *  El usuario podrÃ¡ modificarlo en el menÃº de configuraciÃ³n.
     */
    public static String PASS;

    private static DataAccessObject dataAccessObject=null;
    private PreparedStatement statement = null;
    private Connection connection;

    /** MÃ©todo que nos devuelve el objeto dataAccessObject estÃ¡tico que tiene la clase.
     * La primera vez que se llama a getDataAccessObjectConnected() crea el objeto DataAccessObject
     * y harÃ¡ la conexiÃ³n a la BD. En cualquier caso se hace â€œreturn DataAccessObjectâ€� para que
     * podamos usar el objeto
     *
     * @return dataAccessObject
     */
    public static DataAccessObject getDataAccessObjectConnected(){
		if (dataAccessObject==null){
                    dataAccessObject = new DataAccessObject();
		}
		dataAccessObject.connect();
	
            return dataAccessObject;

    } // fin del mÃ©todo getDataAccessObjectConnected


    /** MÃ©todo que devuelve el objeto statement con el que realizaremos
     * las consultas sql.
     *
     * @param sql contiene la consulta sql a realizar
     *
     * @return statement 
     */

    public PreparedStatement getPreparedStatement(String sql){
        try {
            this.statement = connection.prepareStatement(sql);
            return statement;
        }
        catch (SQLException ex) {
            throw new RuntimeException("Problema al obtener el prepared statement"
                                     + " el sql es: "+sql);
        }
    } // fin del mÃ©todo getPreparedStatement

    private DataAccessObject(){}

    /** Este mÃ©todo se encarga de cargar el Driver MySQL y de realizar
     *  la conexiÃ³n a la Base de Datos. Si ocurre algÃºn error al cargar el Driver
     *  o al intentar conectar a la Base de Datos, el mÃ©todo lanzarÃ¡ una excepciÃ³n.
     *
     */
    private void connect(){
	try {
		Class.forName(DRIVER);
		connection = DriverManager.getConnection(URL, USER, PASS);
                connection.setAutoCommit(false);
	} catch (ClassNotFoundException e) {
		throw new RuntimeException("problemas de driver");
	} catch (SQLException e) {
		throw new RuntimeException("Ha ocurrido un error al conectar con la Base de Datos");
	}
    } // fin del mÃ©todo connect


   /** MÃ©todo que ejecuta la consulta SQL para insertar, borrar o actualizar.
     * Si ocurre algÃºn error, lanzarÃ¡ una excepciÃ³n.
     */
    public void actualizar (){
	try{
            
            this.statement.executeUpdate();
	} catch (SQLException e){
            System.out.println(e.getSQLState());
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("Error de actualizaciÃ³n ");
	}
    } // fin del mÃ©todo actualizar


    /** MÃ©todo que libera los recursos de la Base de Datos y se encarga de
     *  cerrar la conexiÃ³n a la Base de Datos.
     *  Si ocurre algÃºn error, lanzarÃ¡ una excepciÃ³n.
     */
    public void close () {
        try {
            statement.close();
            this.closeConnection();
        }
        catch (SQLException ex) {
            throw new RuntimeException("Problema al cerrar la conexiÃ³n con la Base de Datos");
        }
    } // fin del mÃ©todo close
	
	
    /** MÃ©todo que realiza la entrega (commit) de la sentencia sql y 
     *  cierra la conexiÃ³n a la Base de Datos.
     *  Si ocurre algÃºn error, lanzarÃ¡ una excepciÃ³n.
     */
    private void closeConnection () {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException ex) {
 //           this.rollback();
            throw new RuntimeException("Problema al cerrar la conexiÃ³n");
        }
    } // fin del mÃ©todo closeConnection


    /** MÃ©todo que aborta la transaciÃ³n y devuelve cualquier valor
     *  que fuera modificado a sus valores anteriores.
     *
     */
    public void rollback () {
        try {
            statement.close();
            System.out.println("Rollback 1");
            connection.rollback();
            System.out.println("Rollback 2");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Problema al hacer rollback");
        }
    } // fin del mÃ©todo rollback

} // fin de la clase DataAccessObject