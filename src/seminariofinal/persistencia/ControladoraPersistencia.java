
package seminariofinal.persistencia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import seminariofinal.logica.CargoDocente;
import seminariofinal.logica.Docente;
import seminariofinal.logica.Instituto;
import seminariofinal.persistencia.exceptions.NonexistentEntityException;

public class ControladoraPersistencia {
    
    DocenteJpaController docJpa = new DocenteJpaController();
    CargoDocenteJpaController cargoJpa = new CargoDocenteJpaController();
    InstitutoJpaController instiJpa = new InstitutoJpaController();

    public void crearDocente(Docente doc) {
        docJpa.create(doc);
    }

    public void eliminarDocente(int id) {
        try {
            docJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarDocente(Docente doc) {
        try {
            docJpa.edit(doc);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Docente traerDocente(int id) {
        return docJpa.findDocente(id);
    }

    public ArrayList<Docente> traerListaDocentes() {
        List<Docente> auxList = docJpa.findDocenteEntities();
        ArrayList<Docente> listaDocentes = new ArrayList<Docente> (auxList);
        return listaDocentes;
    }

    //CARGO DOCENTE: 
    public void crearCargoDocente(CargoDocente cargo) {
        cargoJpa.create(cargo);
    }

    public void eliminarCargoDocente(int id) {
        try {
            cargoJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarCargoDocente(CargoDocente cargo) {
        try {
            cargoJpa.edit(cargo);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CargoDocente traerCargoDocente(int id) {
        return cargoJpa.findCargoDocente(id);
    }

    public ArrayList<CargoDocente> traerListaCargoDocente() {
        List<CargoDocente> lista = cargoJpa.findCargoDocenteEntities();
        ArrayList<CargoDocente> listaCargos = new ArrayList(lista);
        return listaCargos;
    }

    //INSTITUTO
    
    public void crearInstituto(Instituto insti) {
        instiJpa.create(insti);
    }

    public void eliminarInstituto(int id) {
        try {
            instiJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarInstituto(Instituto insti) {
        try {
            instiJpa.edit(insti);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Instituto traerInstituto(int id) {
        return instiJpa.findInstituto(id);
    }

    public LinkedList<Instituto> traerListaInstituto() {
        List<Instituto> lista = instiJpa.findInstitutoEntities();
        LinkedList<Instituto> listaInstitutos = new LinkedList (lista);
        return listaInstitutos;
    }

   
}
