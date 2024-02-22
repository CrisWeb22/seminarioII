
package seminariofinal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import seminariofinal.logica.CargoDocente;
import seminariofinal.logica.ControladoraLogica;
import seminariofinal.logica.Docente;
import seminariofinal.logica.Instituto;


public class SeminarioFinal {

    public static void main(String[] args) {
        
        ControladoraLogica control = new ControladoraLogica();
        
        LinkedList<CargoDocente> listaCargos = new LinkedList<CargoDocente>();
        
        Instituto facu = new Instituto(10, "Exactas", listaCargos); 
        
        control.crearInstituto(facu);
        
        CargoDocente cargo1 = new CargoDocente(02, 05, facu);
        CargoDocente cargo2 = new CargoDocente(04, 10, facu);
        CargoDocente cargo3 = new CargoDocente(06, 15, facu);
        
        control.crearCargoDocente(cargo1);
        control.crearCargoDocente(cargo2);
        control.crearCargoDocente(cargo3);
        
        listaCargos.add(cargo1);
        listaCargos.add(cargo2);
        listaCargos.add(cargo3);
        
        facu.setListaCargosDocentes(listaCargos);
        control.editarInstituto(facu);
        
             
    }
    
}
