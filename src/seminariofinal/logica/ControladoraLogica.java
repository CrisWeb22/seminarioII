
package seminariofinal.logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import seminariofinal.persistencia.ControladoraPersistencia;

public class ControladoraLogica {
    
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    //DOCENTE:
    
    public void crearDocente(Docente doc){
        controlPersis.crearDocente(doc);
    }
    
    public void eliminarDocente(int id){
        controlPersis.eliminarDocente(id);
    }
    
    public void editarDocente(Docente doc){
        controlPersis.editarDocente(doc);
    }
    
    public Docente traerDocente(int id){
        return controlPersis.traerDocente(id);
    }
    
    public ArrayList<Docente> traerListaDocentes(){
        return controlPersis.traerListaDocentes();
    }
    
    //CARGO DOCENTE:
    
    public void crearCargoDocente(CargoDocente cargo){
        controlPersis.crearCargoDocente(cargo);
    }
    
    public void eliminarCargoDocente(int id){
        controlPersis.eliminarCargoDocente(id);
    }
    
    public void editarCargoDocente(CargoDocente cargo){
        controlPersis.editarCargoDocente(cargo);
    }
    
    public CargoDocente traerCargoDocente(int id){
        return controlPersis.traerCargoDocente(id);
    }
 
    public ArrayList<CargoDocente> traerListaCargoDocente(){
        return controlPersis.traerListaCargoDocente();
    }
    
    //INSTITUTO:
    public void crearInstituto(Instituto insti){
        controlPersis.crearInstituto(insti);
    }
    
    public void eliminarInstituto(int id){
        controlPersis.eliminarInstituto(id);
    }
    
    public void editarInstituto(Instituto insti){
        controlPersis.editarInstituto(insti);
    }
    
    public Instituto traerInstituto(int id){
        return controlPersis.traerInstituto(id);
    }
 
    public List<Instituto> traerListaInstituto(){
        return controlPersis.traerListaInstituto();
    }
    
    //ASIGNATURA
    public void crearAsignatura(Asignatura asig){
        controlPersis.crearAsignatura(asig);
    }
    
    public void eliminarAsignatura(int id){
        controlPersis.eliminarAsignatura(id);
    }
    
    public void editarAsignatura(Asignatura asig){
        controlPersis.editarAsignatura(asig);
    }
    
    public Asignatura traerAsginatura(int id){
        return controlPersis.traerAsignatura(id);
    }
    
    public List<Asignatura> traerListaAsignaturas(){
        return controlPersis.traerListaAsignaturas();
    }
}
