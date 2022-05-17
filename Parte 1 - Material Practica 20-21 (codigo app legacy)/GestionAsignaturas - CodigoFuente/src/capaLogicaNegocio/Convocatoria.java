
package capaLogicaNegocio;

import capaDatos.ControladorConvocatoria;
import capaDatos.DataAccessObject;

/** Clase que contiene la informaci�n de la convocatoria.
 *  El atributo p�blico convocatoria contendr� el curso actual.
 *
 * @author Confiencial
 */
public class Convocatoria {

    private static String convocatoria_actual;

    private String cod_convocatoria = null;
    private int actual;

    /** Crea una nueva convocatoria.
     *
     */
    public Convocatoria () {
    } // fin del constructor

    /** Crea una nueva convocatoria e incializa el atributo convocatoria.
     *
     * @param convocatoria c�digo de la convocatoria.
     */
    public Convocatoria (String convocatoria) {
        this.cod_convocatoria = convocatoria;
    } // fin del constructor

    /** Crea una nueva convocatoria e incializa los atributos convocatoria y actual.
     *
     * @param convocatoria c�digo de la convocatoria.
     * @param actual indica si la convocatoria es la que se cursa actualmente o no.
     */
    public Convocatoria (String convocatoria, int actual) {
        this.cod_convocatoria = convocatoria;
        this.actual = actual;
    } // fin del constructor

    /** M�todo que cambia, en la Base de Datos, la convocatoria actual.
     *  Si la convocatoria actual es "ordinaria", cambia a "extraordinaria"
     *  y viceversa.
     */
    public void cambioConvocatoria() {
        ControladorConvocatoria DAOConvocatoria = new ControladorConvocatoria();
        DAOConvocatoria.cambioConvocatoria();
        GrupoPractica grupos = new GrupoPractica();
        grupos.desactivarGruposPracticas();
    } // fin del m�todo cambioConvocatoria


    /** M�todo que realiza la recuperaci�n del BackUp de datos cuando
     *  el usuario pulsa el bot�n de "deshacer �ltima decisi�n".
     *  Se eliminar�n TODOS los registros de la convocatoria
     *  extraordinaria del curso actual, y recuperar� los datos de la convocatoria
     *  anterior. Es decir, el estado de la Base de Datos y de la aplicaci�n
     *  ser� el �ltimo estado en el que se encontraba antes de cambiar de
     *  convocatoria.
     */
    public void BackUpConvocatoria() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.eliminarEvaluacionesConvocatoriaActual(dataAccessObject);
            Tutoria tutorias = new Tutoria();
            tutorias.eliminarTutoriasConvocatoriaActual(dataAccessObject);
            Examen examen = new Examen();
            examen.bajaCodExamenConvocatoriaActual(dataAccessObject);
            Practica practica = new Practica();
            practica.bajaCodPracticaConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpConvocatoria");
        }

        this.cambioConvocatoria();
        this.recuperarConvocatoriaActual();

        dataAccessObject = DataAccessObject.getDataAccessObjectConnected();

        try{
            GrupoPractica grupos = new GrupoPractica();
            Profesor profesores = new Profesor();
            grupos.reactivarGruposPracticasEnUsoEnConvocatoriaActual(dataAccessObject);
            profesores.reactivarProfesoresConRegistrosEnConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpConvocatoria 2");
        }
    } // fin del m�todo BackUpConvocatoria


    /** M�todo que, a trav�s de la clase ControladorConvocatoria, consulta en la
     * Base de Datos cual es la convocatoria actual y lo asigna al atributo
     * est�tico convocatoria_actual.
     */
    @SuppressWarnings("static-access")
    public void recuperarConvocatoriaActual() {
        ControladorConvocatoria DAOConvocatoria = new ControladorConvocatoria ();
        this.convocatoria_actual = DAOConvocatoria.recuperarConvocatoriaActual ();
    } // fin del m�todo recuperarConvocatoriaActual


    /** M�todo que devuelve el c�digo de la convocatoria.
     *
     * @return c�digo de la convocatoria.
     */
    public String getConvocatoria() {
        return this.cod_convocatoria;
    } // fin del m�todo getConvocatoria


    /** M�todo que devuelve el atributo actual de la convocatoria.
     *  Indica si la convocatoria es la que se cursa actualmente o no.
     *
     * @return c�digo de la convocatoria.
     */
    public int getActual() {
        return this.actual;
    }


    @SuppressWarnings("static-access")
    /** M�todo que devuelve el c�digo de la convocatoria actual
     * 
     * @return c�digo de la convocatoria actual en forma de cadena (String)
     */
    public String getConvocatoriaActual() {
        return this.convocatoria_actual;
    } // fin del m�todo getConvocatoriaActual

} // fin de la clase Convocatoria