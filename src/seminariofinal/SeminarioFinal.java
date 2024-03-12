
package seminariofinal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import seminariofinal.logica.Asignatura;
import seminariofinal.logica.CargoDocente;
import seminariofinal.logica.ControladoraLogica;
import seminariofinal.logica.Docente;
import seminariofinal.logica.Instituto;


public class SeminarioFinal {

    public static void main(String[] args) {
        
        ControladoraLogica control = new ControladoraLogica();
        /*
        List<CargoDocente> listaCargos = new ArrayList<CargoDocente>();
        List<Docente> listaDocentes = new ArrayList<Docente>();
        List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
        
        Instituto facu = new Instituto(10, "Exactas"); 
        
        control.crearInstituto(facu);
        
        //Creo cargos
        CargoDocente cargo1 = new CargoDocente(02, 05, facu);
        CargoDocente cargo2 = new CargoDocente(04, 10, facu);
        CargoDocente cargo3 = new CargoDocente(06, 15, facu);
        
        control.crearCargoDocente(cargo1);
        control.crearCargoDocente(cargo2);
        control.crearCargoDocente(cargo3);
        
        //Creo docentes
        Docente doc1 = new Docente(03, 35015395, "Cris", "Olm", new Date(), "olmedo@cristian.com", cargo1, facu);
        Docente doc2 = new Docente(06, 35015395, "Leti", "Escobar", new Date(), "olmedo@cristian.com", cargo2, facu);
        Docente doc3 = new Docente(07, 35015395, "Koba", "Guardian", new Date(), "olmedo@cristian.com", cargo3, facu);
        
        control.crearDocente(doc1);
        control.crearDocente(doc2);
        control.crearDocente(doc3);
        
        listaCargos.add(cargo1);
        listaCargos.add(cargo2);
        listaCargos.add(cargo3);
        
        listaDocentes.add(doc1);
        listaDocentes.add(doc2);
        listaDocentes.add(doc3);
        
        Asignatura asig1 = new Asignatura(12, "Programacion I", "Introductoria del primer año", doc1, facu);
        Asignatura asig2 = new Asignatura(24, "Programacion II", "Veremos JAVA y PYTHON", doc2, facu);
        Asignatura asig3 = new Asignatura(36, "Programacion Avanzda", "Desarrollos de sistemas completos", doc3, facu);
        
        control.crearAsignatura(asig1);
        control.crearAsignatura(asig2);
        control.crearAsignatura(asig3);
        
        listaAsignaturas.add(asig1);
        listaAsignaturas.add(asig2);
        listaAsignaturas.add(asig3);
        
        facu.setListaCargosDocentes(listaCargos);
        facu.setListaDocentes(listaDocentes);
        facu.setListaAsignaturas(listaAsignaturas);
        control.editarInstituto(facu);
        */
        //System.out.println("El nombre del instituto ID Nº: " + facu.getIdInsti() + " es: " + facu.getNombreInsti());
       // Instituto facu = new Instituto(13, "Exactas");
        //Instituto uni = new Instituto(12, "Economicas");
        //control.crearInstituto(facu);
        //control.crearInstituto(uni);
        //control.eliminarInstituto(10);
        
        
        Docente doc = new Docente(); 
        Instituto insti = new Instituto();
        insti = control.traerInstituto(12);
        doc = control.traerDocente(7);
        doc.setInsti(insti);
        control.editarDocente(doc);
        
        
    }

    public SeminarioFinal() {
    }
    
}
