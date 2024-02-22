
package seminariofinal.logica;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Instituto implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idInsti;
    private String nombreInsti;
    @OneToMany(mappedBy="insti", fetch = FetchType.LAZY)
    private LinkedList<CargoDocente> listaCargosDocentes;

    public Instituto() {
    }

    public Instituto(int idInsti, String nombreInsti, LinkedList<CargoDocente> listaCargosDocentes) {
        this.idInsti = idInsti;
        this.nombreInsti = nombreInsti;
        this.listaCargosDocentes = listaCargosDocentes;
    }

    public LinkedList<CargoDocente> getListaCargosDocentes() {
        return listaCargosDocentes;
    }

    public void setListaCargosDocentes(LinkedList<CargoDocente> listaCargosDocentes) {
        this.listaCargosDocentes = listaCargosDocentes;
    }

    public int getIdInsti() {
        return idInsti;
    }

    public void setIdInsti(int idInsti) {
        this.idInsti = idInsti;
    }

    public String getNombreInsti() {
        return nombreInsti;
    }

    public void setNombreInsti(String nombreInsti) {
        this.nombreInsti = nombreInsti;
    }
    
    
    
}
