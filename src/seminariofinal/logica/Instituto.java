
package seminariofinal.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    @OneToMany(mappedBy="insti")
    private List<CargoDocente> listaCargosDocentes = new ArrayList<CargoDocente>();
    @OneToMany(mappedBy="insti")
    private List<Docente> listaDocentes = new ArrayList<Docente>();
    @OneToMany(mappedBy="insti")
    private List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();

    public Instituto() {
    }

    public Instituto(int idInsti, String nombreInsti) {
        this.idInsti = idInsti;
        this.nombreInsti = nombreInsti;
    }

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public List<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public List<CargoDocente> getListaCargosDocentes() {
        return listaCargosDocentes;
    }

    public void setListaCargosDocentes(List<CargoDocente> listaCargosDocentes) {
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
