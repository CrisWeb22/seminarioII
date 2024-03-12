
package seminariofinal.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Asignatura implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idAsignatura;
    private String nombreAsignatura;
    private String descripcionAsignatura;
    @ManyToOne
    private Docente docente;
    @ManyToOne
    private Instituto insti;

    public Asignatura() {
    }

    public Asignatura(int idAsignatura, String nombreAsignatura, String descripcionAsignatura, Docente docente, Instituto insti) {
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.descripcionAsignatura = descripcionAsignatura;
        this.docente = docente;
        this.insti = insti;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getDescripcionAsignatura() {
        return descripcionAsignatura;
    }

    public void setDescripcionAsignatura(String descripcionAsignatura) {
        this.descripcionAsignatura = descripcionAsignatura;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Instituto getInsti() {
        return insti;
    }

    public void setInsti(Instituto insti) {
        this.insti = insti;
    }
    
    
    
}
