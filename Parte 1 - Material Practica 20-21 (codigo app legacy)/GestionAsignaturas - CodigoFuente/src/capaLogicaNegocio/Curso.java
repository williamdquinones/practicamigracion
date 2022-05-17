
package capaLogicaNegocio;

import capaDatos.ControladorCurso;
import capaDatos.DataAccessObject;

/** Clase que contiene la informaci�n del curso_actual.
 *  El atributo p�blico curso_actual contendr� el curso_actual actual.
 *
 * @author Confiencial
 */
public class Curso {

    private static int curso_actual;
    private int cod_curso;
    private int actual = 0;


    /** Crea un nuevo curso.
     */
    public Curso () {
    } // fin del constructor


    /** Crea un nuevo curso e inicializa el atributo cod_curso.
     *
     * @param curso c�digo del curso. Ser� el a�o de inicio.
     */
    public Curso (int curso){
        this.cod_curso = curso;
    } // fin del constructor


    /** Crea un nuevo curso e inicializa el atributo cod_curso.
     *
     * @param curso c�digo del curso. Ser� el a�o de inicio.
     * @param actual indica si el curso es el que se cursa actualmente o no.
     */
    public Curso (int curso, int actual){
        this.cod_curso = curso;
        this.actual = actual;
    } // fin del constructor

    /** M�todo que, a trav�s de la clase ControladorCurso, consulta en la
     * Base de Datos cual es el curso actual y lo asigna al atributo
     * est�tico curso_actual.
     */
    @SuppressWarnings("static-access")
    public void recuperarCursoActual() {
        ControladorCurso DAOCurso = new ControladorCurso ();
        this.curso_actual = DAOCurso.recuperarCursoActual ();
    } // fin del m�todo recuperarCursoActual


    /** M�todo que cambia, en la Base de Datos, el curso actual.
     *  El nuevo curso ser� el a�o de inicio del actual, m�s uno.
     */
    public void cambioCurso() {
        ControladorCurso DAOCurso = new ControladorCurso();
        this.cod_curso = this.curso_actual+1;
        this.actual = 1;
        DAOCurso.desactivarCursoActual(this.curso_actual);
        DAOCurso.insertarEnTablaCurso(this);
        this.curso_actual = this.curso_actual+1;
    } // fin del m�todo cambioCurso



    /** M�todo que realiza el BackUp del curso anterior. Elimina TODOS los registros
     *  del curso actual (evaluaciones, matr�culas, tutor�as, pr�cticas, ex�menes,
     *  registros de imparticiones de clase, c�digo del curso actual que va a dejar
     *  de serlo y grupos de pr�cticas en uso en el curso).
     *
     *  Posteriormente, recupera la informaci�n del curso anterior, y deja la
     *  Base de Datos y la aplicaci�n en el �ltimo estado en el que se encontraba
     *  antes de cambiar de curso.
     */
    public void BackUpCurso() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            Matricula matricula = new Matricula();
            matricula.eliminarMatriculasCursoActual(dataAccessObject);
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.eliminarEvaluacionesConvocatoriaActual(dataAccessObject);
            Tutoria tutorias = new Tutoria();
            tutorias.eliminarTutoriasConvocatoriaActual(dataAccessObject);
            Profesor profesor = new Profesor();
            profesor.eliminarImparticionesCursoActual(dataAccessObject);
            Examen examen = new Examen();
            examen.bajaCodExamenConvocatoriaActual(dataAccessObject);
            Practica practica = new Practica();
            practica.bajaCodPracticaConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpCurso");
        }

        Convocatoria convocatoria = new Convocatoria();
        convocatoria.cambioConvocatoria();
        convocatoria.recuperarConvocatoriaActual();
        this.cambioCursoAnterior();
        this.recuperarCursoActual();

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
            throw new RuntimeException("Problema en BackUpCurso 2");
        }
    } // fin del m�todo BackUpCurso



    /** M�todo que elimina de la Base de Datos el curso actual y actualiza el
     *  campo actual al curso anterior.
     */
    public void cambioCursoAnterior() {
        ControladorCurso DAOCurso = new ControladorCurso();
        DAOCurso.eliminarCurso(this.curso_actual);
        DAOCurso.nuevoCursoActual(this.curso_actual-1);
    } // fin del m�todo cambioCursoAnterior


    /** M�todo que devuelve el c�digo del curso.
     *
     * @return c�digo del curso.
     */
    public int getCurso () {
        return this.cod_curso;
    } // fin del m�todo getCurso

    
    /** M�todo que indica si el objeto curso que llama al m�todo es el actual.
     * 
     * @return 1 si es el curso actual.
     *         0 en caso contrario.
     */
    public int getActual () {
        return this.actual;
    } // fin del m�todo getActual


    /** M�todo que devuelve el curso actual.
     * 
     * @return el a�o de inicio del curso actual.
     */
    public int getCursoActual() {
        return curso_actual;
    }

    /** M�todo que devuelve el curso actual en formato String.
     *  El formato ser�: a�o inicio "/" a�o fin.
     *  Ej.: 2010/2011
     *
     * @return curso actual en formato String.
     */
    public String getCursoActualEnString() {
        return (Integer.toString(curso_actual)+"/"+Integer.toString(curso_actual+1));
    } // fin del m�todo getCursoActualEnString


    /** M�todo que devuelve el curso en formato String.
     *  El formato ser�: a�o inicio "/" a�o fin.
     *  Ej.: 2010/2011
     *
     * @return curso en formato String.
     */
    public String getCursoEnString() {
        return (Integer.toString(cod_curso)+"/"+Integer.toString(cod_curso+1));
    } // fin del m�todo getCursoEnString

} // fin de la clase Curso